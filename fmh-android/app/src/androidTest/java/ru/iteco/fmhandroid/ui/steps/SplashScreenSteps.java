package ru.iteco.fmhandroid.ui.steps;


import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.screenElements.SplashScreenElements;

public class SplashScreenSteps {
    SplashScreenElements splashScreenElements = new SplashScreenElements();

    public void isSplashScreen() {
        Allure.step("Проверка элементов экрана загрузки");
        splashScreenElements.splashScreenImage.check(matches(isDisplayed()));
        splashScreenElements.splashScreenLoadingIndicator.check(matches(isDisplayed()));
        splashScreenElements.splashScreenQuote.check(matches(isDisplayed()));
    }
}
