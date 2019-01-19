package mobi.rayson.auth.event;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevokeTokenEventEntry implements Serializable {

  private String userName;
  private String clientId;

}
