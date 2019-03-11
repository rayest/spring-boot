package mobi.rayson.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-11
 *  Time: 5:57 PM
 *  Description:
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seckill {
  private long seckillId;
  private int version;
  private int number;
}
