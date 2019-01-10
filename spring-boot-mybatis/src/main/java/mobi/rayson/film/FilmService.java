package mobi.rayson.film;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-12-30
 *  Time: 11:22 AM
 *  Description:
 **/
@Service
public class FilmService {

  @Resource
  private FilmMapper filmMapper;

  public boolean getByName(String name) {
    return filmMapper.isFilmExist(name);
  }
}
