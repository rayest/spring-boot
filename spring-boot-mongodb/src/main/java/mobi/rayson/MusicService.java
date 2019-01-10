package mobi.rayson;

import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-01-09
 *  Time: 5:19 PM
 *  Description:
 **/
@Service
public class MusicService {

  @Resource
  private MusicRepository musicRepository;

  public void save(Music music) {
    musicRepository.save(music);
  }

  public Music selectById(long id) {
    Optional<Music> optionalMusic = musicRepository.findById(id);
    return optionalMusic.orElse(null);
  }
}
