package mobi.rayson.es.article;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import mobi.rayson.es.article.dto.ArticleDTO;
import mobi.rayson.es.article.dto.ArticleSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Log4j2
public class ArticleController {

  @Resource
  private  ArticleService articleService;

  @ApiOperation(value = "批量添加文章")
  @PostMapping("/article")
  public void save(@RequestBody List<ArticleDTO> articleDTOList) {
    articleService.save(articleDTOList);
  }

  @ApiOperation(value = "搜索文章")
  @PostMapping(value = "/articles")
  public Page<Article> search(@RequestBody ArticleSearchDTO articleSearchDTO) {
    return articleService.search(articleSearchDTO);
  }

  @ApiOperation(value = "通过id查找某篇文章")
  @GetMapping("/article/id/{id}")
  public Article getById(@PathVariable("id") long id) {
    return articleService.getById(id);
  }

  @ApiOperation(value = "更新")
  @PutMapping("/article")
  public void update(@RequestBody Article article) {
    articleService.update(article);
  }

  @ApiOperation(value = "删除")
  @DeleteMapping("/article/id/{id}")
  public void delete(@PathVariable("id") long id) {
    articleService.delete(id);
  }

  @ApiOperation(value = "根据作者统计")
  @GetMapping("/article/author/{author}/count")
  public long countByAuthor(@PathVariable("author") String author) {
    return articleService.countByAuthor(author);
  }
}
