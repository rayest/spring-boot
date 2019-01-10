package mobi.rayson.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "article", type = "poet")
public class Article implements Serializable {

  @Id
  private Long id;
  private String title;
  private String content;
  // 文章类型 1:诗歌 2:散文
  private Integer type;
  // 文章类别
  private String category;
  // 文章阅读数
  private Integer read;
  // 问题支持数
  private Integer support;
  private String author;
}
