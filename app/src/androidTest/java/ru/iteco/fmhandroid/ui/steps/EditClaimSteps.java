package ru.iteco.fmhandroid.ui.steps;


import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.os.SystemClock;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.ui.screenElements.CommonElements;
import ru.iteco.fmhandroid.ui.screenElements.EditClaimsScreen;

public class EditClaimSteps {
    EditClaimsScreen editClaimsScreen = new EditClaimsScreen();

    public void isEditClaimScreen() {
        Allure.step("Проверка элементов экрана Edit Claim");
        SystemClock.sleep(3000);
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
