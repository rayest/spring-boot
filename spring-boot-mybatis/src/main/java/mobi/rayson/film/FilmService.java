package mobi.rayson.film;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

  public boolean exist(String name) {
    return filmMapper.isFilmExist(name);
  }

  public Film getByName(String name) {
    return filmMapper.getByName(name);
  }
}
