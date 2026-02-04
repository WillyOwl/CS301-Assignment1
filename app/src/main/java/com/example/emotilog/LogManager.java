package com.example.emotilog;

import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private static LogManager instance;
    private final List<LogEntry> logs = new ArrayList<>();

    public static class LogEntry {
        public final String emoticon;
        public final long timestamp;

        public LogEntry(String emoticon, long timestamp) {
            this.emoticon = emoticon;
            this.timestamp = timestamp;
        }
    }

    private LogManager() {}

    public static synchronized LogManager getInstance() {
        if (instance == null) {
            instance = new LogManager();
        }
        return instance;
    }

    public void addLog(String emoticon) {
        logs.add(new LogEntry(emoticon, System.currentTimeMillis()));
    }

    public List<LogEntry> getLogs() {
        return new ArrayList<>(logs);
    }
}
