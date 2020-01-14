package mobi.rayson.film;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

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

    @GetMapping("/film/{name}/exist")
    public boolean exist(@PathVariable("name") String name) {
        return filmService.exist(name);
    }

    @GetMapping("/film/{name}")
    public Film getByName(@PathVariable("name") String name) {
        return filmService.getByName(name);
    }

    @GetMapping("/film/cache/{name}")
    public Film fromCache(@PathVariable("name") String name) {
        return filmService.getFromLocalCache(name);
    }

    @GetMapping("/film/cache")
    public ConcurrentHashMap<String, Film> getFilmsFromLocalCache() {
        return filmService.getFilmsFromLocalCache();
    }

    @PostMapping("/film/cache/{name}")
    public void toCache(@PathVariable("name") String name) {
        filmService.put(name);
    }
}
