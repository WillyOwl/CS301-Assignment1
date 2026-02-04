package com.example.emotilog;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        updateSummary();
    }

    private void updateSummary() {
        TextView tvSummary = findViewById(R.id.tvSummaryContent);
        List<LogManager.LogEntry> logs = LogManager.getInstance().getLogs();

        if (logs.isEmpty()) {
            tvSummary.setText(R.string.no_logs);
            return;
        }

        // Group logs by day
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Map<String, List<LogManager.LogEntry>> groupedLogs = new LinkedHashMap<>();
        
        // Logs are usually in chronological order, but we want most recent day first.
        // First, group them.
        for (LogManager.LogEntry entry : logs) {
            String day = dayFormat.format(new Date(entry.timestamp));
            groupedLogs.computeIfAbsent(day, k -> new ArrayList<>()).add(entry);
        }

        StringBuilder summary = new StringBuilder();
        summary.append(getString(R.string.total_logs, logs.size())).append("\n\n");

        // Convert keys to list and reverse to show most recent days first
        List<String> days = new ArrayList<>(groupedLogs.keySet());
        java.util.Collections.reverse(days);

        for (String day : days) {
            List<LogManager.LogEntry> dayLogs = groupedLogs.get(day);
            summary.append("=== ").append(day).append(" ===\n");
            
            Map<String, Integer> counts = new HashMap<>();
            for (LogManager.LogEntry entry : dayLogs) {
                counts.put(entry.emoticon, counts.getOrDefault(entry.emoticon, 0) + 1);
            }

            int dayTotal = dayLogs.size();
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                int count = entry.getValue();
                double percentage = (count * 100.0) / dayTotal;
                summary.append(entry.getKey())
                        .append(": ")
                        .append(count)
                        .append(" (")
                        .append(String.format(Locale.getDefault(), "%.1f%%", percentage))
                        .append(")\n");
            }
            summary.append("\n");
        }

        tvSummary.setText(summary.toString());
    }
}
