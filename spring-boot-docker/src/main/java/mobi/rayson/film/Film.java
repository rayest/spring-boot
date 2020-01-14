package mobi.rayson.film;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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
  private LocalDateTime releaseTime;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
}
