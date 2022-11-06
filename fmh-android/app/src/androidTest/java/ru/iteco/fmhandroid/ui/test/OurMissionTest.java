package ru.iteco.fmhandroid.ui.test;

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
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;
import ru.iteco.fmhandroid.ui.steps.OurMissionSteps;
import ru.iteco.fmhandroid.ui.steps.SplashScreenSteps;

@RunWith(AllureAndroidJUnit4.class)
public class OurMissionTest {

    AuthSteps authSteps = new AuthSteps();
    OurMissionSteps ourMissionSteps = new OurMissionSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    SplashScreenSteps splashScreenSteps = new SplashScreenSteps();

    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        splashScreenSteps.appDownloading();
        try {
            mainScreenSteps.checkMainScreenLoaded();
        } catch (Exception e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
        } finally {
            mainScreenSteps.checkMainScreenLoaded();
            mainScreenSteps.clickOurMissionBtn();
        }
    }

    @Test
    @DisplayName("Проверка элементов экрана с тематическими цитатами")
    @Description("Корректность отображения всех элементов экрана с тематическими цитатами")
    public void shouldCheckMissionScreenElements() {
        ourMissionSteps.isOurMissionScreen();
    }

    @Test
    @DisplayName("Свернуть/развернуть цитату")
    @Description("При нажати на цитату разворачивается ее содержимое")
    public void shouldShowOrHideQuoteDescription() {

        //#2
        String thirdQuoteText = "Все сотрудники хосписа - это адвокаты пациента, его прав и потребностей. Поиск путей решения различных задач - это и есть хосписный индивидуальный подход к паллиативной помощи.";
        ourMissionSteps.showOrHideQuote(2);
        ourMissionSteps.descriptionIsDisplayed(thirdQuoteText);
        ourMissionSteps.showOrHideQuote(2);
    }

}