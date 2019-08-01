package pers.xiaoming.elasticsearch.service;

import lombok.Getter;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import pers.xiaoming.elasticsearch.dao.IBlogDao;
import pers.xiaoming.elasticsearch.model.Blog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DBInitForES extends AbstractTestNGSpringContextTests {

    private static final int NUM_OF_DATA_GENERATE = 30;

    @Getter
    private static final int[] AUTHOR_NUMS = {15, 10, 5};

    @Getter
    private static final String[] AUTHORS = {"John", "Ryan", "Mike"};

    @Getter
    private static final String[] TITLE_PREFIXES = {"MyTitle", "TestTitle", "NotRelated"};

    @Getter
    private Map<String, List<Blog>> authorToBlogMap;

    @Getter
    private Map<String, Blog> titleToBlogMap;

    @Getter
    private Map<String, Integer> titlePrefixToNumMap;

    public static int getNumOfDataGenerate() {
        return NUM_OF_DATA_GENERATE + AUTHORS.length;
    }

    private IBlogDao dao;

    public DBInitForES(IBlogDao dao) {
        this.dao = dao;
        authorToBlogMap = new HashMap<>();
        titleToBlogMap = new HashMap<>();
        titlePrefixToNumMap = new HashMap<>();
        dao.truncateTableForTest();
    }

    public void initDB() {
        Random random = new Random();

        for (int authorIndex = 0; authorIndex < AUTHORS.length; authorIndex++) {
            String author = AUTHORS[authorIndex];
            String title = TITLE_PREFIXES[authorIndex];
            String content = author + title + authorIndex;
            saveBlog(title, title, author, new Blog(title, author, content));

        }

        for (int numOfBlog = 0; numOfBlog < NUM_OF_DATA_GENERATE;) {
            for (int authorIndex = 0; authorIndex < AUTHORS.length; authorIndex++) {
                for (int authorRepeatIndex = 0; authorRepeatIndex < AUTHOR_NUMS[authorIndex]; authorRepeatIndex++, numOfBlog++) {
                    String author = AUTHORS[authorIndex];
                    int titlePrefixIndex = random.nextInt(3);
                    String titlePrefix = TITLE_PREFIXES[titlePrefixIndex];
                    String title = titlePrefix + numOfBlog;
                    String content = author + " : " + authorRepeatIndex + ":" + title + " ; " + authorIndex + " ::: " + titlePrefixIndex + "." + random.nextDouble();

                    saveBlog(title, titlePrefix, author, new Blog(title, author, content));
                }
            }
        }
    }

    private void saveBlog(String title, String titlePrefix, String author, Blog blog) {
        List<Blog> blogs = authorToBlogMap.get(author);
        if (blogs == null || blogs.size() == 0) {
            blogs = new ArrayList<>();
        }
        blogs.add(blog);
        authorToBlogMap.put(author, blogs);
        titleToBlogMap.put(title, blog);

        Integer count = titlePrefixToNumMap.get(titlePrefix);
        titlePrefixToNumMap.put(titlePrefix, count == null ? 1 : ++count);

        dao.create(blog);
    }
}
