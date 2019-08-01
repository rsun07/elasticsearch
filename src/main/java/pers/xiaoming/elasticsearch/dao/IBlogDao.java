package pers.xiaoming.elasticsearch.dao;

import org.springframework.stereotype.Component;
import pers.xiaoming.elasticsearch.model.Blog;

import java.util.List;

@Component
public interface IBlogDao {
    void create(Blog blog);

    Blog selectByTitle(String title);

    List<Blog> selectByAuthor(String author);

    List<Blog> selectAll();

    // dangerous function, should be avoid in prod env
    void truncateTableForTest();
}
