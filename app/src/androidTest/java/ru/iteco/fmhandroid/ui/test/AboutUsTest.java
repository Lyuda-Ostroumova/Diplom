package ru.iteco.fmhandroid.ui.test;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.steps.AboutUsSteps;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;

@RunWith(AllureAndroidJUnit4.class)
public class AboutUsTest {

    AuthSteps authSteps = new AuthSteps();
    AboutUsSteps aboutUsSteps = new AboutUsSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    NewsSteps newsSteps = new NewsSteps();

    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            newsSteps.isNewsScreen();
        } catch (NoMatchingViewException e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
            SystemClock.sleep(5000);
        } finally {
            mainScreenSteps.goToAboutScreen();
        }
    }

    @Test
    @DisplayName("Проверка элементов экрана")
    @Description("Корректность отображения всех элементов экрана About us")
    public void shouldCheckAboutUsScreenElements() {
        aboutUsSteps.isAboutUsScreen();
    }

    @Test
    @DisplayName("Проверка кликабельности ссылок")
    public void shouldCheckLinksAreClickable() {
        aboutUsSteps.privacyPolicyLinkClickable();
        aboutUsSteps.termsLinkClickable();
    }

    @Test
    @DisplayName("Вернуться на предыдущий экран")
    @Description("При нажатии на стрелочку на верхней панели пользователь возвращается на предыдущий экран")
    public void shouldCheckGoBackToPreviousScreen() {
        aboutUsSteps.clickReturnBtn();
        mainScreenSteps.isMainScreen();
    }

}