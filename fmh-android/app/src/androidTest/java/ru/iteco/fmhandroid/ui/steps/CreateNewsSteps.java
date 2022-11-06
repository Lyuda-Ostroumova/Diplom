package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.screenElements.CreatingNewsScreen;


public class CreateNewsSteps {
    CreatingNewsScreen creatingNewsScreen = new CreatingNewsScreen();
    CommonSteps commonSteps = new CommonSteps();

    public void isCreatingNewsScreen() {
        Allure.step("Проверка элементов экрана Creating News");
        creatingNewsScreen.creatingNewsScreenName.check(matches(isDisplayed()));
        creatingNewsScreen.categoryName.check(matches(isDisplayed()));
        creatingNewsScreen.categoryField.check(matches(isDisplayed()));
        creatingNewsScreen.titleField.check(matches(isDisplayed()));
        creatingNewsScreen.titleName.check(matches(isDisplayed()));
        creatingNewsScreen.publicationDateField.check(matches(isDisplayed()));
        creatingNewsScreen.publicationDateName.check(matches(isDisplayed()));
        creatingNewsScreen.timeField.check(matches(isDisplayed()));
        creatingNewsScreen.titleName.check(matches(isDisplayed()));
        creatingNewsScreen.descriptionField.check(matches(isDisplayed()));
        creatingNewsScreen.descriptionName.check(matches(isDisplayed()));
    }

    public void fillInNewsCategory(String text) {
        Allure.step("Заполнить поле категория");
        creatingNewsScreen.categoryField.perform(replaceText(text));
    }

    public void fillInNewsTitle(String text) {
        Allure.step("Заполнить поле заголовок");
        creatingNewsScreen.titleField.perform(replaceText(text));
    }

    public void fillInPublicationDate(String text) {
        Allure.step("Заполнить поле дата публикации");
        creatingNewsScreen.publicationDateField.perform(replaceText(text));
    }

    public void fillInTime(String text) {
        Allure.step("Заполнить поле время");
        creatingNewsScreen.timeField.perform(replaceText(text));
    }

    public void fillInNewsDescription(String text) {
        Allure.step("Заполнить поле описание");
        creatingNewsScreen.descriptionField.perform(replaceText(text));
    }

    public void createNews(String category, String title, String publicationDate, String publicationTime, String description) {
        Allure.step("Создать новость");
        fillInNewsCategory(category);
        fillInNewsTitle(title);
        fillInPublicationDate(publicationDate);
        fillInTime(publicationTime);
        fillInNewsDescription(description);
    }

    public void clickTimeField() {
        Allure.step("Кликнуть поле время");
        creatingNewsScreen.timeField.perform(click());
    }

}
