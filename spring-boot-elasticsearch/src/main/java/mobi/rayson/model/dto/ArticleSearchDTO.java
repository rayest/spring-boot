package mobi.rayson.model.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ArticleSearchDTO implements Serializable {

    private Integer pageNumber;
    private Integer pageSize;
    private String searchContent;
    private Integer type;
    private String category;

    // 排序字段：id、read、support
    private String orderName;
}
