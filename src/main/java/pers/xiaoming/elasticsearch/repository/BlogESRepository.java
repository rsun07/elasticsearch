package pers.xiaoming.elasticsearch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import pers.xiaoming.elasticsearch.model.Blog;

import java.util.List;

public interface BlogESRepository extends ElasticsearchRepository<Blog, String> {

    Page<Blog> findByAuthor(String author, Pageable pageable);

    Page<Blog> findByAuthorNot(String author, Pageable pageable);

    // Contains and Containing are the same
    List<Blog> findByContentContaining(String searchStr);

    // TODO: need to fix order issue in elastic search
    // Page<Blog> findByContentContainingOrderByCreatedAtAsc(String searchStr);

    // If use List<Blog> here as return value, may only limit to get 10 result
    // Not sure why, but after change to Page<Blog>, it works fine
    Page<Blog> findByTitleContaining(String titleQuery, Pageable pageable);

    // Contains and Containing are the same
    Page<Blog> findByTitleContainsAndAuthor(String titleQuery, String author, Pageable pageable);
}
