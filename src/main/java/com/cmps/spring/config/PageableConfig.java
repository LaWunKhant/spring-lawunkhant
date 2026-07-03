package com.cmps.spring.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PageableConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        // PageableインターフェースをControllerで受け取るためのリゾルバ
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();

        // URLにpageやsizeが無い場合のデフォルト値を設定（第一引数：ページ番号、第二引数：1ページあたりの表示件数）
        resolver.setFallbackPageable(PageRequest.of(0, 3));

        // カスタマイズした具体的な設定（resolver）をリゾルバに追加
        argumentResolvers.add(resolver);
    }
}