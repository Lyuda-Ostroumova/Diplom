package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import io.qameta.allure.kotlin.Allure;

import ru.iteco.fmhandroid.ui.screenElements.EditNewsScreen;

public class EditNewsSteps {
    EditNewsScreen editNewsScreen = new EditNewsScreen();

    public void isEditNewsScreen() {
        Allure.step("Проверка элементов экрана News");
        editNewsScreen.editingNewsScreenName.check(matches(isDisplayed()));
        editNewsScreen.editCategoryField.check(matches(isDisplayed()));
        editNewsScreen.editTitleField.check(matches(isDisplayed()));
        editNewsScreen.publicationDateField.check(matches(isDisplayed()));
        editNewsScreen.timeField.check(matches(isDisplayed()));
        editNewsScreen.timeField.check(matches(isDisplayed()));
        editNewsScreen.descriptionField.check(matches(isDisplayed()));
        editNewsScreen.statusSwitcher.check(matches(isDisplayed()));
    }

    public void editStatus() {
        Allure.step("Изменить статус новости (актианый/неактивный)");
        editNewsScreen.statusSwitcher.perform(click());
    }

    public void editTitle(String text) {
        Allure.step("Изменить заголовок новости");
        editNewsScreen.editTitleField.perform(replaceText(text));
    }

    public void editDescription(String text) {
        Allure.step("Изменить описание новости");
        editNewsScreen.descriptionField.perform(replaceText(text));
    }

}
