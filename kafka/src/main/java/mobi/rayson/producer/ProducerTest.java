package mobi.rayson.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("acks", "-1");
        properties.put("retries", 3);
        properties.put("batch.size", 323840);
        properties.put("linger.ms", 10);
        properties.put("max.block.ms", 3000);

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 100; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("my-topic", Integer.toString(i), "Hello " + i);
            producer.send(producerRecord, (metadata, exception) -> {
                if (exception == null) {
                    System.out.println(metadata);
                    System.out.println("消息发送成功");
                } else {
                    System.out.println("消息发送失败");
                }
            });
        }

        producer.close();

    }
}
