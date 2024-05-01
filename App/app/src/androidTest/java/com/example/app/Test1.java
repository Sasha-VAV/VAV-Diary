package com.example.app;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.app.Pages.MainActivity;

import org.junit.Rule;

public class Test1 {
    @Rule
    public ActivityScenarioRule<MainActivity> activityActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);
}
