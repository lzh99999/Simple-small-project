package lzh.controller;

import lzh.model.Article;
import lzh.model.Category;
import lzh.model.Comment;
import lzh.model.User;
import lzh.service.ArticleService;
import lzh.service.CategoryService;
import lzh.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Article> articles = articleService.queryArticles();
        model.addAttribute("articleList",articles);
        return "index";
    }

    @RequestMapping("/a/{id}")
    public String detail(@PathVariable("id") Long id,Model model) {
        Article article = articleService.queryArticle(id);
        List<Comment> comments = commentService.queryComments(id);
        article.setCommentList(comments);
        model.addAttribute("article",article);
        return "info";
    }

    @RequestMapping("/writer")
    public String writer(HttpSession session,Long activeCid,Model model) {
        User user = (User) session.getAttribute("user");
        List<Article> articles = articleService.queryArticleByUserId(user.getId());
        List<Category> categories = categoryService.queryCategoryByUserId(user.getId());
        model.addAttribute("articleList",articles);
        model.addAttribute("categoryList",categories);
        model.addAttribute("activeCid", activeCid == null ? categories.get(0).getId():activeCid);
        return "writer";
    }
    @RequestMapping("/writer/forward/{type}/{id}/editor")
    public String editor(@PathVariable("type") Integer type,@PathVariable("id") Long id,Model model) {
        Category category;
        if (type == 1) {//新增
            category = categoryService.queryCategoryById(id);
            model.addAttribute("activeCid",id);
        }else  {//修改
            Article article = articleService.queryArticle(id);
            category = categoryService.queryCategoryById(new Long(article.getCategoryId()));
            model.addAttribute("article",article);
        }
        model.addAttribute("type",type);
        model.addAttribute("category",category);
        return "editor";
    }
    @RequestMapping(value = "/writer/article/{type}/{id}", method = RequestMethod.POST)
    public String publish(@PathVariable("type") Integer type,
                          @PathVariable("id") Integer id, Article article,
                          HttpSession session){

        article.setUpdatedAt(new Date());
        if(type == 1){//新增的时候，插入文章数据
            article.setCategoryId(id);
            User user = (User) session.getAttribute("user");
            article.setUserId(user.getId());
            article.setCoverImage("https://picsum.photos/id/1/400/300");
            article.setCreatedAt(new Date());
            article.setStatus((byte)0);
            article.setViewCount(0L);
            article.setCommentCount(0);
            int num = articleService.insert(article);
            id = article.getId().intValue();
        }else{//修改的时候，修改文章数据
            article.setId(new Long(id));
            int num = articleService.updateByCondition(article);
        }
        return String.format("redirect:/writer/forward/2/%s/editor", id);
    }
    @RequestMapping("/writer/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        int num = articleService.delete(id);
        return  "/writer";
    }
}
