package mobi.rayson.rabbit;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static mobi.rayson.rabbit.RabbitMQConfiguration.TEST_RABBIT_USER_QUEUE;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-08-28
 *  Time: 下午3:08
 *  Description:
 **/
@Service
@Slf4j
public class ProductService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public ResponseEntity create(ProductDTO productDTO) {
        Map<String, ProductDTO> map = new HashMap<>();
        map.put("productDTO", productDTO);
        log.info("Sending message to queue...");
        rabbitTemplate.convertAndSend(TEST_RABBIT_USER_QUEUE, map);
        return ResponseEntity.ok().build();
    }
}
