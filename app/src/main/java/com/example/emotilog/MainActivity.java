package com.example.emotilog;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private Button selectedButton = null;
    private Button btnLogSelected;
    private Button btnEditSelected;
    private Button btnDeleteSelected; 
    private final int SELECTED_COLOR = Color.LTGRAY;
    private ColorStateList defaultTint;
    private View.OnClickListener emojiClickListener;
    private View.OnLongClickListener emojiLongClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogSelected = findViewById(R.id.btnLogSelected);
        btnEditSelected = findViewById(R.id.btnEditSelected);
        btnDeleteSelected = findViewById(R.id.btnDeleteSelected);

        // Initialize defaultTint from an existing button to ensure new buttons match
        defaultTint = findViewById(R.id.btnHappy).getBackgroundTintList();

        int[] buttonIds = {
                R.id.btnHappy, R.id.btnSad, R.id.btnGrateful,
                R.id.btnAngry, R.id.btnExcited, R.id.btnTired,
                R.id.btnCalm, R.id.btnLoved, R.id.btnSurprised
        };

        emojiClickListener = v -> {
            Button clickedButton = (Button) v;
            String emoticon = clickedButton.getText().toString();
            LogManager.getInstance().addLog(emoticon);
            Toast.makeText(this, "Logged: " + emoticon, Toast.LENGTH_SHORT).show();
        };

        emojiLongClickListener = v -> {
            Button clickedButton = (Button) v;

            if (selectedButton != null) {
                selectedButton.setBackgroundTintList(defaultTint);
            }

            if (selectedButton == clickedButton) {
                // Deselect if long-pressing the same button
                selectedButton = null;
                btnLogSelected.setEnabled(false);
                btnEditSelected.setEnabled(false);
                btnDeleteSelected.setEnabled(false);
            } else {
                selectedButton = clickedButton;
                // Capture tint just in case, though it should match defaultTint
                defaultTint = selectedButton.getBackgroundTintList();
                selectedButton.setBackgroundTintList(ColorStateList.valueOf(SELECTED_COLOR));
                btnLogSelected.setEnabled(true);
                btnEditSelected.setEnabled(true);
                btnDeleteSelected.setEnabled(true);
            }
            return true;
        };

        for (int id : buttonIds) {
            View view = findViewById(id);
            view.setOnClickListener(emojiClickListener);
            view.setOnLongClickListener(emojiLongClickListener);
        }

        btnLogSelected.setOnClickListener(v -> {
            if (selectedButton != null) {
                String emoticon = selectedButton.getText().toString();
                LogManager.getInstance().addLog(emoticon);
                Toast.makeText(this, "Logged: " + emoticon, Toast.LENGTH_SHORT).show();

                // Deselect after logging
                selectedButton.setBackgroundTintList(defaultTint);
                selectedButton = null;
                btnLogSelected.setEnabled(false);
                btnEditSelected.setEnabled(false);
                btnDeleteSelected.setEnabled(false);
            }
        });

        btnEditSelected.setOnClickListener(v -> {
            if (selectedButton != null) {
                showEditDialog();
            }
        });
        
        btnDeleteSelected.setOnClickListener(v -> {
            if (selectedButton != null) {
                deleteSelectedEmoji();
            }
        });

        findViewById(R.id.btnAddEmoji).setOnClickListener(v -> {
            showAddDialog();
        });

        findViewById(R.id.btnViewHistory).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnViewSummary).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
            startActivity(intent);
        });
    }
    
    private void deleteSelectedEmoji() {
        if (selectedButton != null) {
            GridLayout gridLayout = findViewById(R.id.gridLayout);
            String emoticonText = selectedButton.getText().toString();
            
            // Remove the button from the UI
            gridLayout.removeView(selectedButton);
            
            // Clear selection state
            selectedButton = null;
            btnLogSelected.setEnabled(false);
            btnEditSelected.setEnabled(false);
            btnDeleteSelected.setEnabled(false);
            
            // Inform user
            Toast.makeText(this, "Deleted emoji: " + emoticonText, Toast.LENGTH_SHORT).show();
        }
    }


    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.edit_emoji_title);

        final EditText input = new EditText(this);
        input.setText(selectedButton.getText().toString());
        builder.setView(input);

        builder.setPositiveButton(R.string.save, (dialog, which) -> {
            String newText = input.getText().toString();
            if (!newText.isEmpty()) {
                selectedButton.setText(newText);
            }
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_emoji_title);

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton(R.string.save, (dialog, which) -> {
            String newText = input.getText().toString();
            if (!newText.isEmpty()) {
                addNewEmojiButton(newText);
            }
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void addNewEmojiButton(String text) {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        // Using MaterialButton to match existing buttons inflated from XML
        MaterialButton newButton = new MaterialButton(this);
        newButton.setText(text);
        newButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        
        int paddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        newButton.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        
        // Explicitly set background tint to match existing ones
        if (defaultTint != null) {
            newButton.setBackgroundTintList(defaultTint);
        }

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        newButton.setLayoutParams(params);

        newButton.setOnClickListener(emojiClickListener);
        newButton.setOnLongClickListener(emojiLongClickListener);

        gridLayout.addView(newButton);
    }
}