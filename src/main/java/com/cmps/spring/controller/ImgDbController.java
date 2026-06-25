package com.cmps.spring.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cmps.spring.dto.ImgDbDto;
import com.cmps.spring.entity.Image;
import com.cmps.spring.form.ImgForm;
import com.cmps.spring.service.ImageService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/imgupDB")
public class ImgDbController {

	private final ImageService imageService;

	/**
	 * 画像アップロードDB版 初期表示画面
	 * @param model Model
	 * @param imgForm ImgForm
	 * @param resultMessage String
	 * @return
	 */
	@GetMapping("")
	public String index(Model model,
			@ModelAttribute ImgForm imgForm,
			@ModelAttribute("resultMessage") String resultMessage) {

		// Viewに渡すList ImgDbDtoオブジェクトに必要な情報を詰めて渡す
		List<ImgDbDto> list = imageService.findAllAsDto();

		model.addAttribute("list", list);
		return "img/indexDB";
	}

	/**
	 * 画像アップロード DBに保存する（問2：バリデーション付き）
	 * @param model Model
	 * @param imgForm ImgForm
	 * @param result BindingResult
	 * @param redirectAttributes RedirectAttributes
	 * @return
	 */
	@PostMapping("/upload")
	public String upload(Model model,
			@Validated ImgForm imgForm, BindingResult result,
			RedirectAttributes redirectAttributes) {

		// バリデーションエラー返却
		if (result.hasErrors()) {
			return index(model, imgForm, "");
		}

		try {
			Image imageEntity = new Image(
					imgForm.getFile().getBytes(),
					imgForm.getFile().getOriginalFilename(),
					imgForm.getFile().getContentType()
			);

			imageService.saveEntityWithDate(imageEntity);

		} catch (Exception e) {
			e.printStackTrace();
			// エラーの詳細をログに出力
			System.out.println("Error: " + e.getMessage());
			System.out.println("Cause: " + e.getCause());
			
			redirectAttributes.addFlashAttribute("resultMessage", 
				"ファイルの保存に失敗しました: " + e.getMessage());
			return "redirect:/imgupDB";
		}

		redirectAttributes.addFlashAttribute("resultMessage", "画像ファイルのアップロードが完了しました。");
		return "redirect:/imgupDB";
	}
}