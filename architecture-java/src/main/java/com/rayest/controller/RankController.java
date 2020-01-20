package com.rayest.controller;

import com.rayest.common.Note;
import com.rayest.model.UsersRank;
import com.rayest.service.RankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

@Note("用户排行榜设计")
@RestController
public class RankController {

    @Resource
    private RankService rankService;

    @PutMapping("/rank/weekly")
    public void updateByWeekly(){
        rankService.updateByWeekly();
    }

    @GetMapping("/rank/weekly")
    public List<UsersRank> getWeekly(){
        return rankService.getWeekly();
    }

    @PostMapping("/rank/timely")
    public void addOrUpdateRank(@RequestParam String username){
        rankService.addOrUpdateRank(username);
    }
}
