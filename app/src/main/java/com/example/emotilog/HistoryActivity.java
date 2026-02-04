package com.example.emotilog;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 HistoryActivity - Chronological display of all emotion log entries
 
 Purpose:
 Offers users a complete chronological list of all the emotions they have logged, showing each one and its corresponding time stamp in reverse chronological order (most recent first).
    Acts as a detailed journal view for users to examine their emotional patterns over time.
 
 Design Rationale:
    The design focuses on a clean and simple interface with the readability of historical data as its primary concern, 
    listing entries in reverse chronological order so that the latest and usually most relevant information is viewed first. 
    A simple interface with only basic navigation elements like a back button keeps the user’s attention on the data.
 
 
Outstanding Issues:
    1. PERFORMANCE: All logs displayed in single TextView
    2. NO FILTERING: Users cannot filter by date range, emotion type, etc.
    3. NO EXPORT: Users cannot export their history data
    4. ACCESSIBILITY: Large text blocks may be difficult for users with disabilities
    5. NO EDITING: Users cannot modify or delete entries from history view
 */
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
