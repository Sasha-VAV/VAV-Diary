package com.example.app;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
//import androidx.test.espresso.intent.Intents;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

public class RegisterTest1 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    private String login = "test1";
    private String password = "test2";
    private String name = "testName";
    @Test
    public void registerTest1() {
        onView(withId(R.id.toProfile))
                .perform(click());
        onView(withText("Выйти из аккаунта"))
                .perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.RegisterButton),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.loginEd),
                        isDisplayed()));
        appCompatEditText.perform(replaceText(login), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.passwordEd),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText(password), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.nametagEd),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText(name), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.RegisterButton),
                        isDisplayed()));
        appCompatButton2.perform(click());

        onView(withId(R.id.name))
                .check(matches(withText(name)));
        onView(withId(R.id.SettingsButton))
                .perform(click());
        onView(withText("Войти"))
                .check(matches(withText("Войти")));


    }
    @Test
    public void registerTest2() {

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.RegisterButton),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.loginEd),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("test1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.passwordEd),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("test2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.nametagEd),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("testName"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.RegisterButton),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.loginEd), withText("test1"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        editText.check(matches(withText("test1")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.passwordEd), withText("test2"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        editText2.check(matches(withText("test2")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.nametagEd), withText("testName"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        editText3.check(matches(withText("testName")));


    }

}
