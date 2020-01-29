package com.rayest.redpackage;

import com.rayest.common.Note;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RestController
public class RedEnvelopeController {

    @Resource
    private RedEnvelopeService redEnvelopeService;

    @Note("创建红包")
    @PostMapping("/red-envelope")
    public void add(@RequestBody RedEnvelopeDTO redEnvelopeDTO){
        redEnvelopeService.save(redEnvelopeDTO);
    }

    @Note("抢红包")
    @GetMapping("/red-envelope/{userNo}/{redEnvelopeNo}")
    public RedEnvelopeVO get(@PathVariable String userNo, @PathVariable String redEnvelopeNo) throws Exception {
        return redEnvelopeService.get(userNo, redEnvelopeNo);
    }

    // ==================== Lua 实现 ====================================

    @Resource
    private DefaultRedisScript<Boolean> redisScript;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/lua")
    public Boolean lua() {
        List<String> keys = Arrays.asList("key-name", "value-lee");
        return stringRedisTemplate.execute(redisScript, keys, "100");
    }

    @GetMapping("/lua/red-envelope/{redEnvelopeNo}/{userNo}")
    public void getWithLua(@PathVariable String redEnvelopeNo, @PathVariable String userNo) throws Exception {
        redEnvelopeService.getWithLua(redEnvelopeNo, userNo);
    }

    // ==================== 乐观锁 实现 ====================================
    @RequestMapping("/lock/version/red-envelope/{redEnvelopeNo}/{userNo}")
    public void getWithVersion(@PathVariable String redEnvelopeNo, @PathVariable String userNo) throws Exception {
        redEnvelopeService.getWithVersion(redEnvelopeNo, userNo);
    }
}
