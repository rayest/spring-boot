package com.rayest.redpackage;

import com.rayest.common.Note;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RedPacketController {

    @Resource
    private RedPacketService redPacketService;

    @Note("创建红包")
    @PostMapping("/red-package")
    public void add(@RequestBody RedPackageDTO redPackageDTO){
        redPacketService.save(redPackageDTO);
    }

    @Note("抢红包")
    @GetMapping("/red-package/{userNo}/{redPackageNo}")
    public RedPackageVO get(@PathVariable String userNo, @PathVariable String redPackageNo) throws Exception {
        return redPacketService.get(userNo, redPackageNo);
    }
}
