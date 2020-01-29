package com.rayest.secondkill;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SecondKillController {

    @Resource
    private SecondKillService secondKillService;

    @PostMapping("/management/sec-kill/good")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(){
        secondKillService.add();
    }

    @GetMapping("/sec-kill/good/{goodNo}")
    public void getGoodDetail(@PathVariable String goodNo){
        secondKillService.getGoodDetail(goodNo);
    }

    @PostMapping("/sec-kill/good/{goodNo}/{userNo}")
    public void kill(@PathVariable String goodNo, @PathVariable String userNo) throws Exception {
        secondKillService.kill(goodNo, userNo);
    }
}
