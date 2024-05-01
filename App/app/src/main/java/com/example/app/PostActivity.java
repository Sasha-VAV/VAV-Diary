package com.example.app;

import static com.example.app.Pages.DiaryFragment.currentPost;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.Pages.DiaryFragment;
import com.example.app.Pages.MainActivity;


public class PostActivity extends AppCompatActivity{
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
        //ImageButton imageButton = findViewById(R.id.imgButton);


        TextView textLocation = findViewById(R.id.textLocation);
        textLocation.setText(currentPost.getL());

        ImageButton returnButton = findViewById(R.id.returnButton);
        textLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = currentPost.getL();
                String latitude = s.substring(s.indexOf(" ") + 1,s.indexOf("Lo"));
                String longitude = s.substring(s.indexOf(" ", 15));
                String label = "Вы были здесь " + currentPost.getDate() +" в " + currentPost.getTime() + "\n" + currentPost.getHead();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + label + ")"));
                try{
                    startActivity(intent);
                }
                catch (Exception ignored){
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT ).show();
                    // Handle the case where Google Maps is not installed
                }
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
