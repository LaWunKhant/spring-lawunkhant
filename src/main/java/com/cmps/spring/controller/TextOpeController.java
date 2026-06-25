package com.cmps.spring.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmps.spring.dto.TextOpeDto;
import com.cmps.spring.form.TextOpeForm;

@Controller
@RequestMapping("/textope")
public class TextOpeController {

	// プロジェクト内保存用のパス
	private static final Path DIR_PATH = Path.of("src", "main", "resources", "static", "text");

	/**
	 * 初期画面表示
	 */
	@GetMapping("")
	public String index(Model model,
			@ModelAttribute TextOpeForm textOpeForm,
			@ModelAttribute("resultMessage") String resultMessage) {

		List<TextOpeDto> list = new ArrayList<TextOpeDto>();

		if (Files.exists(DIR_PATH) && Files.isDirectory(DIR_PATH)) {
			try (Stream<Path> stream = Files.list(DIR_PATH)) {
				list = stream.filter(Files::isRegularFile)
					.map(path -> {
						TextOpeDto dto = new TextOpeDto();
						dto.setName(path.getFileName().toString());
						try {
							String content = Files.readString(path).replaceAll("\r\n|\n", "<br>");
							dto.setContent(content);
						} catch (IOException e) {
							dto.setContent("読み込み失敗");
						}
						return dto;
					})
					.collect(Collectors.toList());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// セレクトボックス用：ファイル一覧を取得
		List<String> fileNames = getFileNames();
		model.addAttribute("fileNames", fileNames);
		model.addAttribute("list", list);
		return "textope/index";
	}

	/**
	 * ファイルアップロード
	 */
	@PostMapping("/upload")
	public String upload(Model model,
			@Validated TextOpeForm textOpeForm, BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return index(model, textOpeForm, "");
		}

		Path path = DIR_PATH.resolve(Path.of(textOpeForm.getFile().getOriginalFilename()));

		try {
			Files.createDirectories(DIR_PATH);
			Files.copy(textOpeForm.getFile().getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("resultMessage", "ファイルの保存に失敗しました。");
			return "redirect:/textope";
		}

		redirectAttributes.addFlashAttribute("resultMessage", ".txtファイルのアップロードが完了しました。");
		return "redirect:/textope";
	}

	/**
	 * 特定ファイルへの追記（従来の固定ファイル版）
	 */
	@PostMapping("/append")
	public String appendFile(Model model,
			@RequestParam(required = false) String textPlus,
			RedirectAttributes redirectAttributes) {

		if (textPlus == null || textPlus.isBlank()) {
			redirectAttributes.addFlashAttribute("resultMessage", "追記テキストが入力されていません。");
			return "redirect:/textope";
		}

		Path path = DIR_PATH.resolve(Path.of("sample.txt"));

		try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
			bw.write(textPlus);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("resultMessage", "ファイルの書き込みに失敗しました。");
			return "redirect:/textope";
		}

		redirectAttributes.addFlashAttribute("resultMessage", ".txtファイルの書き込みが完了しました。");
		return "redirect:/textope";
	}

	/**
	 * 選択したファイルへの追記（練習問題 問1）
	 */
	@PostMapping("/append-selected")
	public String appendToSelectedFile(Model model,
			@ModelAttribute TextOpeForm textOpeForm,
			RedirectAttributes redirectAttributes) {

		// バリデーション
		if (textOpeForm.getSelectedFileName() == null || textOpeForm.getSelectedFileName().isBlank()) {
			redirectAttributes.addFlashAttribute("resultMessage", "ファイルを選択してください。");
			return "redirect:/textope";
		}

		if (textOpeForm.getTextPlus() == null || textOpeForm.getTextPlus().isBlank()) {
			redirectAttributes.addFlashAttribute("resultMessage", "追記テキストが入力されていません。");
			return "redirect:/textope";
		}

		// 選択されたファイル名でPathを取得
		Path path = DIR_PATH.resolve(Path.of(textOpeForm.getSelectedFileName()));

		// ファイルが存在するかチェック（セキュリティ対策）
		if (!Files.exists(path) || !Files.isRegularFile(path)) {
			redirectAttributes.addFlashAttribute("resultMessage", "選択されたファイルが見つかりません。");
			return "redirect:/textope";
		}

		try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
			bw.write(textOpeForm.getTextPlus());
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("resultMessage", "ファイルの書き込みに失敗しました。");
			return "redirect:/textope";
		}

		redirectAttributes.addFlashAttribute("resultMessage", "ファイルへの追記が完了しました。");
		return "redirect:/textope";
	}

	/**
	 * ディレクトリ内のファイル一覧を取得
	 */
	private List<String> getFileNames() {
		List<String> fileNames = new ArrayList<>();

		if (Files.exists(DIR_PATH) && Files.isDirectory(DIR_PATH)) {
			try (Stream<Path> stream = Files.list(DIR_PATH)) {
				fileNames = stream.filter(Files::isRegularFile)
					.map(path -> path.getFileName().toString())
					.collect(Collectors.toList());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return fileNames;
	}
}