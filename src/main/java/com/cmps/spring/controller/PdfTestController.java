package com.cmps.spring.controller;

import java.io.File;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.apache.fontbox.ttf.TrueTypeCollection;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

@Controller
public class PdfTestController {

    @GetMapping("/pdfTest")
    public String pdfTest(Model model) {

        // 出力先の設定
        // resourcesフォルダの絶対パスを取得
        String resourcePath = Paths.get("src/main/resources/PDF/").toAbsolutePath().toString();

        // 出力先フォルダを確認し、存在しない場合は作成
        File outputDir = new File(resourcePath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // 出力ファイルパス・ファイル名も設定
        File outputFile = new File(outputDir, "output.pdf");

        try {
            // ドキュメントオブジェクトの作成
            PDDocument document = new PDDocument();
            
            // ページオブジェクトの作成
            PDPage page = new PDPage();
            document.addPage(page);
            
            // ↓ ここに追加 (add here)
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            
            // 日本語の場合
            File fontFile = new File("C:/Windows/Fonts/msmincho.ttc");
            TrueTypeCollection collection = new TrueTypeCollection(fontFile);
            PDFont font = PDType0Font.load(document, collection.getFontByName("MS-Mincho"), true);

//            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), 20);
//            contentStream.newLineAtOffset(100f, 400f);
//            contentStream.showText("Hello World!");
//            contentStream.endText();
//            contentStream.close();
            // ↑ ここまで
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(100f, 400f);
            contentStream.showText("こんにちは");
            contentStream.endText();
            contentStream.close();
            
            // ドキュメントの保存
            document.save(outputFile);
            document.close();
            
            model.addAttribute("text", "成功しました。");
            return "pdf/pdfTest";
        } catch (Exception e) {
            e.printStackTrace();

            model.addAttribute("text", "失敗しました。");
            return "pdf/pdfTest";
        }
    }
}