package mobi.rayson.mapper;

import mobi.rayson.model.domain.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {
  Long countArticlesByAuthor(String author);
}
