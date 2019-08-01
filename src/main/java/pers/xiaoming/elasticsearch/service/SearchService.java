package pers.xiaoming.elasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pers.xiaoming.elasticsearch.model.Blog;

import java.util.List;

public interface SearchService {
    void refreshRepository();

    Blog searchByTitle(String title);

    List<Blog> searchByAuthor(String author);

    List<Blog> searchByAuthorNot(String author);

    Page<Blog> searchByAuthor(String author, PageRequest page);

    List<Blog> searchByContent(String author);

    List<Blog> searchByTitleContains(String titleQuery);

    List<Blog> searchByTitleContainsAndAuthor(String titleQuery, String author);
}
