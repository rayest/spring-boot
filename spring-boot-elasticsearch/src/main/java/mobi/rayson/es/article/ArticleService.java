package mobi.rayson.es.article;

import lombok.extern.slf4j.Slf4j;
import mobi.rayson.es.article.dto.ArticleDTO;
import mobi.rayson.es.article.dto.ArticleSearchDTO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ArticleService {

    @Resource
    private ArticleRepository articleRepository;

    public void save(List<ArticleDTO> articleDTOList) {
        List<Article> articleList = buildFromArticleDTOList(articleDTOList);
        articleRepository.saveAll(articleList);
    }

    public Page<Article> search(ArticleSearchDTO articleSearchDTO) {


        // 构建查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 搜索内容
        String searchContent = articleSearchDTO.getSearchContent();
        if (!StringUtils.isEmpty(searchContent)) {
            MatchPhraseQueryBuilder builder = QueryBuilders.matchPhraseQuery("content", searchContent);
            boolQueryBuilder.should(builder);
            boolQueryBuilder.minimumShouldMatch(1);
        }

        // 内容类型筛选
        Integer type = articleSearchDTO.getType();
        if (type != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("type", type).lenient(true));
        }

        // 内容类别筛选
        String category = articleSearchDTO.getCategory();
        if (!StringUtils.isEmpty(category)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("category", category).lenient(true));
        }

        // 构建分页、排序条件
        Integer pageNumber = articleSearchDTO.getPageNumber();
        Integer pageSize = articleSearchDTO.getPageSize();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if (!StringUtils.isEmpty(articleSearchDTO.getOrderName())) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC,
                    articleSearchDTO.getOrderName());
        }
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(boolQueryBuilder)
                .build();

        // 搜索
        log.info("\n ArticleServiceImpl.search() DSL  = \n " + searchQuery.getQuery().toString());
        return articleRepository.search(searchQuery);

    }

    public Article getById(long id) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        Article article = Article.builder()
                .author("Rayson")
                .read(0)
                .title("世界在未来")
                .type(2)
                .category("散文")
                .build();
        return articleOptional.orElse(article);
    }

    public void update(Article article) {
        articleRepository.save(article);
    }

    public void delete(long id) {
        articleRepository.deleteById(id);
    }

    public long countByAuthor(String author) {
        return articleRepository.countArticlesByAuthor(author);
    }

    private List<Article> buildFromArticleDTOList(final List<ArticleDTO> articleDTOList) {
        List<Article> articleList = new ArrayList<>();
        articleDTOList.forEach(articleDTO -> {
            Article article = buildFromArticleDTO(articleDTO);
            articleList.add(article);
        });
        return articleList;
    }

    private Article buildFromArticleDTO(final ArticleDTO articleDTO) {
        if (articleDTO == null) {
            return null;
        }

        return Article.builder()
                .id(articleDTO.getId())
                .title(articleDTO.getTitle())
                .content(articleDTO.getContent())
                .type(articleDTO.getType())
                .category(articleDTO.getCategory())
                .read(articleDTO.getRead())
                .support(articleDTO.getSupport())
                .author(articleDTO.getAuthor())
                .build();
    }
}
