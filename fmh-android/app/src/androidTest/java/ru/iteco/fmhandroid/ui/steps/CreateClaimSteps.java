package ru.iteco.fmhandroid.ui.steps;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.screenElements.CommonElements;
import ru.iteco.fmhandroid.ui.screenElements.CreatingClaimsScreen;

public class CreateClaimSteps {
    CreatingClaimsScreen creatingClaimsScreen = new CreatingClaimsScreen();
    CommonElements commonElements = new CommonElements();

    public void createClaimScreenLoaded() {
        Allure.step("Загрузка страницы создания претензии");
        elementWaiting(withId(R.id.title_edit_text), 10000);
    }

    public void timeFieldLoaded() {
        Allure.step("Загрузка поля ввода данных");
        elementWaiting(withId(R.id.time_in_plan_text_input_edit_text), 10000);
    }


    public void isCreatingClaimScreen() {
        Allure.step("Проерка элементов экрана Creating Claims");
        creatingClaimsScreen.creatingClaimsScreenName.check(matches(isDisplayed()));
        creatingClaimsScreen.titleField.check(matches(isDisplayed()));
        creatingClaimsScreen.executorField.check(matches(isDisplayed()));
        creatingClaimsScreen.claimDateField.check(matches(isDisplayed()));
        creatingClaimsScreen.claimTimeField.check(matches(isDisplayed()));
        creatingClaimsScreen.claimDescriptionField.check(matches(isDisplayed()));
        commonElements.saveBtn.check(matches(isDisplayed()));
    }

    public void fillInTitle(String title) {
        Allure.step("Заполнить поле заголовок");
        creatingClaimsScreen.titleField.perform(replaceText(title));
    }

    public void fillInExecutor(String executor) {
        Allure.step("Заполнить поле исполнитель");
        creatingClaimsScreen.executorField.perform(replaceText(executor));
    }

    public void fillInDate(String date) {
        Allure.step("Заполнить поле дата");
        creatingClaimsScreen.claimDateField.perform(replaceText(date));
    }

    public void fillInTime(String time) {
        Allure.step("Заполнить поле время");
        creatingClaimsScreen.claimTimeField.perform(replaceText(time));
    }

    public void fillItDescription(String description) {
        Allure.step("Заполнить поле описание");
        creatingClaimsScreen.claimDescriptionField.perform(replaceText(description));
    }

    public void clickTimeField() {
        Allure.step("Заполнить поле время");
        creatingClaimsScreen.claimTimeField.perform(click());
    }

}
