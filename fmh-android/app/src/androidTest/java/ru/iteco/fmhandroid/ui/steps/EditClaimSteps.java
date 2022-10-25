package ru.iteco.fmhandroid.ui.steps;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static ru.iteco.fmhandroid.ui.data.Helper.waitForElement;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.screenElements.EditClaimsScreen;

public class EditClaimSteps {
    EditClaimsScreen editClaimsScreen = new EditClaimsScreen();

    public void isEditClaimScreen() {
        Allure.step("Проверка элементов экрана Edit Claim");
        onView(isRoot()).perform(waitForElement(withText("Editing"), 10000));
        editClaimsScreen.editClaimScreenName.check(matches(isDisplayed()));
    }

    public void changeClaimTitle(String text) {
        Allure.step("Изменить заголовок претензии");
        editClaimsScreen.editClaimTitleField.perform(replaceText(text));
    }

    public void changeDescription(String text) {
        Allure.step("Изменить описание претензии");
        editClaimsScreen.editClaimDescriptionField.perform(replaceText(text));
    }
}
