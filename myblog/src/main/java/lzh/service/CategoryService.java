package lzh.service;

import lzh.mapper.CategoryMapper;
import lzh.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public  List<Category> queryCategoryByUserId(Long id) {
        return  categoryMapper.queryCategoryByUserId(id);
    }

    public int insert(Category category) {
        return categoryMapper.insert(category);
    }

    public Category queryCategoryById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }
}
