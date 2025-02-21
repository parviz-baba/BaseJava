package com.basejava.storage.strategy;

import com.basejava.model.Resume;

import javax.xml.bind.*;
import java.io.*;

public class XmlStreamStrategy implements SerializationStrategy {
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public XmlStreamStrategy() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Resume.class);
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        unmarshaller = context.createUnmarshaller();
    }

    @Override
    public void write(Resume r, OutputStream os) throws IOException {
        try {
            marshaller.marshal(r, os);
        } catch (JAXBException e) {
            throw new IOException("XML serialization error", e);
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try {
            return (Resume) unmarshaller.unmarshal(is);
        } catch (JAXBException e) {
            throw new IOException("XML deserialization error", e);
        }
    }
}