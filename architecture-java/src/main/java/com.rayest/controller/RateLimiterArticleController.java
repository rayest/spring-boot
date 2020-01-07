package com.rayest.controller;

import com.rayest.service.RateLimiterArticleService;
import com.rayest.service.SemaphoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RateLimiterArticleController {

    @Resource
    private RateLimiterArticleService articleService;

    @GetMapping("/article/id/{articleId}")
    public void getById(@PathVariable String articleId) {
        articleService.getById(articleId);
    }
}
