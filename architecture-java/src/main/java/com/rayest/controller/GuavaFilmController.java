package com.rayest.controller;

import com.rayest.service.GuavaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class GuavaFilmController {

    @Resource
    private GuavaService filmService;

    @GetMapping("/guava/film/id/{filmId}")
    public String getById(@PathVariable String filmId) {
        return filmService.getById(filmId);
    }

    @PutMapping("/guava/film/id/{filmId}")
    public void update(@PathVariable String filmId) {
        filmService.update(filmId);
    }
}
