package mobi.rayson.endpoints;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-12-30
 *  Time: 11:21 AM
 *  Description:
 **/
@RestController
public class TestEndpoints {

  @GetMapping("/product/{id}")
  public String getProduct(@PathVariable String id) {
    //for debug
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return "product id : " + id;
  }

  @GetMapping("/order/{id}")
  public String getOrder(@PathVariable String id) {
    //for debug
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return "order id : " + id;
  }

}
