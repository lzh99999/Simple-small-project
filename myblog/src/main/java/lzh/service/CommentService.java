package lzh.service;

import lzh.mapper.CommentMapper;
import lzh.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> queryComments(Long id) {
        return commentMapper.queryComments(id);
    }

    public int insertComment(Comment comment) {
        return commentMapper.insert(comment);
    }
}
