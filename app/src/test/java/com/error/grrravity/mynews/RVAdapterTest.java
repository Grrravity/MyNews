package com.error.grrravity.mynews;

import com.bumptech.glide.RequestManager;
import com.error.grrravity.mynews.controllers.fragments.PageFragment;
import com.error.grrravity.mynews.models.APIResult;
import com.error.grrravity.mynews.views.RecyclerViewAdapter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RVAdapterTest {

    private List<APIResult> mList = new ArrayList<>();
    private RequestManager mGlide;
    private PageFragment mArticleFragment;
    private RecyclerViewAdapter mRecyclerViewAdapter =
            new RecyclerViewAdapter(mList, mGlide, mArticleFragment);

    @Test
    public void countReturnTest(){
        assertEquals(0, mRecyclerViewAdapter.getItemCount());
    }
}
