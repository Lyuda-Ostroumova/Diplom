package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.screenElements.AdvancedNewsFilterScreen;
import ru.iteco.fmhandroid.ui.screenElements.FilterNewsScreen;


public class FilterNewsSteps {
    FilterNewsScreen filterNewsScreen = new FilterNewsScreen();
    AdvancedNewsFilterScreen advancedFilter = new AdvancedNewsFilterScreen();

    public void isFilterScreen() {
        Allure.step("Проверка элементов экрана фильтр");
        filterNewsScreen.filterScreenName.check(matches(isDisplayed()));
        filterNewsScreen.categoryField.check(matches(isDisplayed()));
        filterNewsScreen.startDateField.check(matches(isDisplayed()));
        filterNewsScreen.endDateField.check(matches(isDisplayed()));
        filterNewsScreen.filterBtn.check(matches(isClickable()));
    }

    public void clickFilter() {
        Allure.step("Кликнуть кнопку фильтр");
        filterNewsScreen.filterBtn.perform(click());
    }

    public String category(int position) {
        return Helper.Text.getText(onView(withIndex(withId(R.id.news_item_title_text_view), position)));
    }

    public void enterStartDate(String startDate) {
        Allure.step("Заполнить поле начальная дата");
        filterNewsScreen.startDateField.perform(replaceText(startDate));
    }

    public void enterEndDate(String endDate) {
        Allure.step("Заполнить поле конечная дата");
        filterNewsScreen.endDateField.perform(replaceText(endDate));
    }

    public void clickOnActiveCheckBox() {
        Allure.step("Кликнуть чек-бокс Active");
        advancedFilter.activeCheckBox.perform(click());
    }

    public void clickOnNotActiveCheckBox() {
        Allure.step("Кликнуть чек-бокс Not active");
        advancedFilter.notActiveCheckBox.perform(click());
    }

    public void checkActiveCheckBoxStatus(boolean checked) {
        Allure.step("Проверка нажатия чек-бокса Active");
        if (checked) {
            advancedFilter.activeCheckBox.check(matches(isChecked()));
        } else {
            advancedFilter.activeCheckBox.check(matches(isNotChecked()));
        }
    }

    public void checkNotActiveCheckBoxStatus(boolean checked) {
        Allure.step("Проверка нажатия чек-бокса Not active");
        if (checked) {
            advancedFilter.notActiveCheckBox.check(matches(isChecked()));
        } else {
            advancedFilter.notActiveCheckBox.check(matches(isNotChecked()));
        }
    }

}
