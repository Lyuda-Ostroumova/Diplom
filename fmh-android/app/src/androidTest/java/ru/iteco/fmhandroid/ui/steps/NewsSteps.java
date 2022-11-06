package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import android.os.SystemClock;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.data.Resources;
import ru.iteco.fmhandroid.ui.screenElements.NewsScreen;


public class NewsSteps {
    NewsScreen newsScreen = new NewsScreen();
    Resources resources = new Resources();

    public void newsListLoaded() {
        Allure.step("Дождаться загрузки списка новостей");
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
    }

    public void isNewsScreen() {
        Allure.step("Проверка элементов экрана News");
        newsScreen.newsScreenName.check(matches(isDisplayed()));
        newsScreen.allNewsList.check(matches(isDisplayed()));
    }

    public void openFilter() {
        Allure.step("Открыть фильтр");
        newsScreen.filterNewsBtn.check(matches(isDisplayed()));
        newsScreen.filterNewsBtn.perform(click());
    }

    public void clickSortBtn() {
        Allure.step("Кликнуть кнопку сортировки");
        newsScreen.sortNewsBtn.check(matches(isDisplayed()));
        newsScreen.sortNewsBtn.perform(click());
    }

    public void clickEditBtn() {
        Allure.step("Клинкнуть кнопку редактирования");
        newsScreen.editButton.check(matches(isDisplayed()));
        newsScreen.editButton.perform(click());
    }

    public void openNews(int position) {
        Allure.step("Развернуть произвольно выбранную новость");
        newsScreen.expandNewsButton.perform(actionOnItemAtPosition(position, click()));
    }

    public void newsContentIsDisplayed(int position) {
        Allure.step("Проверка отображения описания произвольной новости");
        String newsContent = Helper.Text.getText(onView(withIndex(withId(R.id.news_item_description_text_view), position)));
        ViewInteraction newsDescription = onView(allOf(withId(R.id.news_item_description_text_view), withText(newsContent)));
        newsDescription.check(matches(isDisplayed()));
    }

    public String getFirstNewsTitle(int position) {
        Allure.step("Получить заголовок первой новости");
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_title_text_view), position)));
    }

    public String getFirstNewsTitleAfterSecondSorting(int position) {
        Allure.step("Получить заголовок первой новости после второй сортировки");
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_title_text_view), position)));
    }

    public void checkFirstNewsPublicationDate() {
        Allure.step("Проверка даты публикации первой новости");
        String firstNewsPublicationDate = Helper.Text.getText(onView(withIndex(withId(R.id.news_item_date_text_view), 0)));
        assertEquals(firstNewsPublicationDate, resources.newsPublicationDate);
    }

    public String getCreatedNewsDescription(int position) {
        Allure.step("Получить описание созданной новости");
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_description_text_view), position)));

    }

}
