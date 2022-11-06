package ru.iteco.fmhandroid.ui.steps;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.screenElements.AboutUsScreen;

public class AboutUsSteps {

    AboutUsScreen aboutUsScreen = new AboutUsScreen();

    public void isAboutUsScreen() {
        Allure.step("Проверка элементов экрана About us");
        elementWaiting(withId(R.id.about_company_info_label_text_view), 10000);
        aboutUsScreen.tradeMarkImage.check(matches(isDisplayed()));
        aboutUsScreen.version.check(matches(isDisplayed()));
        aboutUsScreen.versionValue.check(matches(isDisplayed()));
        aboutUsScreen.privacyPolicy.check(matches(isDisplayed()));
        aboutUsScreen.privacyPolicyLink.check(matches(isDisplayed()));
        aboutUsScreen.termsOfUse.check(matches(isDisplayed()));
        aboutUsScreen.termsOfUseLink.check(matches(isDisplayed()));
        aboutUsScreen.companyInfo.check(matches(isDisplayed()));
        aboutUsScreen.returnBtn.check(matches(isDisplayed()));
    }

    public void privacyPolicyLinkClickable() {
        Allure.step("Проверка кликабильности ссылки Политика конфиденциальности");
        elementWaiting(withId(R.id.about_company_info_label_text_view), 10000);
        aboutUsScreen.privacyPolicyLink.check(matches(isClickable()));
    }

    public void clickPrivacyPolicyLink() {
        aboutUsScreen.privacyPolicyLink.perform(click());
    }

    public void termsLinkClickable() {
        Allure.step("Проверка кликабельности ссылки Условия использования");
        aboutUsScreen.termsOfUseLink.check(matches(isClickable()));
    }

    public void clickTermsLink() {
        aboutUsScreen.termsOfUseLink.perform(click());
    }

    public void clickReturnBtn() {
        Allure.step("Проверка кнопки возврат");
        aboutUsScreen.returnBtn.perform(click());
    }

}
