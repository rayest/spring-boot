package mobi.rayson.mapper;

import mobi.rayson.model.SuccessKilled;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-11
 *  Time: 1:45 PM
 *  Description:
 **/
public interface SuccessKilledMapper {
  long countBySeckillId(long seckillId);

  void save(SuccessKilled successKilled);
}
