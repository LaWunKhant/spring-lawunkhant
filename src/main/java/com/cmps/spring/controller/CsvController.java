package com.cmps.spring.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmps.spring.entity.Csv;
import com.cmps.spring.form.CsvForm;
import com.cmps.spring.service.CsvService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/csv")
@Controller
public class CsvController {

    private final CsvService csvService;

    /**
     * フォーム初期表示画面
     */
    @GetMapping("")
    public String index(Model model, @ModelAttribute("csvForm") CsvForm csvForm,
            @ModelAttribute("resultMessage") String resultMessage, 
            @ModelAttribute("list") ArrayList<Csv> list) {
        List<Csv> allList = csvService.findAll();
        // findAll() should never return null, but just in case
        if (allList == null) {
            allList = new ArrayList<>();
        }
        model.addAttribute("allList", allList);
        return "csv/index";
    }

    /**
     * CSVファイルをアップロード・DB登録機能
     */
    @PostMapping("/upload")
    public String upload(Model model, @Validated CsvForm csvForm, BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            List<Csv> allList = csvService.findAll();
            model.addAttribute("allList", allList);
            return "csv/index";
        }

        try {
            List<Csv> list = csvService.uploadCsv(csvForm.getFile());
            redirectAttributes.addFlashAttribute("resultMessage", "CSVのアップロードが完了しました。");
            redirectAttributes.addFlashAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            result.rejectValue("file", "", "ファイル読み込みに失敗しました");
            return "csv/index";
        }
        return "redirect:/csv";
    }

    /**
     * テーブル全件をCSVファイルとしてプロジェクト内のフォルダに出力する処理
     */
    @PostMapping("/output")
    public String output(RedirectAttributes redirectAttributes) {
        try {
            csvService.writeCsvToProject();
            redirectAttributes.addFlashAttribute("resultMessage", "プロジェクト内への出力が完了しました。");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("resultMessage", "CSV出力に失敗しました。");
        }
        return "redirect:/csv";
    }

    /**
     * プロジェクト内のCSVファイルをダウンロードさせる処理
     */
    @PostMapping("/output-download")
    @ResponseBody
    public ResponseEntity<Resource> outputFileDownload() throws UnsupportedEncodingException {
        Resource resource = csvService.getProjectCsvResource();
        String filename = "CSVファイルをプロジェクト内に出力.csv";
        HttpHeaders headers = csvService.createDownloadHeaders(filename, new MediaType("text", "csv"));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /**
     * テーブル全件をCSVファイルにしてダウンロードさせる処理
     */
    @PostMapping("/download")
    @ResponseBody
    public ResponseEntity<byte[]> download(
            @RequestParam(value = "fileName", defaultValue = "download") String fileName)
            throws JsonProcessingException, UnsupportedEncodingException {

        byte[] csvBytes = csvService.exportCsvBytes();

        // 入力されたファイル名に.csvを付ける
        String filename = fileName + ".csv";
        HttpHeaders headers = csvService.createDownloadHeaders(filename, new MediaType("text", "csv"));
        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}