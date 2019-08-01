package pers.xiaoming.elasticsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.xiaoming.elasticsearch.dao.IBlogDao;
import pers.xiaoming.elasticsearch.model.Blog;

import java.util.List;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Autowired
    private IBlogDao blogDao;

    @Override
    public void createBlog(Blog blog) {
        blogDao.create(blog);
    }

    @Override
    public Blog getBlogByTitle(String title) {
        return blogDao.selectByTitle(title);
    }

    @Override
    public List<Blog> getBlogByAuthor(String author) {
        return blogDao.selectByAuthor(author);
    }


}
