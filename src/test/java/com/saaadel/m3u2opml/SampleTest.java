package com.saaadel.m3u2opml;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@RunWith(JUnit4.class)
public class SampleTest {

    @Test
    public void sampleOPMLGenerationTest() throws IOException {
        String opmlPath = "build/generated-test-resources/garant-tv.radio.opml.xml";
        String opmlStandardPath = "src/test/resources/garant-tv.radio.opml.xml";
        String m3u8Path = "src/test/resources/garant-tv.radio.m3u8";
        Converter.main(new String[]{m3u8Path, opmlPath});

        Assert.assertTrue(FileUtils.contentEquals(new File(opmlStandardPath), new File(opmlPath)));
    }
}
