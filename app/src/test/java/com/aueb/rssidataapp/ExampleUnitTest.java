package com.aueb.rssidataapp;

import com.aueb.rssidataapp.Connection.ApiUtil;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws IOException {
        String bill = null;
        String bill2 = "";
        if (bill == null) {
            System.out.println("bill is empty");
        }
        if (bill2 == null) {
            System.out.println("same");
        }
        ApiUtil apiUtil = new ApiUtil();
        apiUtil.getRequest("test");
        assertEquals(4, 2 + 2);
    }
}