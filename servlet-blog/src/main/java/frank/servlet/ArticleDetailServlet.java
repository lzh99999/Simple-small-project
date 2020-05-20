package frank.servlet;

import frank.dao.ArticleDAO;
import frank.exception.BusinessException;
import frank.exception.ClientException;
import frank.model.Article;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@WebServlet("/articleDetail")
public class ArticleDetailServlet extends AbstractBaseServlet {
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取请求数据中的id（文章id）
        String id = req.getParameter("id");
        Integer articleId;
        try {
            articleId = Integer.parseInt(id);
        }catch (Exception e){
            throw new ClientException("001", "请求参数错误[id="+id+"]");
        }
        Article article = ArticleDAO.query(articleId);
        if(article == null)
            throw new BusinessException("002", "查询不到文章详情id="+articleId);
        return article;//测试代码，以后替换为数据库根据文章id查询文章信息操作
    }
}
