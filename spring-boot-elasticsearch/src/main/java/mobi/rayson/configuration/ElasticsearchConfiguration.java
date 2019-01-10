package mobi.rayson.configuration;

import java.net.InetAddress;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-11-28
 *  Time: 10:53 AM
 *  Description:
 **/
@Configuration
@EnableElasticsearchRepositories(basePackages = "mobi.rayson")
@Slf4j
public class ElasticsearchConfiguration {

  @Value("${spring.data.elasticsearch.cluster-nodes}")
  private String clusterNodes;

  @Value("${spring.data.elasticsearch.cluster-name}")
  private String clusterName;

  @Bean
  public Client client() throws Exception {
    Settings settings = Settings.builder()
        .put("cluster.name", clusterName)
        .build();

    TransportClient client = new PreBuiltTransportClient(settings);
    TransportAddress address =
        new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300);
    client.addTransportAddress(address);
    return client;


  }

  @Bean
  public ElasticsearchOperations elasticsearchTemplate() throws Exception {
    return new ElasticsearchTemplate(client());
  }
}
