package com.example.app.Notifications;

import static com.example.app.Pages.MainActivity.application;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.app.Pages.MainActivity;
import com.example.app.R;

import java.util.Locale;

public class MyWorker extends Worker {
    public static String text = "You asked to remind you";
    public static String header = "Save info";

    public static final String CHANNEL_ID = "NOT_ID";
    public static final String CHANNEL_NAME = "NOT_NAME";
    private static final Integer NOTIFICATION_ID = 1;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        createNotification();
        return Result.success();
    }

    private void createNotification() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("was", 1);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.warning)
                .setContentTitle(header)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setLocalOnly(true);


        NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
