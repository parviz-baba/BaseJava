package com.basejava.storage;

import com.basejava.model.Resume;
import com.basejava.storage.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SerializationStrategyTest {
    private SerializationStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new JsonStreamStrategy();
    }

    @Test
    void testWriteAndRead() throws IOException {
        Resume resume = new Resume("John Doe");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        strategy.write(resume, baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Resume deserializedResume = strategy.read(bais);
        assertEquals(resume, deserializedResume,
                "Deserialized resume should be equal to the original one");
    }

    @Test
    void testDifferentStrategies() throws IOException, JAXBException {
        SerializationStrategy[] strategies = {
                new JsonStreamStrategy(),
                new CsvStreamStrategy(),
                new XmlStreamStrategy(),
                new ObjectStreamStrategy()
        };

        Resume resume = new Resume("John Doe");
        for (SerializationStrategy strategy : strategies) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            strategy.write(resume, baos);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            Resume deserializedResume = strategy.read(bais);
            assertEquals(resume, deserializedResume, "Strategy " +
                                                     strategy.getClass().getSimpleName() + " failed!");
        }
    }
}