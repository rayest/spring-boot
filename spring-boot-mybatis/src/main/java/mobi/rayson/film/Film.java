package mobi.rayson.film;

import com.sun.xml.internal.ws.spi.db.DatabindingException;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-12-30
 *  Time: 11:56 AM
 *  Description:
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {
  private int id;
  private String name;
  private String director;
  private Date releaseTime;
  private Date createDate;
  private Date updateDate;
}
