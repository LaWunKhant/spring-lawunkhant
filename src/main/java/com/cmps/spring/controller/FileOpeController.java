package com.cmps.spring.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/file")
@RestController
public class FileOpeController {

    private final Path applogDir = Path.of("src", "main", "resources", "static", "applog");
    private final Path logFile = applogDir.resolve("dummy_error.log");
    private final Path archiveDir = applogDir.resolve("archive");
    private final Path backupFile = archiveDir.resolve("backup_app.log");
    
    @GetMapping("/path-test")
    public String checkPath() {
        // 1. パスの組み立て
        Path path = Path.of("src", "main", "resources", "static", "css", "style.css");

        // 2. 連結（resolve）
        Path subDir = Path.of("src", "main", "resources");
        Path resolvedPath = subDir.resolve(Path.of("static", "css", "style.css"));

        // 3. 親フォルダ、ファイル名
        Path parentDir = path.getParent();
        Path fileName = path.getFileName();

        // 結果の出力
        StringBuilder sb = new StringBuilder();
        sb.append("組み立てたパス: ").append(path).append("<br><br>");

        sb.append("絶対パス: ").append(path.toAbsolutePath()).append("<br>");
        sb.append("resolveで連結したパス: ").append(resolvedPath).append("<br><br>");

        sb.append("親フォルダ: ").append(parentDir).append("<br>");
        sb.append("ファイル名: ").append(fileName);

        return sb.toString();
    }
    
    @GetMapping("/files-attribute")
    public String checkAttributes() {
        Path[] paths = {
                Path.of("src", "main", "resources", "static", "css", "style.css"),
                Path.of("src", "main", "resources", "static", "css", "none.txt")
        };
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>① 属性確認の結果</h3>");

        for (Path path : paths) {
            try {
                sb.append("<b>").append(path).append("</b><br>");
                if (Files.exists(path)) {
                    sb.append("ファイルサイズ: ").append(Files.size(path)).append(" bytes<br>");
                    sb.append("ディレクトリか: ").append(Files.isDirectory(path)).append("<br>");
                    sb.append("最終更新日時: ").append(Files.getLastModifiedTime(path)).append("<br>");
                    sb.append("所有者: ").append(Files.getOwner(path));
                } else {
                    sb.append("ファイルが存在しません。");
                }
            } catch (IOException e) {
                return "エラー: " + e.getMessage();
            }
            sb.append("<br><br>");
        }

        return sb.toString();
    }
    
