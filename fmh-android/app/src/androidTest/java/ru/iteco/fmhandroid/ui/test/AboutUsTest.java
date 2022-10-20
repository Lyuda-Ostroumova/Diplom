package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.Helper.waitFor;
import static ru.iteco.fmhandroid.ui.data.Helper.waitForElement;


import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.screenElements.SplashScreenElements;
import ru.iteco.fmhandroid.ui.steps.AboutUsSteps;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;

@RunWith(AllureAndroidJUnit4.class)
public class AboutUsTest {

    AuthSteps authSteps = new AuthSteps();
    AboutUsSteps aboutUsSteps = new AboutUsSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();


    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        onView(isRoot()).perform(waitForElement(withId(R.id.splashscreen_image_view), 3000));
        try {
            onView(isRoot()).perform(waitForElement(withId(R.id.claim_list_recycler_view), 2000));
        } catch (Exception e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
        } finally {
            onView(isRoot()).perform(waitForElement(withId(R.id.claim_list_recycler_view), 2000));
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
        onView(isRoot()).perform(waitForElement(withId(R.id.about_company_info_label_text_view), 2000));
        aboutUsSteps.privacyPolicyLinkClickable();
        aboutUsSteps.termsLinkClickable();
    }

    @Test
    @DisplayName("Вернуться на предыдущий экран")
    @Description("При нажатии на стрелочку на верхней панели пользователь возвращается на предыдущий экран")
    public void shouldCheckGoBackToPreviousScreen() {
        onView(isRoot()).perform(waitForElement(withId(R.id.about_company_info_label_text_view), 2000));
        aboutUsSteps.clickReturnBtn();
        onView(isRoot()).perform(waitForElement(withId(R.id.claim_list_recycler_view), 2000));
        mainScreenSteps.isMainScreen();
    }

}