package com.cmps.spring.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cmps.spring.service.CsvService;
import com.lowagie.text.DocumentException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PdfController {

    private final CsvService csvService;

    @GetMapping("/pdfConvert")
    public String index(Model model) {
        return "pdf/sample";
    }

    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf() throws IOException, DocumentException {
        TemplateEngine engine = initializeTemplateEngine();

        Map<String, Object> datas = new HashMap<>();
        List<Map<String, String>> list = getList();
        datas.put("list", list);

        Context context = new Context();
        context.setVariables(datas);

        ITextRenderer renderer = new ITextRenderer();
        String htmlContent = engine.process("pdf/content", context);
        String baseUrl = new ClassPathResource("static/").getURL().toString();

        renderer.setDocumentFromString(htmlContent, baseUrl);
        renderer.layout();

        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        renderer.createPDF(byteOutStream);
        byte[] pdfBytes = byteOutStream.toByteArray();

        String filename = "fruits_list.pdf";
        HttpHeaders headers = csvService.createDownloadHeaders(filename, MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    private TemplateEngine initializeTemplateEngine() {
        final TemplateEngine templateEngine = new TemplateEngine();
        final ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setTemplateMode("XHTML");
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(resolver);
        return templateEngine;
    }

    public List<Map<String, String>> getList() {
        Map<String, String> apple = new HashMap<>();
        apple.put("product", "リンゴ");
        apple.put("price", "100");
        apple.put("from", "青森県");

        Map<String, String> banana = new HashMap<>();
        banana.put("product", "バナナ");
        banana.put("price", "200");
        banana.put("from", "フィリピン");

        Map<String, String> orange = new HashMap<>();
        orange.put("product", "みかん");
        orange.put("price", "350");
        orange.put("from", "愛媛県");

        List<Map<String, String>> list = new ArrayList<>();
        list.add(apple);
        list.add(banana);
        list.add(orange);
        return list;
    }
}