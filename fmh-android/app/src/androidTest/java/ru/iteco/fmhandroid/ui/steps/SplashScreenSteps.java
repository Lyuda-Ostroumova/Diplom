package ru.iteco.fmhandroid.ui.steps;


import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.screenElements.SplashScreenElements;

public class SplashScreenSteps {
    SplashScreenElements splashScreenElements = new SplashScreenElements();

    public void isSplashScreen() {
        Allure.step("Проверка элементов экрана загрузки");
        splashScreenElements.splashScreenImage.check(matches(isDisplayed()));
        splashScreenElements.splashScreenLoadingIndicator.check(matches(isDisplayed()));
        splashScreenElements.splashScreenQuote.check(matches(isDisplayed()));
    }

    public void appDownloading() {
        Allure.step("Загрузка приложения");
        elementWaiting(withId(R.id.splashscreen_image_view), 3000);
    }
}
