package com.basejava.util;

import com.basejava.model.Resume;
import com.basejava.model.Section;
import com.basejava.model.TextSection;
import org.junit.jupiter.api.Test;

import static com.basejava.TestData.R1;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonParserTest {

    @Test
    void testResume() throws Exception {
        String json = JsonParser.write(R1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(R1, resume);
    }

    @Test
    void write() throws Exception {
        Section section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        assertEquals(section1, section2);
    }
}