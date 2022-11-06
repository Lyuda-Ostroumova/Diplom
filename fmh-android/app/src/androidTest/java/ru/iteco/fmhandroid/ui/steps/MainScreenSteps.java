package ru.iteco.fmhandroid.ui.steps;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;
import static ru.iteco.fmhandroid.ui.data.Helper.waitForElement;
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.screenElements.MainScreenElements;

public class MainScreenSteps {
    MainScreenElements mainScreenElements = new MainScreenElements();

    public void checkMainScreenLoaded() {
        Allure.step("Загрузка страницы");
        elementWaiting(withText("all claims"), 10000);
    }

    public void isMainScreen() {
        Allure.step("Проверка элементов экрана Main");
        mainScreenElements.tradeMark.check(matches(isDisplayed()));
        mainScreenElements.news.check(matches(isDisplayed()));
        mainScreenElements.newsUnit.check(matches(isDisplayed()));
        mainScreenElements.claims.check(matches(isDisplayed()));
        mainScreenElements.claimsUnit.check(matches(isDisplayed()));
    }

    public void expandAllNews() {
        Allure.step("Развернуть/свернуть блок новостей");
        mainScreenElements.expandNewsFeedButton.check(matches(isDisplayed()));
        mainScreenElements.expandNewsFeedButton.perform(click());
    }

    public void allNewsNotDisplayed() {
        Allure.step("Проверка, что блок новостей свернут");
        mainScreenElements.allNewsBtn.check(matches(not(isDisplayed())));
    }

    public void allNewsDisplayed() {
        Allure.step("Проверка отображения кнопки All news");
        mainScreenElements.newsUnit.check(matches(isDisplayed()));
    }

    public void expandAllClaims() {
        Allure.step("Развернуть/свренуть блок претензий");
        mainScreenElements.expandClaimsFeedButton.check(matches(isDisplayed()));
        mainScreenElements.expandClaimsFeedButton.perform(click());
    }

    public void allClaimsNotDisplayed() {
        Allure.step("Проверка, что блок претензий свернут");
        mainScreenElements.allClaimsBtn.check(matches(not(isDisplayed())));
    }

    public void allClaimsDisplayed() {
        Allure.step("Првоекра отобраения кнопкаи all claims");
        mainScreenElements.claimsUnit.check(matches(isDisplayed()));
    }

    public void expandSingleNews(int position) {
        Allure.step("Развернуть произвольно выбранную новость");
        mainScreenElements.expandSingleNews.perform(actionOnItemAtPosition(position, click()));
    }

    public void descriptionIsDisplayed(int position) {
        Allure.step("Проверка отображения описания нвоости");
        String descriptionText = Helper.Text.getText(onView(withIndex(withId(R.id.news_item_description_text_view), position)));
        ViewInteraction newsDescription = onView(allOf(withId(R.id.news_item_description_text_view), withText(descriptionText)));
        newsDescription.check(matches(isDisplayed()));
    }

    public void clickAllNews() {
        Allure.step("Кликнуть all news");
        mainScreenElements.allNewsBtn.check(matches(isDisplayed()));
        mainScreenElements.allNewsBtn.perform(click());
    }

    public void clickAllClaims() {
        Allure.step("Кликнуть all claims");
        mainScreenElements.allClaimsBtn.check(matches(isDisplayed()));
        mainScreenElements.allClaimsBtn.perform(click());
    }

    public void clickNewClaimBtn() {
        Allure.step("Кликнуть кнопку создать претензию");
        mainScreenElements.newClaimBtn.check(matches(isDisplayed()));
        mainScreenElements.newClaimBtn.perform(click());
        onView(isRoot()).perform(waitForElement(withText("Creating"), 3000));
    }

    public void clickOurMissionBtn() {
        Allure.step("Кликнуть кнопку Our Mission");
        mainScreenElements.ourMissionBtn.check(matches(isDisplayed()));
        mainScreenElements.ourMissionBtn.perform(click());
    }

    public void clickClaimOnMainScreen(int position) {
        Allure.step("Кликнуть претензию на оснвоном экране");
        mainScreenElements.claimList.perform(actionOnItemAtPosition(position, click()));
    }

    public void clickLogOutBtn() {
        Allure.step("Кликнуь кнопку выхода");
        mainScreenElements.logOutBtn.check(matches(isDisplayed()));
        mainScreenElements.logOutBtn.perform(click());
        onView(isRoot()).perform(waitForElement(withText("Log out"), 1000));
        mainScreenElements.logOut.check(matches(isDisplayed()));
        mainScreenElements.logOut.perform(click());
    }

    public void clickActionMenuBtn() {
        Allure.step("Кликнуть кнопку меню");
        mainScreenElements.menuBtn.check(matches(isDisplayed()));
        mainScreenElements.menuBtn.perform(click());
    }

    public void checkMenuList() {
        Allure.step("Проверка выпадающего списка кнопки меню");
        mainScreenElements.titleMain.check(matches(isDisplayed()));
        mainScreenElements.titleClaims.check(matches(isDisplayed()));
        mainScreenElements.titleNews.check(matches(isDisplayed()));
        mainScreenElements.titleAbout.check(matches(isDisplayed()));
    }

    public void goToClaimsScreen() {
        Allure.step("Кликнуть название экрана Claims в выпадающем списке");
        mainScreenElements.menuBtn.perform(click());
        mainScreenElements.titleClaims.check(matches(isDisplayed()));
        mainScreenElements.titleClaims.perform(click());
    }

    public void goToNewsScreen() {
        Allure.step("Кликнуть название экрана News в выпадающем списке");
        mainScreenElements.menuBtn.perform(click());
        mainScreenElements.titleNews.check(matches(isDisplayed()));
        mainScreenElements.titleNews.perform(click());
    }

    public void goToAboutScreen() {
        Allure.step("Кликнуть название экрана About в выпадающем списке");
        mainScreenElements.menuBtn.perform(click());
        mainScreenElements.titleAbout.check(matches(isDisplayed()));
        mainScreenElements.titleAbout.perform(click());
    }

    public void goToMainScreen() {
        Allure.step("Кликнуть название экрана Main в выпадающем списке");
        mainScreenElements.menuBtn.perform(click());
        mainScreenElements.titleMain.check(matches(isDisplayed()));
        mainScreenElements.titleMain.perform(click());
    }

    public void claimOnMainScreenLoaded() {
        Allure.step("Загрузка претензии");
        elementWaiting(withId(R.id.plan_date_label_material_text_view), 10000);
    }

}
