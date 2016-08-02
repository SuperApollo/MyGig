package com.example.administrator.myapplication.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/7/27.
 */
public class TestTimeTest {

    private TestTime mTestTime;
    @Before
    public void setUp() throws Exception {
        mTestTime = new TestTime();
    }

    @Test
    public void testPrintTime() throws Exception {
        mTestTime.printTime();
    }
}