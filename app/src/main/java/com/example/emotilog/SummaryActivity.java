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

/**
 SummaryActivity - Statistical analysis and visualization of emotion log data
 
 Purpose:
    Offers users analytical views of their emotional patterns by aggregating and summarizing their emotion logs. 
    Provides users with statistical breakdowns of their emotion logs by day, including counts and percentages for each type of emotion.
 
 Design Rationale:
   To enable meaningful temporal analysis, data is grouped by day. 
   Raw counts and percentages are combined to clearly convey relative emotion frequencies. 
   A HashMap efficiently counts occurrences, while a LinkedHashMap preserves day order. 
   Results are displayed in reverse chronological order so recent days are immediately visible
 
 Outstanding Issues:
    1. PERFORMANCE: Inefficient string building for large datasets
    2. NO VISUALIZATIONS: No charts, graphs, or visual representations of trends
    3. NO COMPARISON: Cannot compare different time periods
    4. STATIC DATA: No real-time updates if new logs are added
    5. NO FILTERING: Cannot filter summary by date ranges or specific emotions
    6. MEMORY USAGE: Builds entire summary string in memory (problematic for large datasets)
    7. NO EXPORT: Users cannot export summary data
*/

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
            summary.append(day).append("\n");
            
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
