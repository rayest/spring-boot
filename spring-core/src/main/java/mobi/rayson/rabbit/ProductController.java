package mobi.rayson.rabbit;

import javax.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-08-28
 *  Time: 下午3:06
 *  Description:
 **/
@RestController
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping("/rabbit/product")
    public ResponseEntity create(@RequestBody ProductDTO productDTO){
        return productService.create(productDTO);
    }
}
