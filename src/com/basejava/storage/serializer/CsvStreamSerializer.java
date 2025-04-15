package com.basejava.storage.serializer;

import com.basejava.model.Resume;
import org.apache.commons.csv.*;

import java.io.*;
import java.util.List;

public class CsvStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(r.getUuid(), r.getFullName());
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            List<CSVRecord> records = csvParser.getRecords();
            CSVRecord record = records.get(0);
            return new Resume(record.get(0), record.get(1));
        }
    }
}