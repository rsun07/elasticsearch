package pers.xiaoming.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pers.xiaoming.elasticsearch.exception.ExceptionResolver;
import pers.xiaoming.elasticsearch.exception.ResourceNotFoundException;
import pers.xiaoming.elasticsearch.model.Blog;
import pers.xiaoming.elasticsearch.service.BlogService;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogContoller extends ExceptionResolver {

    private BlogService service;

    @Autowired
    public BlogContoller(BlogService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Blog post(@RequestBody Blog blog) {
        service.createBlog(blog);
        return blog;
    }

    @RequestMapping(value = "/title/{title}", method = RequestMethod.GET)
    public Blog getByTitle(@PathVariable("title") String title) {
        return nullCheck(service.getBlogByTitle(title));
    }

    @RequestMapping(value = "/author/{author}", method = RequestMethod.GET)
    public List<Blog> getByAuthor(@PathVariable("author") String author) {
        return service.getBlogByAuthor(author);
    }

    private Blog nullCheck(Blog blog) {
        if (blog == null) {
            throw new ResourceNotFoundException();
        }
        return blog;
    }
}
