package mobi.rayson;

import javax.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-01-09
 *  Time: 4:32 PM
 *  Description:
 **/
@RestController
public class MusicController {

  @Resource
  private MusicService musicService;

  @PostMapping("/music")
  public ResponseEntity save(@RequestBody Music music) {
    musicService.save(music);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/music/id/{id}")
  public ResponseEntity selectById(@PathVariable("id") long id) {
    Music music = musicService.selectById(id);
    return ResponseEntity.ok(music);
  }
}
