package com.cmps.spring.form;

import java.io.Serializable;
import org.springframework.web.multipart.MultipartFile;

import com.cmps.spring.validation.annotation.FileExtension;
import com.cmps.spring.validation.annotation.FileNotEmpty;
import com.cmps.spring.validation.annotation.FileSize;

import lombok.Data;

@Data
public class CsvForm implements Serializable {

    // CSVファイル
    @FileNotEmpty                          // ファイルが添付されているか
    @FileSize(max = 100 * 1024)           // 100kB以下か
    @FileExtension(regExp = "csv")        // 拡張子がcsvか
    private MultipartFile file;
}