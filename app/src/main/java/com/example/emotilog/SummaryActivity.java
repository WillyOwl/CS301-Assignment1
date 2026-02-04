package com.example.emotilog;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

        Map<String, Integer> counts = new HashMap<>();
        for (LogManager.LogEntry entry : logs) {
            counts.put(entry.emoticon, counts.getOrDefault(entry.emoticon, 0) + 1);
        }

        StringBuilder summary = new StringBuilder();
        summary.append(getString(R.string.total_logs, logs.size())).append("\n\n");
        summary.append(getString(R.string.frequencies_header)).append("\n");
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            summary.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        summary.append("\n").append(getString(R.string.recent_history_header)).append("\n");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        for (int i = logs.size() - 1; i >= 0; i--) {
            LogManager.LogEntry entry = logs.get(i);
            summary.append(sdf.format(new Date(entry.timestamp)))
                    .append(" - ")
                    .append(entry.emoticon)
                    .append("\n");
        }

        tvSummary.setText(summary.toString());
    }
}
