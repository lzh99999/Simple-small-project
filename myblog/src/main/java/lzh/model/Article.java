package lzh.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Article {
    private Long id;

    private Long userId;

    private String coverImage;

    private Integer categoryId;

    private Byte status;

    private String title;

    private String content;

    private Long viewCount;

    private Date createdAt;

    private Date updatedAt;

    //前端需要用到的属性
    private User author;

    private Integer commentCount;

    private List<Comment> commentList;
}