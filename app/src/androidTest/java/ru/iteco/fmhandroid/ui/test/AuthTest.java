package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.CommonSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;

@RunWith(AllureAndroidJUnit4.class)
public class AuthTest {

    AuthSteps authSteps = new AuthSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    CommonSteps commonSteps = new CommonSteps();

    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            authSteps.isAuthScreen();
        } catch (NoMatchingViewException e) {
            mainScreenSteps.clickLogOutBtn();
        }
    }

    @After
    public void setUp() {
        SystemClock.sleep(3000);
    }

    @Test
    @DisplayName("Проверка элементов экрана авторизации")
    public void shouldCheckAuthScreenElements() {
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Вход с валидными данными")
    public void shouldLogInWithValidData() {
        authSteps.authWithValidData(authInfo());
        authSteps.clickSignInBtn();
        SystemClock.sleep(3000);
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с невалидными данными")
    public void shouldNotLogInWithInvalidData() {
        authSteps.authWithInvalidData(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.wrong_login_or_password, true);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с пустыми данными")
    public void shouldNotLogInWithEmptyData() {
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.empty_login_or_password, true);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с пустым логином")
    public void shouldNotLogInWithEmptyLogin() {
        authSteps.authWithEmptyLogin(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.empty_login_or_password, true);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с пустым паролем")
    public void shouldNotLogInWithEmptyPassword() {
        authSteps.authWithEmptyPass(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.empty_login_or_password, true);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с невалидным паролем")
    public void shouldNotLogInWithInvalidPass() {
        authSteps.authWithInvalidPass(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.wrong_login_or_password, true);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с невалидным логином")
    public void shouldNotLogInWithInvalidLogin() {
        authSteps.authWithInvalidLogin(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.wrong_login_or_password, true);
    }

    @Test
    @DisplayName("Авторизация и выход ")
    public void shouldLogInAndLogOut() {
        authSteps.authWithValidData(authInfo());
        authSteps.clickSignInBtn();
        SystemClock.sleep(3000);
        mainScreenSteps.isMainScreen();
        mainScreenSteps.clickLogOutBtn();
        authSteps.isAuthScreen();
    }

}