package mobi.rayson.film;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-12-30
 *  Time: 11:22 AM
 *  Description:
 **/
@Service
public class FilmService {

    private ConcurrentHashMap<String, Film> films = new ConcurrentHashMap<>();

    @Resource
    private FilmMapper filmMapper;

    public boolean exist(String name) {
        return filmMapper.isFilmExist(name);
    }

    public Film getByName(String name) {
        return filmMapper.getByName(name);
    }

    public Film getFromLocalCache(String name) {
        Film film = films.get(name);
        return film == null ? new Film() : film;
    }

    public ConcurrentHashMap<String, Film> getFilmsFromLocalCache() {
       return this.films;
    }

    public void put(String name) {
        Film film = new Film();
        film.setId(new Random().nextInt());
        film.setName(name);
        film.setCreateDate(LocalDateTime.now());
        film.setUpdateDate(LocalDateTime.now());
        films.putIfAbsent(name, film);
    }
}
