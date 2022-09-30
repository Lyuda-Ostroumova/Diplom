package ru.iteco.fmhandroid.ui.test;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.SplashScreenSteps;

@RunWith(AllureAndroidJUnit4.class)
public class SplashScreenTest {

    SplashScreenSteps splashScreenSteps = new SplashScreenSteps();

    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Test
    @DisplayName("Проверить наличие элементо на экране")
    public void shouldCheckSplashScreen() {
        splashScreenSteps.isSplashScreen();
    }
}


