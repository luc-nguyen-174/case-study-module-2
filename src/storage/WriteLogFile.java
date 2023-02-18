package storage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteLogFile implements IWriteLog {
    private WriteLogFile() {
    }

    private static WriteLogFile instance;

    public static WriteLogFile getInstance() {
        if (instance == null) {
            instance = new WriteLogFile();
        }
        return instance;
    }

    @Override
    public void WriteLogFile(Object log) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("log.txt", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter buff = new BufferedWriter(writer);
        try {
            buff.write( log+"\n");
            writer.close();
            buff.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
