package com.example.emotilog;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        displayHistory();
    }

    private void displayHistory() {
        TextView tvHistory = findViewById(R.id.tvHistoryContent);
        List<LogManager.LogEntry> logs = LogManager.getInstance().getLogs();

        if (logs.isEmpty()) {
            tvHistory.setText(R.string.no_logs);
            return;
        }

        StringBuilder history = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        
        for (int i = logs.size() - 1; i >= 0; i--) {
            LogManager.LogEntry entry = logs.get(i);
            history.append(sdf.format(new Date(entry.timestamp)))
                    .append(" - ")
                    .append(entry.emoticon)
                    .append("\n");
        }

        tvHistory.setText(history.toString());
    }
}
