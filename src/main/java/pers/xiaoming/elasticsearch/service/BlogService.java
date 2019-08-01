package pers.xiaoming.elasticsearch.service;

import pers.xiaoming.elasticsearch.model.Blog;

import java.util.List;

public interface BlogService {
    void createBlog(Blog blog);

    Blog getBlogByTitle(String title);

    List<Blog> getBlogByAuthor(String author);
}
