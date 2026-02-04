package com.example.emotilog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] buttonIds = {
                R.id.btnHappy, R.id.btnSad, R.id.btnGrateful,
                R.id.btnAngry, R.id.btnExcited, R.id.btnTired,
                R.id.btnCalm, R.id.btnLoved, R.id.btnSurprised
        };

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            String emoticon = b.getText().toString();
            LogManager.getInstance().addLog(emoticon);
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.btnViewHistory).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnViewSummary).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            startActivity(intent);
        });
    }
}
