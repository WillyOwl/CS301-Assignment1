package com.example.emotilog;

import java.util.ArrayList;
import java.util.List;

/**
 LogManager - Singleton data manager for emotion log entries
 
 Purpose:
    LogManager is a singleton that handles all emotion log entries in the app. 
    It acts as the one central place where emotional data is stored and accessed, 
    making sure everything stays consistent throughout the app’s lifetime.
 
 Design Rationale:
   The design uses the Singleton pattern to ensure a single shared instance and avoid data duplication. 
   An immutable LogEntry inner class preserves data integrity by preventing modification after logging. 
   Logs are exposed only through defensive copies to protect against external changes, and simple, easily managed data structures are used throughout.
 
 Outstanding Issues:
    1. CRITICAL: No data persistence - all data is lost when app terminates
    2. No data size limits - could consume unlimited memory over time
    3. No data validation on emoticon input
    4. No bulk operations
    5. No sorting or filtering capabilities
    6. No backup/export functionality

**Singleton Pattern for Shared State**  
LogManager follows a Singleton pattern to ensure that all activities interact
with the same data instance and to prevent data duplication.

- https://developer.android.com/training/dependency-injection/manual#singleton  
- https://refactoring.guru/design-patterns/singleton  

**Data Integrity and Controlled Access**  
Log entries are designed to be immutable once created, and defensive copies are
returned when exposing internal collections to prevent unintended modification.

- https://docs.oracle.com/javase/tutorial/essential/concurrency/immutable.html  
*/

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
