package mobi.rayson;

import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-01-09
 *  Time: 5:50 PM
 *  Description:
 **/
@Configuration
public class MongoConfiguration {

  @Resource
  private MongoDbFactory mongoDbFactory;

  @Bean
  public MongoDbFactory mongoDBFactory() {
    return new SimpleMongoDbFactory(new MongoClient(), "test");
  }

  @Bean
  public MongoTemplate mongoTemplate() {

    DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);

    // Remove _class
    MappingMongoConverter converter =
        new MappingMongoConverter(dbRefResolver, new MongoMappingContext());
    converter.setTypeMapper(new DefaultMongoTypeMapper(null));
    return new MongoTemplate(mongoDBFactory(), converter);
  }
}
