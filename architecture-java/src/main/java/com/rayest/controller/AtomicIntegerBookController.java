package com.rayest.controller;

import com.rayest.service.AtomicIntegerService;
import com.rayest.service.SemaphoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AtomicIntegerBookController {

    @Resource
    private AtomicIntegerService atomicIntegerService;

    @GetMapping("/book/id/{bookId}")
    public void getById(@PathVariable String bookId) {
        atomicIntegerService.getById(bookId);
    }

}
