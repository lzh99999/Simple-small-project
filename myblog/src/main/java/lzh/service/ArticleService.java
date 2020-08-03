package lzh.service;

import lzh.mapper.ArticleMapper;
import lzh.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public List<Article> queryArticles() {
        return articleMapper.selectAll();
    }

    public Article queryArticle(Long id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    public List<Article> queryArticleByUserId(Long id) {
        return articleMapper.queryArticleByUserId(id);
    }

    public int delete(Integer id) {
        return articleMapper.deleteByPrimaryKey(new Long(id));
    }

    public int insert(Article article) {
        return articleMapper.insert(article);
    }

    public int updateByCondition(Article article) {
        return articleMapper.updateByCondition(article);
    }
}
