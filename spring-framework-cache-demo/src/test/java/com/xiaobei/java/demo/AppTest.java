package com.xiaobei.java.demo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.cache.annotation.Cacheable;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Cacheable("books")
    public String findBook() {
        return null;
    }
}
