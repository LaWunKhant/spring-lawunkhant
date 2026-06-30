package com.cmps.spring.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/mail")
@Controller
public class MailSendController {

    private final JavaMailSender javaMailSender;

    @GetMapping("")
    public String index(Model model,
            @ModelAttribute("successMessage") String successMessage) {
        return "mail/index";
    }

    @PostMapping("/send")
    public String send(Model model,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String email,
            RedirectAttributes redirectAttributes) {

        SpringTemplateEngine engine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setCharacterEncoding("UTF-8");
        engine.setTemplateResolver(templateResolver);

        Map<String, Object> datas = new HashMap<>();
        datas.put("name", name);
        datas.put("content", content.replace("\n", "<br>"));
        datas.put("email", email);
        Context context = new Context();
        context.setVariables(datas);

        String htmlBody = engine.process("/templates/mail/send.html", context);

        try {
            MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            helper.setTo(email);
            helper.setSubject("テンプレートメール送信テスト");
            helper.setText(htmlBody, true);
            this.javaMailSender.send(mimeMessage);
            redirectAttributes.addFlashAttribute("successMessage", "メールの送信が完了しました。");
        } catch (Exception e) {
            e.getStackTrace();
            redirectAttributes.addFlashAttribute("successMessage", "メールの送信に失敗しました。");
        }

        return "redirect:/mail";
    }
    
}