package com.error.grrravity.mynews;
import com.error.grrravity.mynews.models.APIArticles;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArticleTest {

    //Test if articles are set
    @Test
    public void articleGetTest() {
        APIArticles articles = new APIArticles();
        articles.setStatus("ok");
        articles.setCopyright("authorization");
        articles.setNumResults(1);

        assertEquals("ok", articles.getStatus());
        assertEquals("authorization", articles.getCopyright());
        assertEquals(Long.valueOf(1), Long.valueOf(articles.getNumResults()));
    }
}