    @GetMapping("/files-operation")
    public String fileOperation() {
        // テスト用のパス作成
        Path studyDir = Path.of("src", "main", "resources", "static", "fileope");
        Path source = Path.of("src", "main", "resources", "static", "css", "style.css");
        Path target = studyDir.resolve("style_copy.css");

        try {
            // 1. フォルダがなければ作成
            Files.createDirectories(studyDir);

            // 2. コピーを実行（上書き設定）
            // 第3引数にコピーについてオプションを指定可能。REPLACE_EXISTINGは、存在する場合は既存のファイルを置換する
            Files.copy(source, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // 3. 削除（練習用：作成したコピーを即座に消す場合はこれを有効化）
            // Files.deleteIfExists(target);

            return "操作成功: " + target + " を作成しました。";
        } catch (IOException e) {
            return "操作失敗: " + e.getMessage();
        }
    }
    
    @GetMapping("/files-stream")
    public String streamOperation() {
        // 探索を開始するルートディレクトリ（ここではプロジェクトのsrcフォルダ）
        Path startDir = Path.of("src", "main", "resources");
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>③ Stream APIを活用した探索・読み込み</h3>");

        try {
            // 1. find：特定の条件（例：拡張子が .css のファイル）で探す
            sb.append("<b>[.css ファイルの検索結果]</b><br>");
            try (Stream<Path> foundFiles = Files.find(startDir, Integer.MAX_VALUE,
                    (p, attr) -> p.toString().endsWith(".css"))) {
                foundFiles.forEach(p -> sb.append("見つかったパス: ").append(p).append("<br>"));
            }

            // 操作対象のファイル
            Path targetFile = startDir.resolve(Path.of("static", "css", "style.css"));
            if (Files.exists(targetFile)) {
                // 2. lines：ファイルの中身を1行ずつフィルタリングして読み込む
                sb.append("<br><b>[ファイル内の特定行（colorを含む行）の抽出]</b><br>");
                try (Stream<String> lines = Files.lines(targetFile)) {
                    lines.filter(line -> line.contains("color")).forEach(line -> sb.append(line).append("<br>"));
                }

                // 3. 一括読み込み（List<String>で取得）
                sb.append("<br><b>[一括読み込み]</b><br>");
                List<String> allLines = Files.readAllLines(targetFile);
                sb.append("ファイル全体の行数: ").append(allLines.size());

            } else {
                sb.append("<br>テスト用ファイルが見つかりません。");
            }

        } catch (IOException e) {
            return "エラー: " + e.getMessage();
        }
        return sb.toString();
    }
    
    @GetMapping("/write-read")
    public String writeAndRead() {
        Path source = Path.of("src", "main", "resources", "static", "css", "style.css");
        Path target = Path.of("src", "main", "resources", "static", "fileope", "style_new.css");

        // try-with-resources で読み書き両方のストリームを開く
        // セミコロンで区切ることで、複数の変数を宣言することが可能
        try (BufferedReader reader = Files.newBufferedReader(source);
                BufferedWriter writer = Files.newBufferedWriter(target)) {

            // 1. 全ての行を書き写す
            String line;
            while ((line = reader.readLine()) != null) {// 代入と比較を同時に行っている
                writer.write(line); // 読み込んだ内容を書き込み
                writer.newLine(); // 改行文字を書き込み
            }

            // 2. コメントを書き込む
            writer.write("/* Javaから書き込みました */");
            writer.newLine();

            return "成功: " + target + " を作成しました。";
        } catch (IOException e) {
            return "失敗: " + e.getMessage();
        }
    }

    /**
     * 問1: ディレクトリとファイルの作成（存在チェック付き）
     */
    @GetMapping("/exercise1")
    public String runExercise1() {
        try {
            StringBuilder sb = new StringBuilder("<h3>問1 実行結果</h3>");

            // 1. ディレクトリの存在チェック & 作成
            if (Files.exists(applogDir)) {
                sb.append("ディレクトリは既に存在します: ").append(applogDir).append("<br>");
            } else {
                Files.createDirectories(applogDir);
                sb.append("ディレクトリを作成しました: ").append(applogDir).append("<br>");
            }

            // 2. ファイルの存在チェック & 作成
            if (Files.exists(logFile)) {
                sb.append("ファイルは既に存在します: ").append(logFile).append("<br>");
            } else {
                Files.createFile(logFile);
                sb.append("ファイルを作成しました: ").append(logFile).append("<br>");
            }

            return sb.toString();
        } catch (IOException e) {
            return "問1 エラー: " + e.getMessage();
        }
    }

    /**
     * 問2: テキストの書き込みとアーカイブへのコピー
     */
    @GetMapping("/exercise2")
    public String runExercise2() {
        try {
            if (!Files.exists(logFile)) {
                return "エラー: 先に問1を実行してファイルを作成してください。";
            }

            // 1. 指定されたテキストブロックを書き込む
            String content = """
                    [INFO] アプリケーションを起動しました。
                    [ERROR] データベースへの接続に失敗しました。
                    [INFO] ユーザー入力を受け付けました。
                    [ERROR] 認証トークンが期限切れです。
                    """;
            Files.writeString(logFile, content);

            // 2. archiveディレクトリを作成
            if (!Files.exists(archiveDir)) {
                Files.createDirectories(archiveDir);
            }

            // 3. backup_app.logという名前でコピー
            Files.copy(logFile, backupFile, StandardCopyOption.REPLACE_EXISTING);

            return "<h3>問2 実行結果</h3>" +
                   "ファイルを書き込み、バックアップを作成しました:<br>" + backupFile;
        } catch (IOException e) {
            return "問2 エラー: " + e.getMessage();
        }
    }

    /**
     * 問3: [ERROR] 行の抽出とカウント (try-with-resources + Stream API)
     */
    @GetMapping("/exercise3")
    public String runExercise3() {
        if (!Files.exists(backupFile)) {
            return "エラー: 先に問2を実行してバックアップファイルを作成してください。";
        }

        // Files.lines()はメモリリーク防止のため try-with-resources で囲む
        try (Stream<String> lines = Files.lines(backupFile)) {
            StringBuilder sb = new StringBuilder("<h3>問3 実行結果 (エラーログ抽出)</h3>");
            
            // [ERROR]が含まれる行をフィルタリングして配列に収める
            String[] errorLines = lines
                    .filter(line -> line.contains("[ERROR]"))
                    .toArray(String[]::new);

            // 件数と中身を組み立て
            sb.append("<b>エラー件数: </b>").append(errorLines.length).append(" 件<br><br>");
            sb.append("<b>エラー内容:</b><br>");
            for (String errorLine : errorLines) {
                sb.append(errorLine).append("<br>");
            }

            return sb.toString();
        } catch (IOException e) {
            return "問3 エラー: " + e.getMessage();
        }
    }

    /**
     * 問4: ファイルとディレクトリのクリーンアップ削除 (内側から外側へ)
     */
    @GetMapping("/exercise4")
    public String runExercise4() {
        try {
            StringBuilder sb = new StringBuilder("<h3>問4 クリーンアップ結果</h3>");

            // 1. archive ディレクトリ内のファイルを削除 (Files.list() は要 try-with-resources)
            if (Files.exists(archiveDir)) {
                try (Stream<Path> files = Files.list(archiveDir)) {
                    files.forEach(file -> {
                        try {
                            Files.delete(file);
                            sb.append("ファイルを削除しました: ").append(file.getFileName()).append("<br>");
                        } catch (IOException e) {
                            throw new RuntimeException("ファイル削除失敗: " + file, e);
                        }
                    });
                }
                // 中身が空になったので archive ディレクトリを削除
                Files.delete(archiveDir);
                sb.append("ディレクトリを削除しました: archive<br>");
            }

            // 2. applog 直下のファイルを削除
            if (Files.exists(logFile)) {
                Files.delete(logFile);
                sb.append("ファイルを削除しました: dummy_error.log<br>");
            }

            // 3. 最後に外側の applog ディレクトリを削除
            if (Files.exists(applogDir)) {
                Files.delete(applogDir);
                sb.append("ディレクトリを削除しました: applog<br>");
            }

            return sb.toString();
        } catch (Exception e) {
            return "問4 クリーンアップ中にエラーが発生しました: " + e.getMessage();
        }
    }
}