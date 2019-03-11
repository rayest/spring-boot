package mobi.rayson.mapper;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-11
 *  Time: 1:44 PM
 *  Description:
 **/
public interface SeckillMapper {
  long countLeft(long seckillId);

  void updateNumber(long seckillId);
}
