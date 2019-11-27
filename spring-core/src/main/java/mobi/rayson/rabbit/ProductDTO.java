package mobi.rayson.rabbit;

import java.io.Serializable;
import lombok.Data;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-08-28
 *  Time: 下午3:33
 *  Description:
 **/
@Data
public class ProductDTO implements Serializable {
    private String name;
    private String price;
}
