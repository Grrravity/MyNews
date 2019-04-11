package com.error.grrravity.mynews;

import com.error.grrravity.mynews.models.APIDoc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DocTest {

    //Test if docs are set correctly
    @Test
    public void docGetTest(){
        APIDoc doc = new APIDoc();

        doc.setWebUrl("https");
        doc.setAPIHeadline(null);
        doc.setSectionName("business");
        doc.setKeywords(null);
        doc.setPubDate("2019/10/04");

        assertEquals("https", doc.getWebUrl());
        assertNull("", doc.getHeadline());
        assertEquals("business", doc.getSectionName());
        assertNull("",doc.getKeywords());
        assertEquals("2019/10/04", doc.getPubDate());
    }
}
