package com.cmps.spring.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.net.URLEncoder;

import org.apache.commons.io.input.BOMInputStream;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cmps.spring.dto.CsvDto;
import com.cmps.spring.entity.Csv;
import com.cmps.spring.repository.CsvRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CsvService {

    private final CsvRepository csvRepository;

    // プロジェクト内保存用のパス
    private static final Path PROJECT_CSV_PATH =
        Paths.get("src", "main", "resources", "static", "csv", "sample.csv").toAbsolutePath();

    /**
     * テーブルからEntity全件取得
     */
    public List<Csv> findAll() {
        return csvRepository.findAll();
    }

    /**
     * アップロードされたCSVを解析してDB登録する。登録後のリストを返す
     */
    public List<Csv> uploadCsv(MultipartFile file) throws IOException {
        List<Csv> list = new ArrayList<Csv>();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .setUseHeader(true)
                .setColumnSeparator(',')
                .setQuoteChar('"')
                .setEscapeChar('\"')
                .setLineSeparator("\r\n")
                .build();

        try (InputStream inputStream = file.getInputStream();
                BOMInputStream bomIn = BOMInputStream.builder().setInputStream(inputStream).get();
                BufferedReader br = new BufferedReader(new InputStreamReader(bomIn, StandardCharsets.UTF_8))) {

            MappingIterator<CsvDto> objectMappingIterator =
                    mapper.readerFor(CsvDto.class)
                        .with(schema)
                        .readValues(br);

            while (objectMappingIterator.hasNext()) {
                CsvDto dto = objectMappingIterator.next();
                Csv csv = new Csv(dto.getCode(), dto.getName(), dto.getAge(), dto.getAddress());
                list.add(csv);
            }
            csvRepository.saveAll(list);
        }
        return list;
    }

    /**
     * csvsテーブル全件のList<CsvDto>を取得
     */
    public List<CsvDto> getCsvList() {
        List<CsvDto> csvList = new ArrayList<>();
        List<Csv> allData = csvRepository.findAll();
        for (Csv data : allData) {
            CsvDto csvDto = new CsvDto(data.getCode(), data.getName(), data.getAge(), data.getAddress());
            csvList.add(csvDto);
        }
        return csvList;
    }

    /**
     * プロジェクト内の特定の場所にCSVを出力する
     */
    public void writeCsvToProject() throws IOException {
        List<CsvDto> csvList = getCsvList();

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(CsvDto.class).withHeader();

        Files.createDirectories(PROJECT_CSV_PATH.getParent());
        if (!Files.exists(PROJECT_CSV_PATH)) {
            Files.createFile(PROJECT_CSV_PATH);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(PROJECT_CSV_PATH, StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING)) {
            mapper.writer(schema).writeValues(writer).writeAll(csvList);
        }
    }

    /**
     * プロジェクト内のCSVファイルをResourceとして取得
     */
    public Resource getProjectCsvResource() {
        return new PathResource(PROJECT_CSV_PATH);
    }

    /**
     * ダウンロード用のHttpHeadersを生成するヘルパーメソッド
     */
    public HttpHeaders createDownloadHeaders(String filename, MediaType mediaType) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"");
        headers.setContentType(mediaType);
        return headers;
    }

    /**
     * DBデータをCSV形式のバイト配列に変換する（即時ダウンロード用）
     */
    public byte[] exportCsvBytes() throws JsonProcessingException {
        List<CsvDto> csvList = getCsvList();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(CsvDto.class).withHeader();
        return mapper.writer(schema).writeValueAsBytes(csvList);
    }
}