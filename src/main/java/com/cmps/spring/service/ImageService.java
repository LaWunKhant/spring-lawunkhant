package com.cmps.spring.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cmps.spring.dto.ImgDbDto;
import com.cmps.spring.entity.Image;
import com.cmps.spring.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;

	/**
	 * 受け取ったEntityをDBに登録する処理
	 * @param imageEntity Image
	 */
	public void saveEntity(Image imageEntity) {
		imageRepository.save(imageEntity);
	}

	/**
	 * 【問3】ファイル名に日付を追加してDBに登録する処理
	 * image.pngであれば「image2025-03-28.png」のようにファイル名を変更して保存
	 * @param imageEntity Image
	 */
	public void saveEntityWithDate(Image imageEntity) {
		// 元のファイル名を取得
		String originalName = imageEntity.getName();

		// ファイル名と拡張子を分割（最後の「.」で分割）
		// "image.png" → ["image", "png"]
		int lastDotIndex = originalName.lastIndexOf(".");
		String fileName;
		String extension;

		if (lastDotIndex > 0) {
			// 拡張子がある場合
			fileName = originalName.substring(0, lastDotIndex);
			extension = originalName.substring(lastDotIndex + 1);
		} else {
			// 拡張子がない場合
			fileName = originalName;
			extension = "";
		}

		// 現在の日付を取得（YYYY-MM-DD形式）
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateStr = today.format(formatter);

		// ファイル名に日付を追加
		// "image" + "2025-03-28" + ".png" = "image2025-03-28.png"
		String newFileName;
		if (extension.isEmpty()) {
			newFileName = fileName + dateStr;
		} else {
			newFileName = fileName + dateStr + "." + extension;
		}

		// エンティティの名前を更新
		imageEntity.setName(newFileName);

		// DBに保存
		imageRepository.save(imageEntity);
	}

	/**
	 * 画面出力するDTO形式でデータを全件取得
	 * @return List<ImgDbDto>
	 */
	public List<ImgDbDto> findAllAsDto() {
		// DBより全件取得してStreamAPIで整形
		return imageRepository.findAll().stream().map(image -> {
			// データをBase64エンコード
			String encoded = Base64.getEncoder().encodeToString(image.getData());
			// "data: image/png ;base64,【Base64文字列】"の形式に整形
			String srcText = "data:" + image.getContentType() + ";base64," + encoded;
			ImgDbDto dto = ImgDbDto.builder()
					.name(image.getName())
					.contentType(image.getContentType())
					.srcText(srcText)
					.build();
			return dto;
		}).toList();
	}
}