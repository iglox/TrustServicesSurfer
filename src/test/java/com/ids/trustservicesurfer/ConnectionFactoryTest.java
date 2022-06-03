package com.ids.trustservicesurfer;

import com.ids.trustservicesurfer.ConnectionFactory;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionFactoryTest {

    @Test
    public void buildSearchCriteriaString() {
        Assert.assertEquals("{\"countries\":[],\"qServiceTypes\":[]}", ConnectionFactory.buildSearchCriteriaString(new String[]{}, new String[]{}));
        assertEquals("{\"countries\":[\"TT\",\"rr\"],\"qServiceTypes\":[\"TEst\",\"Test\"]}", ConnectionFactory.buildSearchCriteriaString(new String[]{"TT","rr"}, new String[]{"TEst","Test"}));
    }
}