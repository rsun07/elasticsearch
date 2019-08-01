package pers.xiaoming.elasticsearch.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pers.xiaoming.elasticsearch.Application;
import pers.xiaoming.elasticsearch.dao.IBlogDao;
import pers.xiaoming.elasticsearch.model.Blog;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = Application.class)
@Slf4j
public class ElasticSearchServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SearchService service;

    @Autowired
    private IBlogDao dao;

    private static DBInitForES dbInit;

    @BeforeClass
    public void setup() {
        dbInit = new DBInitForES(dao);
        dbInit.initDB();
        service.refreshRepository();
    }

    @Test
    public void testSearchByTitle() {
        String[] titlePrefixes = DBInitForES.getTITLE_PREFIXES();
        for (String title : titlePrefixes) {
            Blog blog = service.searchByTitle(title);
            log.info("Search by Title from ES : search {}, \nresult {}", title, blog);
            Assert.assertEquals(dbInit.getTitleToBlogMap().get(title), blog);
        }
    }

    @Test
    public void testSearchByTitleContains() {
        String[] titlePrefixes = DBInitForES.getTITLE_PREFIXES();
        for (String title : titlePrefixes) {
            List<Blog> blogs = service.searchByTitleContains(title);
            log.info("Search by Title Contains from ES : search {}, \nresult {}", title, blogs);
            Assert.assertSame(dbInit.getTitlePrefixToNumMap().get(title), blogs.size());
        }
    }

    @Test
    public void testSearchByAuthor() {
        String[] authors = DBInitForES.getAUTHORS();
        for (String author : authors) {
            List<Blog> blogs = service.searchByAuthor(author);
            log.info("Search by Authors from ES : search {}, \nresult {}", author, blogs);
            Assert.assertSame(dbInit.getAuthorToBlogMap().get(author).size(), blogs.size());
        }
    }

    @Test
    public void testSearchByAuthorWithPage() {
        String[] authors = DBInitForES.getAUTHORS();
        for (String author : authors) {
            Page<Blog> blogs = service.searchByAuthor(author, PageRequest.of(0, 100));
            log.info("Search by Author with page from ES : search {}, \nresult {}", author, blogs);
            Assert.assertSame((long) dbInit.getAuthorToBlogMap().get(author).size(), blogs.getTotalElements());
        }
    }

    @Test
    public void testSearchByAuthorWithPageLimit1() {
        String author = DBInitForES.getAUTHORS()[0];
        // This will limit to only return one
        Page<Blog> blogs = service.searchByAuthor(author, PageRequest.of(0, 1));
        log.info("Search by Author with page limit 1 from ES : search {}, \nresult {}", author, blogs);

        // Total elements still get the right count from page 0 to unlimited size
        Assert.assertSame((long) dbInit.getAuthorToBlogMap().get(author).size(), blogs.getTotalElements());

        List<Blog> blogList = blogs.getContent();
        Assert.assertSame(1, blogList.size());
    }

    @Test
    public void testSearchByAuthorWithPageStartFromPageTwo() {
        String author = DBInitForES.getAUTHORS()[0];
        // This will limit to only return one
        Page<Blog> blogs = service.searchByAuthor(author, PageRequest.of(2, 100));
        log.info("Search by Author with page starting from page 2 from ES : search {}, \nresult {}.", author, blogs);

        // Total elements still get the right count from page 0
        Assert.assertSame((long) dbInit.getAuthorToBlogMap().get(author).size(), blogs.getTotalElements());

        List<Blog> blogList = blogs.getContent();
        Assert.assertSame(0, blogList.size());
    }

    @Test
    public void testSearchByAuthorNot() {
        service.refreshRepository();
        String[] authors = DBInitForES.getAUTHORS();
        for (String author : authors) {
            List<Blog> blogs = service.searchByAuthorNot(author);
            log.info("Search by NOT Authors from ES : search {}, \nresult {}", author, blogs);
            Assert.assertSame(DBInitForES.getNumOfDataGenerate() - dbInit.getAuthorToBlogMap().get(author).size(), blogs.size());
        }
    }

    @Test
    public void testSearchByContent() {
        String[] authors = DBInitForES.getAUTHORS();
        for (String contentQuery : authors) {
            List<Blog> blogs = service.searchByContent(contentQuery);
            log.info("Search by Content from ES : search {}, \nresult {}", contentQuery, blogs);
        }
    }

    @Test
    public void testSearchByTitleContainsAndAuthor() {
        String titlePrefix = DBInitForES.getTITLE_PREFIXES()[0];
        String author = DBInitForES.getAUTHORS()[0];
        List<Blog> blogs = service.searchByTitleContainsAndAuthor(titlePrefix, author);
        log.info("Search by Title Contains and Author from ES : search title: {}, author: {}\nresult {}", titlePrefix, blogs);
        Assert.assertSame(getTitleAndAuthor(titlePrefix, author).size(), blogs.size());
    }

    private List<Blog> getTitleAndAuthor(String titlePrefix, String author) {
        List<Blog> blogs = dbInit.getAuthorToBlogMap().get(author);
        List<Blog> result = new ArrayList<>();
        for (Blog blog : blogs) {
            if (blog.getTitle().startsWith(titlePrefix)) {
                result.add(blog);
            }
        }
        return result;
    }
}
