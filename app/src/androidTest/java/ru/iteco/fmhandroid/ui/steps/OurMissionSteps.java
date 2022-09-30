package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import static ru.iteco.fmhandroid.ui.data.Helper.childAtPosition;
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import android.os.SystemClock;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matchers;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.screenElements.OurMissionScreen;

public class OurMissionSteps {
    OurMissionScreen ourMissionScreen = new OurMissionScreen();

    public void isOurMissionScreen() {
        Allure.step("Проверка элементов экрана Our mission");
        ourMissionScreen.screenName.check(matches(isDisplayed()));
        ourMissionScreen.listOfQuotes.check(matches(isDisplayed()));
    }

    public void showOrHideQuote(int number) {
        Allure.step("Развернуть/свернуть цитату");
        ourMissionScreen.missionList.check(matches(isDisplayed()));
        ourMissionScreen.missionList.perform(actionOnItemAtPosition(number, click()));
    }

    public void descriptionNotDisplayed(String text) {
        Allure.step("Проверка отображения содержания цитаты");
        onView(withText(text)).check(matches(not(isDisplayed())));
    }

    public void descriptionIsDisplayed(String text) {
        Allure.step("Проверка скрытия содержания цитаты");
        onView(withText(text)).check(matches(isDisplayed()));
    }


}


