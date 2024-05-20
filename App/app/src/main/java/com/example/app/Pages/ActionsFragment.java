package com.example.app.Pages;

import static android.content.Context.MODE_PRIVATE;
import static com.example.app.Pages.MainActivity.NAME_SP;
import static com.example.app.Pages.MainActivity.application;
import static com.example.app.Pages.MainActivity.dictionary1;
import static com.example.app.Pages.MainActivity.user;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app.Notifications.MyWorker;
import com.example.app.R;
import com.example.app.days.DayManager;
import com.example.app.posts.PostManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DayManager dayManager;
    private PostManager postManager;
    String previousSelection1 = "English";
    String previousSelection2 = "never";

    int delay = -3;

    HashMap<String, String> dictionary2 = new HashMap<>();
    Spinner notificationSelector;

    public ActionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment actionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActionsFragment newInstance(String param1, String param2) {
        ActionsFragment fragment = new ActionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actions, container, false);
        EditText nDays = view.findViewById(R.id.nDays);
        //Button sendOnServerButton = view.findViewById(R.id.sendButton);
        Button copyData = view.findViewById(R.id.copyButton);
        Spinner languageSelector = view.findViewById(R.id.language_spinner);

        postManager = new PostManager(getActivity().getApplication(), user);
        dayManager = new DayManager(postManager.getUserPosts(-1));

        copyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n;
                if (nDays.getText().toString().equals(""))
                    n = 7;
                else
                    n = Integer.parseInt(nDays.getText().toString());
                ClipboardManager clipboardManager = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText( application.getString(R.string.last)
                        + " " + n + " " + application.getString(R.string.days), dayManager.getLastNDays(n));
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.copied), Toast.LENGTH_SHORT).show();

                //MAYBE TODO SHARE WITH
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(NAME_SP, MODE_PRIVATE);
        previousSelection1 = sharedPreferences.getString("lang","-1");
        languageSelector.setSelection(new ArrayList<>(dictionary1.values()).indexOf(dictionary1.get(previousSelection1)));
        languageSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentSelection = parent.getItemAtPosition(position).toString();
                if (!currentSelection.equals(previousSelection1)) {
                    previousSelection1 = currentSelection;
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(NAME_SP, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lang", currentSelection);
                    editor.apply();
                    Locale locale = new Locale(dictionary1.get(currentSelection));
                    Locale.setDefault(locale);
                    Configuration configuration = new Configuration();
                    configuration.locale = locale;
                    getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("was", 1);
                    startActivity(intent);
                    getActivity().finish();
                }
                else {
                    //delay = -2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        if (!MainActivity.isSendNotifications){
            LinearLayout linearLayout = view.findViewById(R.id.LL);
            linearLayout.setVisibility(View.INVISIBLE);
        }
        else if (delay != -2){
            Log.d("debug", "1");
            notificationSelector = view.findViewById(R.id.remind_me_in_spinner);
            String[] times = getResources().getStringArray(R.array.reminders);
            dictionary2.put(times[0], "1");
            dictionary2.put(times[1], "60");
            dictionary2.put(times[2], "1440");
            dictionary2.put(times[3], "10800");
            dictionary2.put(times[4], "-1");
            delay = 3;

            previousSelection2 = sharedPreferences.getString("ntfT", "-1");
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(MyWorker.CHANNEL_ID
                    , MyWorker.CHANNEL_NAME, importance);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            if (!Objects.equals(previousSelection2, "-1"))
                notificationSelector.setSelection(new ArrayList<>(Arrays.asList(times)).indexOf(previousSelection2));
        }


        ImageButton returnButton = view.findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delay != -3 && delay != -2){
                    delay = Integer.parseInt(dictionary2.get(notificationSelector.getSelectedItem().toString()));
                    if (delay != -1){
                        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(MyWorker.class, delay, TimeUnit.MINUTES)
                                .setInitialDelay(delay, TimeUnit.MINUTES)
                                .build();
                        WorkManager workManager = WorkManager.getInstance(getActivity().getApplicationContext());
                        workManager.enqueueUniquePeriodicWork("tag", ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, workRequest);
                        String s = getString(R.string.you_will_receive_notification_every) +" a";
                        if (notificationSelector.getSelectedItem().toString().indexOf("h") == 0)
                            s += "n";
                        s += " " + notificationSelector.getSelectedItem().toString();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ntfT", notificationSelector.getSelectedItem().toString());
                        editor.apply();
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ntfT", notificationSelector.getSelectedItem().toString());
                        editor.apply();
                        WorkManager.getInstance(getContext()).cancelAllWork();
                        Toast.makeText(getContext(), R.string.we_wont_send_notifications_again, Toast.LENGTH_SHORT).show();
                    }
                }
                Navigation.findNavController(view).navigateUp();
            }
        });
        /*sendOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO BACKEND
            }
        });*/

        return view;
    }
}