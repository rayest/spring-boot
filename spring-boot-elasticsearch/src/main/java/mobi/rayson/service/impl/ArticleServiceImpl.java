package mobi.rayson.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mobi.rayson.constant.SearchConstant;
import mobi.rayson.model.domain.Article;
import mobi.rayson.model.dto.ArticleDTO;
import mobi.rayson.model.dto.ArticleSearchDTO;
import mobi.rayson.model.vo.PageBean;
import mobi.rayson.mapper.ArticleRepository;
import mobi.rayson.service.ArticleService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Primary
@AllArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepository;

  @Override
  public boolean save(List<ArticleDTO> articleDTOList) {
    List<Article> articleList = buildFromArticleDTOList(articleDTOList);
    articleRepository.saveAll(articleList);
    return true;
  }

  @Override
  public PageBean search(ArticleSearchDTO articleSearchDTO) {

    Integer pageNumber = articleSearchDTO.getPageNumber();
    Integer pageSize = articleSearchDTO.getPageSize();

    PageBean<Article> resultPageBean = new PageBean<>();
    resultPageBean.setPageNumber(pageNumber);
    resultPageBean.setPageSize(pageSize);

    // 构建查询条件
    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

    // 搜索内容
    String searchContent = articleSearchDTO.getSearchContent();
    if (!StringUtils.isEmpty(searchContent)) {
      String fieldName = SearchConstant.CONTENT_ES_NAME;
      MatchPhraseQueryBuilder builder = QueryBuilders.matchPhraseQuery(fieldName, searchContent);
      boolQueryBuilder.should(builder);
      boolQueryBuilder.minimumShouldMatch(SearchConstant.MINIMUM_SHOULD_MATCH);
    }

    // 内容类型筛选
    Integer type = articleSearchDTO.getType();
    if (type != null) {
      String fieldName = SearchConstant.TYPE_ES_NAME;
      boolQueryBuilder.must(QueryBuilders.matchQuery(fieldName, type).lenient(true));
    }

    // 内容类别筛选
    String category = articleSearchDTO.getCategory();
    if (!StringUtils.isEmpty(category)) {
      String fieldName = SearchConstant.CATEGORY_ES_NAME;
      boolQueryBuilder.must(QueryBuilders.matchQuery(fieldName, category).lenient(true));
    }

    // 构建分页、排序条件
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
    Page<Article> contentPage = articleRepository.search(searchQuery);

    resultPageBean.setResult(contentPage.getContent());
    resultPageBean.setTotalCount((int) contentPage.getTotalElements());
    int totalPage = (int) contentPage.getTotalElements() / resultPageBean.getPageSize() + 1;
    resultPageBean.setTotalPage(totalPage);
    return resultPageBean;
  }

  @Override
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

  @Override
  public void update(Article article) {
    articleRepository.save(article);
  }

  @Override
  public void delete(long id) {
    articleRepository.deleteById(id);
  }

  @Override
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
    if (articleDTO == null) return null;

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
