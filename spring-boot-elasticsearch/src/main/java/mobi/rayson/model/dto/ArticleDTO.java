package mobi.rayson.model.dto;

import lombok.Data;

@Data
public class ArticleDTO {
  private Long id;
  private String title;
  private String content;
  private Integer type;
  private String category;
  private String author;
  private Integer read;
  private Integer support;
}
