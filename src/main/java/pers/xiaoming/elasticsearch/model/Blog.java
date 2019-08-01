package pers.xiaoming.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Document(indexName = "blog", type = "blog")
public class Blog {

    // id annotation is for elastic search finding the primary key
    @Id
    @JsonProperty
    private String title;

    // Ignore id here, it has impact on elastic search
    // Elastic search may choose id to be the default search id which causes issue
    // @JsonProperty
    // private String id;

    @JsonProperty
    private String author;

    @JsonProperty
    private String content;

    @JsonProperty("created_at")
    private Timestamp createdAt;

    public Blog(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return Objects.equals(title, blog.title) &&
                Objects.equals(author, blog.author) &&
                Objects.equals(content, blog.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, author, content);
    }

    @Override
    public String toString() {
        return "\nBlog{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                "}";
    }
}
