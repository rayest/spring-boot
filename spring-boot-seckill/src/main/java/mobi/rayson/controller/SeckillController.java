package mobi.rayson.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import mobi.rayson.service.SeckillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-11
 *  Time: 1:13 PM
 *  Description:
 **/
@Api("秒杀")
@RestController
public class SeckillController {

  @Resource
  private SeckillService seckillService;

  @ApiOperation("秒杀一。可能会出现超卖")
  @PostMapping("/seckill/one/{seckillId}")
  public ResponseEntity seckillOne(@PathVariable long seckillId) {
    seckillService.seckillOne(seckillId);
    return ResponseEntity.ok().build();
  }

  @ApiOperation("秒杀二。使用可重入锁:lock")
  @PostMapping("/seckill/two/{seckillId}")
  public ResponseEntity seckillTwo(@PathVariable long seckillId) {
    seckillService.seckillTwo(seckillId);
    return ResponseEntity.ok().build();
  }
}
