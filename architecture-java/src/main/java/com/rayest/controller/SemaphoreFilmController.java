package com.rayest.controller;

import com.rayest.service.SemaphoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SemaphoreFilmController {

    @Resource
    private SemaphoreService semaphoreService;

    @GetMapping("/film/id/{filmId}")
    public void getById(@PathVariable String filmId) {
        semaphoreService.getById(filmId);
    }

}
