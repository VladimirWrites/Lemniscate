package com.vlad1m1r.lemniscate.base.models;

import com.vlad1m1r.lemniscate.TestConstants;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LineLengthTest {

    private LineLength lineLength;

    @Before
    public void setUp() {
        lineLength = new LineLength();
    }

    @Test
    public void getLineMaxLength() {
        lineLength.setLineMaxLength(0.9f);
        assertEquals(0.9f, lineLength.getLineMaxLength(), TestConstants.DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void getLineMaxLengthException() {
        lineLength.setLineMaxLength(1.1f);
    }

    @Test
    public void setLineMinLength(){
        lineLength.setLineMinLength(0.1f);
        assertEquals(0.1f, lineLength.getLineMinLength(), TestConstants.DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setLineMinLengthException(){
        lineLength.setLineMaxLength(-1.1f);
    }

}