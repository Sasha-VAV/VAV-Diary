package com.example.app.Actions;

import static com.example.app.MainActivity.user;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.days.DayManager;
import com.example.app.posts.PostManager;

public class ActionsActivity extends AppCompatActivity {
    private DayManager dayManager;
    private PostManager postManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actions_activity);

        EditText nDays = findViewById(R.id.nDays);
        Button sendOnServerButton = findViewById(R.id.sendButton);
        Button copyData = findViewById(R.id.copyButton);

        postManager = new PostManager(getApplication(), user);
        dayManager = new DayManager(postManager.getUserPosts(-1));

        copyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n;
                if (nDays.getText().toString().equals(""))
                    n = 7;
                else
                    n = Integer.parseInt(nDays.getText().toString());
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Last " + n + " days", dayManager.getLastNDays(n));
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "Скопировано!", Toast.LENGTH_SHORT).show();

                //MAYBE TODO SHARE WITH
            }
        });
        ImageButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO BACKEND
            }
        });
    }
}
