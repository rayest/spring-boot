package mobi.rayson;

import org.springframework.data.mongodb.repository.MongoRepository;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-01-09
 *  Time: 5:21 PM
 *  Description:
 **/
public interface MusicRepository extends MongoRepository<Music, Long> {
}
