package mobi.rayson;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-01-09
 *  Time: 5:20 PM
 *  Description:
 **/
@Data
@Document(collection = "music")
public class Music {

  @Id
  private long id;
  private String name;
  private String singer;
}
