package ru.iteco.fmhandroid.ui.steps;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.isDisplayedWithSwipe;
import static ru.iteco.fmhandroid.ui.data.Helper.waitForElement;
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.screenElements.CommonElements;
import ru.iteco.fmhandroid.ui.screenElements.ControlPanelScreen;

public class ControlPanelSteps {
    ControlPanelScreen controlPanelScreen = new ControlPanelScreen();
    CommonSteps commonSteps = new CommonSteps();
    CommonElements commonElements = new CommonElements();

    public void isControlPanelScreen() {
        Allure.step("Проверка элементов Control panel");
        controlPanelScreen.controlPanelScreenName.check(matches(isDisplayed()));
        controlPanelScreen.sortBtn.check(matches(isDisplayed()));
        controlPanelScreen.newsFilterBtn.check(matches(isDisplayed()));
        controlPanelScreen.createNewsBtn.check(matches(isDisplayed()));
    }

    public void clickSortNewsBtn() {
        Allure.step("Кликнуть кнопку сортирвоки");
        controlPanelScreen.sortBtn.perform(click());
    }

    public void openNewsFilterScreen() {
        Allure.step("Открыть окно расширенного фильтра");
        controlPanelScreen.newsFilterBtn.perform(click());
    }

    public void clickCreateNewsBtn() {
        Allure.step("Кликнуть кнопку создания новости");
        controlPanelScreen.createNewsBtn.perform(click());
    }

    public void deleteNews(String newsTitle) {
        Allure.step("Удалить новость");
        controlPanelScreen.deleteNewsBtn(newsTitle).perform(click());

    }

    public void confirmDeleting() {
        Allure.step("Подвтердить удаление");
        commonElements.deleteDialog.check(matches(isDisplayed()));
        commonSteps.clickOkBtn();
    }

    public void cancelDeleting() {
        Allure.step("Отменить удаление");
        commonElements.deleteDialog.check(matches(isDisplayed()));
        commonSteps.clickCancelInDialog();
    }

    public void clickEditNews(int position) {
        Allure.step("Клинкть кнопку редактирования новости");
        onView(withIndex(withId(R.id.edit_news_item_image_view), position)).perform(click());
        onView(isRoot()).perform(waitForElement(withText("Editing"), 10000));
    }

    public void clickOnRandomlySelectedNewsItem(int position) {
        Allure.step("Кликнуть произвольную новость");
        controlPanelScreen.blockOfNews.perform(actionOnItemAtPosition(position, click()));
    }

    public String getFirstNewsPublicationDate(int position) {
        Allure.step("Получить дату публикации первой новости");
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_publication_date_text_view), position)));
    }

    public String getFirstNewsPublicationDateAfterSecondSorting(int position) {
        Allure.step("Получить дату публикации первой новости после двух сортировок");
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_publication_date_text_view), position)));
    }

    public void checkControlPanelSorting() {
        Allure.step("Проверка сортировки новостей в Control panel");
        String firstNewsPublication = getFirstNewsPublicationDate(0);
        clickSortNewsBtn();
        controlPanelScreen.blockOfNews.perform(swipeDown());
        clickSortNewsBtn();
        controlPanelScreen.blockOfNews.perform(swipeDown());
        String firstNewsPublicationAfterSecondSorting = getFirstNewsPublicationDateAfterSecondSorting(0);
        assertEquals(firstNewsPublication, firstNewsPublicationAfterSecondSorting);
    }

    public String getEditedNewsTitle(int position) {
        Allure.step("Получить отредактированной заголовок новости");
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_title_text_view), position)));
    }

    public String getEditedNewsDescription(int position) {
        Allure.step("Получить отредактированное содержание новости");
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_description_text_view), position)));
    }

    public void checkNotActiveNewsStatus() {
        Allure.step("Проверка статуса Not active");
        controlPanelScreen.newsStatusNotActive.check(matches(withText("Not active")));
    }

    public void checkActiveNewsStatus() {
        Allure.step("Проверка статуса Active");
        controlPanelScreen.newsStatusActive.check(matches(withText("Active")));
    }

    public void checkCreatedNews(int position, String titleText, String descriptionText) {
        Allure.step("Проверка заголовка и содержания созданной новости");
        controlPanelScreen.blockOfNews.perform(actionOnItemAtPosition(position, click()));
        onView(isRoot()).perform(waitForElement(withId(R.id.news_item_description_text_view),10000));
        controlPanelScreen.newsItemTitle.check(matches(withText(titleText)));
        controlPanelScreen.newsDescription.check(matches(withText(descriptionText)));
    }

    public void checkNewsExists(String title) {
        Allure.step("Проверка наличия новости с указанным названием");
        controlPanelScreen.blockOfNews.check(matches(isDisplayed()));
        if (isDisplayedWithSwipe(onView(withText(title)), 1, true)) {
            onView(withText(title)).check(matches(isDisplayed()));
        }
    }
}

