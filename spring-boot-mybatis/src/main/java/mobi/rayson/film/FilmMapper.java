package mobi.rayson.film;

import org.apache.ibatis.annotations.Param;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-12-29
 *  Time: 10:31 AM
 *  Description:
 **/
public interface FilmMapper {
  boolean isFilmExist(@Param("name") String name);

  Film getByName(String name);
}
