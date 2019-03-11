package mobi.rayson.mapper;

import mobi.rayson.model.Seckill;
import org.apache.ibatis.annotations.Param;

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

  long selectNumberWithPessimisticLock(long seckillId);

  Seckill selectBySeckillId(long seckillId);

  int update(@Param("seckillId") long seckillId, @Param("version") int version,
      @Param("number") int number);
}
