package com.example.app;

import static com.example.app.Diary.DiaryActivity.currentPost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.Diary.DiaryActivity;

public class PostActivity extends AppCompatActivity {
    private int key;
    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        if (currentPost == null)
            finish();
        TextView textDate = findViewById(R.id.textDate)
                , textTime = findViewById(R.id.textTime)
                , textHead = findViewById(R.id.header_title)
                , text = findViewById(R.id.text);
        textDate.setText(currentPost.getDate());
        textTime.setText(currentPost.getTime());
        textHead.setText(currentPost.getHead());
        text.setText(currentPost.getText());
        ImageButton imageButton = findViewById(R.id.imgButton);
        imageButton.setEnabled(false);

        ImageButton returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, DiaryActivity.class);
                startActivity(intent);
            }
        });
    }
}
