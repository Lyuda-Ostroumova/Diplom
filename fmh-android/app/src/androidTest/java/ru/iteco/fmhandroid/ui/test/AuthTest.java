package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;
import static ru.iteco.fmhandroid.ui.data.Helper.waitFor;
import static ru.iteco.fmhandroid.ui.data.Helper.waitForElement;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
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
        onView(isRoot()).perform(waitForElement(withId(R.id.splashscreen_image_view), 3000));
        try {
            onView(isRoot()).perform(waitForElement(withId(R.id.enter_button),3000));
            authSteps.isAuthScreen();
        } catch (Exception e) {
            mainScreenSteps.clickLogOutBtn();
            onView(isRoot()).perform(waitForElement(withId(R.id.enter_button),3000));
        }
    }

    @Test
    @DisplayName("Проверка элементов экрана авторизации")
    @Description("Корректность отображения всех элементов экрана Авторизация")
    public void shouldCheckAuthScreenElements() {
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Вход с валидными данными")
    @Description("При вводе валидного логина и пароля пользователь переходит на главный экран")
    public void shouldLogInWithValidData() {
        authSteps.authWithValidData(authInfo());
        authSteps.clickSignInBtn();
        onView(isRoot()).perform(waitForElement(withText("all claims"), 3000));
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с невалидными данными")
    @Description("При вводе невалидных значений логина и пароля всплывает соощение о неверных данных")
    public void shouldNotLogInWithInvalidData() {
        authSteps.authWithInvalidData(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.wrong_login_or_password, true);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с пустыми данными")
    @Description("При попытке авторизоваться с пустыми логином и паролем пользователь не авторизуется, вплывает сообщение о незаполненных полях")
    public void shouldNotLogInWithEmptyData() {
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.empty_login_or_password, true);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с пустым логином")
    @Description("При попытке авторизации с пустым логином пользователь не авторизуется, всплывает собщение о незаполненом поле")
    public void shouldNotLogInWithEmptyLogin() {
        authSteps.authWithEmptyLogin(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.empty_login_or_password, true);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с пустым паролем")
    @Description("При попытке авторизации с пустым паролеи пользователь не авторизуется, всплывает собщение о незаполненом поле")
    public void shouldNotLogInWithEmptyPassword() {
        authSteps.authWithEmptyPass(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.empty_login_or_password, true);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с невалидным паролем")
    @Description("При попытке авторизации с невалидным паролем пользователь не авторизуется, всплывает собщение о неверно заполненном поле")
    public void shouldNotLogInWithInvalidPass() {
        authSteps.authWithInvalidPass(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.wrong_login_or_password, true);
    }

    @Test
    @DisplayName("Сообщение об ошибке при авторизации с невалидным логином")
    @Description("При попытке авторизации с невалидным логином пользователь не авторизуется, всплывает собщение о неверно заполненном поле")
    public void shouldNotLogInWithInvalidLogin() {
        authSteps.authWithInvalidLogin(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyToast(R.string.wrong_login_or_password, true);
    }

    @Test
    @DisplayName("Авторизация и выход ")
    @Description("Пользователь авторизуется с валидными данными и выходит из приложения с помощью кнопки Log out")
    public void shouldLogInAndLogOut() {
        authSteps.authWithValidData(authInfo());
        authSteps.clickSignInBtn();
        onView(isRoot()).perform(waitForElement(withText("all claims"), 3000));
        mainScreenSteps.isMainScreen();
        mainScreenSteps.clickLogOutBtn();
        authSteps.isAuthScreen();
    }

}