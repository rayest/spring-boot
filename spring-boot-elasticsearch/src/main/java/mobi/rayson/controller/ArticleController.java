package mobi.rayson.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mobi.rayson.model.domain.Article;
import mobi.rayson.model.dto.ArticleDTO;
import mobi.rayson.model.dto.ArticleSearchDTO;
import mobi.rayson.model.vo.PageBean;
import mobi.rayson.model.vo.ResponseBean;
import mobi.rayson.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class ArticleController {

  private final ArticleService articleService;

  @ApiOperation(value = "批量添加文章")
  @PostMapping("/article")
  public ResponseBean save(@RequestBody List<ArticleDTO> articleDTOList) {
    boolean result = articleService.save(articleDTOList);
    return result ? ResponseBean.success(result) : ResponseBean.fail(result);
  }

  @ApiOperation(value = "搜索文章")
  @PostMapping(value = "/articles")
  public ResponseBean search(@RequestBody ArticleSearchDTO articleSearchDTO) {
    PageBean pageBean = articleService.search(articleSearchDTO);
    return ResponseBean.successPage(pageBean);
  }

  @ApiOperation(value = "通过id查找某篇文章")
  @GetMapping("/article/id/{id}")
  public ResponseEntity getById(@PathVariable("id") long id) {
    Article article = articleService.getById(id);
    return ResponseEntity.ok(article);
  }

  @ApiOperation(value = "更新")
  @PutMapping("/article")
  public ResponseEntity update(@RequestBody Article article) {
    articleService.update(article);
    return ResponseEntity.ok().build();
  }

  @ApiOperation(value = "删除")
  @DeleteMapping("/article/id/{id}")
  public ResponseEntity update(@PathVariable("id") long id) {
    articleService.delete(id);
    return ResponseEntity.ok().build();
  }

  @ApiOperation(value = "根据作者统计")
  @GetMapping("/article/author/{author}/count")
  public ResponseEntity countByAuthor(@PathVariable("author") String author) {
    long count = articleService.countByAuthor(author);
    return ResponseEntity.ok(count);
  }
}
