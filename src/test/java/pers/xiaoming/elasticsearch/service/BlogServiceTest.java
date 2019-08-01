package pers.xiaoming.elasticsearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pers.xiaoming.elasticsearch.Application;
import pers.xiaoming.elasticsearch.model.Blog;

// BUG:
// test directory not shares resources folder with the main dir
// After copy paste the resources dir, test get passed
// Before it cannot find mybatis_blog.xml

@SpringBootTest(classes = Application.class)
public class BlogServiceTest extends AbstractTestNGSpringContextTests {
    @Autowired
    @Qualifier("blogService")
    private BlogService blogService;

    private static Blog blog;

    @BeforeClass
    public static void createStudent() {
        blog = new Blog("MyTitle " + System.currentTimeMillis(), "TestUser", "Random content");
    }

    @Test
    public void testCURD() {
        blogService.createBlog(blog);
        String title = blog.getTitle();

        Blog actual = blogService.getBlogByTitle(title);
        Assert.assertEquals(actual, blog);
    }
}
