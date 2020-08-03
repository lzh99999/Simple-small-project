package lzh.controller;

import lzh.model.Category;
import lzh.model.User;
import lzh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/c/add")
    public String add(HttpSession session, Category category) {
        User user = (User) session.getAttribute("user");
        category.setUserId(user.getId());
        int num = categoryService.insert(category);
        return "redirect:/writer";
    }
}
