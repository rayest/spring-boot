package mobi.rayson.film;

import javax.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-12-30
 *  Time: 11:21 AM
 *  Description:
 **/
@RestController
public class FilmController {

  @Resource
  private FilmService filmService;

  @GetMapping("/film/{name}")
  public ResponseEntity getByName(@PathVariable("name") String name) {
    boolean isFilmExist = filmService.getByName(name);
    return ResponseEntity.ok(isFilmExist);
  }
}
