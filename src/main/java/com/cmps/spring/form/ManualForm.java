package com.cmps.spring.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;////追記
import jakarta.validation.constraints.NotNull;////追記
import org.hibernate.validator.constraints.Length;////追記
import org.hibernate.validator.constraints.Range;////追記
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.AssertTrue;

import lombok.Data;

@Data
public class ManualForm implements Serializable {

		// 名前
		@NotEmpty(message = "名前は必須項目です。")////追記
		@Length(max = 10, message = "名前は10文字以内で入力してください。") //// 追記
		private String userName;

		// 出身
		@NotEmpty(message = "出身は必須項目です。")////追記
		private String comeFrom;

		// 年齢
		@NotNull(message = "年齢は必須項目です。")////追記
		@Range(min = 0, max = 130, message = "年齢は0～130で入力してください。")////追記
		private Integer age;
		
		@NotEmpty
	    @Email // Validates basic email structure (must contain @)
	    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "{customForm.email.pattern}")
	    private String email;

	    // Getter and Setter
	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }
		
		 @NotNull(message = "開始日は入力必須です。")
		 private Integer lower;

		 // 期間(終了日)
		 @NotNull(message = "終了日は入力必須です。")
		 private Integer upper;

		@AssertTrue(message = "開始日は終了日以前を入力してください。")
		public boolean isDateValid() {
		    if (lower == null || upper == null) return true; //// nullチェック
		    if (lower <= upper) return true;
		    return false;
		 }
}