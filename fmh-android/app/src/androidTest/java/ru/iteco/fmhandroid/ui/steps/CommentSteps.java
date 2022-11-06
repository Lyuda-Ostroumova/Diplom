package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.screenElements.CommentScreen;
import ru.iteco.fmhandroid.ui.screenElements.CommonElements;

public class CommentSteps {
    CommentScreen commentScreen = new CommentScreen();
    CommonElements buttons = new CommonElements();

    public void isCommentScreen() {
        Allure.step("Проверка элементов экрана Comment");
        commentScreen.commentField.check(matches(isDisplayed()));
        commentScreen.commentFieldName.check(matches(isDisplayed()));
        buttons.saveBtn.check(matches(isDisplayed()));
        buttons.cancelInDialog.check(matches(isDisplayed()));
    }

    public void addComment(String comment) {
        Allure.step("Добавить комментарий");
        commentScreen.commentField.perform(click()).perform(replaceText(comment));

    }

    public void commentInputLoaded () {
        Allure.step("Загрузка страницы с полем ввода комментария");
        elementWaiting(withId(R.id.comment_text_input_layout), 10000);
    }

}
