package com.cmps.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/file")
@RestController
public class FileOpeController {

    private final Path applogDir = Path.of("src", "main", "resources", "static", "applog");
    private final Path logFile = applogDir.resolve("dummy_error.log");
    private final Path archiveDir = applogDir.resolve("archive");
    private final Path backupFile = archiveDir.resolve("backup_app.log");

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