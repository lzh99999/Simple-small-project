package lzh.controller;

import lzh.model.Comment;
import lzh.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/a/{id}/comments")
    public String commentsInsert(@PathVariable("id") Long id, Comment comment) {
        comment.setArticleId(id);
        comment.setCreatedAt(new Date());
        int sum = commentService.insertComment(comment);
        return "redirect:/a/"+id;
    }
}
