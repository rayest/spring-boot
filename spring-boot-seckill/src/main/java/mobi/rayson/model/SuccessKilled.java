package mobi.rayson.model;

import java.io.Serializable;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-11
 *  Time: 1:50 PM
 *  Description:
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessKilled implements Serializable {
  private static final long serialVersionUID = 1L;
  private long seckillId;
  private long userId;
  private short state;
  private Date createTime;
}
