package mobi.rayson.service;

import java.util.List;
import mobi.rayson.model.domain.Article;
import mobi.rayson.model.dto.ArticleDTO;
import mobi.rayson.model.dto.ArticleSearchDTO;
import mobi.rayson.model.vo.PageBean;

public interface ArticleService {

  boolean save(List<ArticleDTO> articleDTOList);

  PageBean search(ArticleSearchDTO articleSearchDTO);

  Article getById(long id);

  void update(Article article);

  void delete(long id);

  long countByAuthor(String author);
}
