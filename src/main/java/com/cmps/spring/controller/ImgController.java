package com.cmps.spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmps.spring.dto.ImgDto;
import com.cmps.spring.form.ImgForm;

@Controller
@RequestMapping("/imgup")
public class ImgController {

	// プロジェクト内保存用のパス
	private static final Path DIR_PATH = Path.of("src", "main", "resources", "static", "images");

	/**
	 * 初期画面表示
	 * @param model Model
	 * @param resultMessage String
	 * @return
	 */
	@GetMapping("")
	public String index(Model model,
			@ModelAttribute ImgForm imgForm,
			@ModelAttribute("resultMessage") String resultMessage) {

		// Viewに渡すList ImgDtoオブジェクトに必要な情報を詰めて渡す
		List<ImgDto> list = new ArrayList<ImgDto>();

		// ディレクトリが存在しない場合のガード
		if (Files.exists(DIR_PATH) && Files.isDirectory(DIR_PATH)) {
			// Files.listでStreamを取得し、StreamAPIで処理を行う
			try (Stream<Path> stream = Files.list(DIR_PATH)) {
				list = stream.filter(Files::isRegularFile)// ファイルを対象にする（ディレクトリは除外）
					.map(path -> {
						// DTOオブジェクトをmap内で整形
						ImgDto dto = new ImgDto();
						dto.setName(path.getFileName().toString());// ファイル名を格納

						// ファイルのパスをStringで取得して格納したい
						// 直接取得できるのはパス ～src/main/resources/static/images/filename.jpg だが、
						// thymeleafのリンク式に指定して表示できるのはstatic配下のパス(/images/～)であるため、不要な部分を除去する
						// 後続のrelativizeメソッドでエラーが出ないよう土俵を「絶対パス」に揃える
						Path staticDir = DIR_PATH.getParent().toAbsolutePath();// ～static/までのパス
						Path absPath = path.toAbsolutePath();//各ファイルまでのフルパス ～static/images/filename.jpg

						// staticディレクトリからの「相対パス」を算出する(relativizeメソッド)ことで、OSを問わず /images/～ を取得する
						// Windows環境でrelativizeを実行した場合、Windowsでしか使えない \ 区切りになる。→ 汎用的にするため / に変換
						String relPath = "/" + staticDir.relativize(absPath).toString().replace("\\", "/");// /images/filename.jpg
						// DTOオブジェクトにrelPathを格納
						dto.setPath(relPath);
						return dto;
					})
					.collect(Collectors.toList());// Listとして取得
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("list", list);
		return "img/index";
	}

	/**
	 * ファイルアップロード resources/static/imagesに保存する
	 * @param model Model
	 * @param redirectAttributes String
	 * @return
	 */
	@PostMapping("/upload")
	public String upload(Model model,
			@Validated ImgForm imgForm, BindingResult result,
			RedirectAttributes redirectAttributes) {

		//バリデーションエラー返却
		if (result.hasErrors()) {
			return index(model, imgForm, "");
		}

		//書き込み対象ファイル
		Path path = DIR_PATH.resolve(Path.of(imgForm.getFile().getOriginalFilename()));

		try {
			//ファイルの保存
			imgForm.getFile().transferTo(path);

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("resultMessage", "ファイルの保存に失敗しました。");
			return "redirect:/imgup";
		}

		// redirectAttributesに登録
		redirectAttributes.addFlashAttribute("resultMessage", "画像ファイルのアップロードが完了しました。");
		return "redirect:/imgup";
	}
}
