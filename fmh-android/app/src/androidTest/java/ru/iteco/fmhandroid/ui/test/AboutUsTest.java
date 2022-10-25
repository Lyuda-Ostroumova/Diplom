package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;

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
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.steps.AboutUsSteps;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;

@RunWith(AllureAndroidJUnit4.class)
public class AboutUsTest {

    AuthSteps authSteps = new AuthSteps();
    AboutUsSteps aboutUsSteps = new AboutUsSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();


    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        elementWaiting(withId(R.id.splashscreen_image_view), 3000);
        try {
            elementWaiting(withText("all claims"), 8000);
        } catch (Exception e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
        } finally {
            elementWaiting(withText("all claims"),  8000);
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
        elementWaiting(withId(R.id.about_company_info_label_text_view), 10000);
        aboutUsSteps.privacyPolicyLinkClickable();
        aboutUsSteps.termsLinkClickable();
    }

    @Test
    @DisplayName("Вернуться на предыдущий экран")
    @Description("При нажатии на стрелочку на верхней панели пользователь возвращается на предыдущий экран")
    public void shouldCheckGoBackToPreviousScreen() {
        elementWaiting(withId(R.id.about_company_info_label_text_view), 10000);
        aboutUsSteps.clickReturnBtn();
        elementWaiting(withId(R.id.claim_list_recycler_view), 10000);
        mainScreenSteps.isMainScreen();
    }

}