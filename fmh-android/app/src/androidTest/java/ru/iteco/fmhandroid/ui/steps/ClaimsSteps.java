package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.isDisplayedWithSwipe;
import static ru.iteco.fmhandroid.ui.data.Helper.nestedScrollTo;
import static ru.iteco.fmhandroid.ui.data.Helper.waitFor;

import android.os.SystemClock;

import io.qameta.allure.kotlin.Allure;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.screenElements.ClaimsScreen;
import ru.iteco.fmhandroid.ui.screenElements.FilterClaimsWindow;

public class ClaimsSteps {
    ClaimsScreen claimsScreen = new ClaimsScreen();
    FilterClaimsWindow claimsFilter = new FilterClaimsWindow();

    public void isClaimsScreen() {
        Allure.step("Проверка элементов экрана Claims");
        claimsScreen.claimsScreenName.check(matches(isDisplayed()));
        claimsScreen.claimsList.check(matches(isDisplayed()));
    }

    public void openFilterWindow() {
        Allure.step("Открыть окно фильтра");
        claimsScreen.claimsFilterBtn.perform(click());
    }

    public void isFilterWindow() {
        Allure.step("Проверка элеменмтов окна Filter");
        claimsFilter.filterWindowName.check(matches(isDisplayed()));
        claimsFilter.openCheckBox.check(matches(isDisplayed()));
        claimsFilter.inProgressCheckBox.check(matches(isDisplayed()));
        claimsFilter.executedCheckBox.check(matches(isDisplayed()));
        claimsFilter.cancelledCheckBox.check(matches(isDisplayed()));
    }

    public void openClaimIndex(int index) {
        Allure.step("Развернуть претензию");
        claimsScreen.claimList(index).perform(click());
    }

    public void clickInProgress() {
        Allure.step("Кликнуть чек-бокс In progress");
        claimsFilter.inProgressCheckBox.perform(click());
    }

    public void clickOpen() {
        Allure.step("Кликнуть чек-бокс Open");
        claimsFilter.openCheckBox.perform(click());
    }

    public void clickExecuted() {
        Allure.step("Кликнуть чек-бокс Executed");
        claimsFilter.executedCheckBox.perform(click());
    }

    public void clickCancelled() {
        Allure.step("Кликнуть чек-бокс Cancelled");
        claimsFilter.cancelledCheckBox.perform(click());
    }

    public void clickEditClaim() {
        Allure.step("Кликнуть кнопку редактирования Претензии");
        claimsScreen.editClaimBtn.perform(nestedScrollTo());
        claimsScreen.editClaimBtn.perform(click());
    }

    public void clickAddComment() {
        Allure.step("Кликнуть кнопку добавления комментраия");
        claimsScreen.addCommentBtn.perform(nestedScrollTo());
        claimsScreen.addCommentBtn.perform(click());
    }

    public void clickEditStatusBtn() {
        Allure.step("Кликнуть кнопку изменения статуса");
        claimsScreen.changeStatusBtn.perform(nestedScrollTo());
        claimsScreen.changeStatusBtn.check(matches(isDisplayed()));
        claimsScreen.changeStatusBtn.perform(click());
    }

    public void clickTakeToWork() {
        Allure.step("Кликнуть Take to work");
        claimsScreen.takeToWorkClaim.check(matches(isDisplayed()));
        claimsScreen.takeToWorkClaim.perform(click());
    }

    public void clickCancelClaim() {
        Allure.step("Кликнуть Cancel");
        claimsScreen.cancelClaim.check(matches(isDisplayed()));
        claimsScreen.cancelClaim.perform(click());
    }

    public void clickThrowOff() {
        Allure.step("Кликнуть Throw off");
        claimsScreen.throwOffClaim.check(matches(isDisplayed()));
        claimsScreen.throwOffClaim.perform(click());
    }

    public void clickToExecute() {
        Allure.step("Кликнуть to execute");
        claimsScreen.toExecuteClaim.check(matches(isDisplayed()));
        claimsScreen.toExecuteClaim.perform(click());
    }

    public void returnToPreviousScreen() {
        Allure.step("Вернуться на предыдущий экран");
        claimsScreen.returnBtn.perform(nestedScrollTo());
        claimsScreen.returnBtn.perform(click());
    }

    public void clickCommentEditBtn(int index) {
        Allure.step("Кликнуть кнопку редактирвоания комментярия");
        claimsScreen.editComment(index).check(matches(isDisplayed()));
        claimsScreen.editComment(index).perform(click());
    }

    public void checkCommentToClaim(String text) {
        Allure.step("Проврека отображение комментария");
        claimsScreen.comment(text).check(matches(isDisplayed()));
    }

    public String getClaimComment(int position) {
        Allure.step("Получить текст комментария");
        return Helper.Text.getText(claimsScreen.commentDescription(position));
    }

    public void statusIconIsDisplayed() {
        Allure.step("Проверка видимости статуса Претензии");
        claimsScreen.statusIcon.check(matches(isDisplayed()));
    }

    public void checkClaimElements() {
        Allure.step("Проверка элементов претензии");
        claimsScreen.titleLabel.check(matches(isDisplayed()));
        claimsScreen.title.check(matches(isDisplayed()));
        claimsScreen.executorLabel.check(matches(isDisplayed()));
        claimsScreen.executorName.check(matches(isDisplayed()));
        claimsScreen.dateLabel.check(matches(isDisplayed()));
        claimsScreen.claimDate.check(matches(isDisplayed()));
        claimsScreen.claimTime.check(matches(isDisplayed()));
        claimsScreen.description.check(matches(isDisplayed()));
        claimsScreen.descriptionText.check(matches(isDisplayed()));
        claimsScreen.authorLabel.check(matches(isDisplayed()));
        claimsScreen.authorName.check(matches(isDisplayed()));
        claimsScreen.createdLabel.check(matches(isDisplayed()));
        claimsScreen.dateCreated.check(matches(isDisplayed()));
        claimsScreen.timeCreated.check(matches(isDisplayed()));
    }

    public void checkClaimStatus(String status) {
        Allure.step("Проверка статус претензии");
        onView(isRoot()).perform(waitFor(1000));
        String claimStatus = Helper.Text.getText(onView(withId(R.id.status_label_text_view)));
        assertEquals(status, claimStatus);
    }

    public void clickNewClaimBtn() {
        Allure.step("Кликнуть кнопку добавить претензию");
        claimsScreen.createClaimBtn.perform(click());
    }

    public String getClaimTitle() {
        Allure.step("Получить текст заголовка претензии");
        return Helper.Text.getText(onView(withId(R.id.title_text_view)));
    }

    public String getClaimDescription() {
        Allure.step("Получить текст описания претензии");
        return Helper.Text.getText(onView(withId(R.id.description_text_view)));
    }

    public String getClaimTime() {
        Allure.step("Получить время претензии");
        return Helper.Text.getText(onView(withId(R.id.plan_time_text_view)));
    }

    public String getClaimDate() {
        Allure.step("Получить дату создания претензии");
        return Helper.Text.getText(onView(withId(R.id.plane_date_text_view)));
    }

    public void addCommentWhenStatusChange(String comment) {
        Allure.step("Добавить комментарий при изменении статуса претензии");
        claimsScreen.statusChangingComment.perform(replaceText(comment));
    }

    public void checkCreatedClaimElement(String title, String description, String date, String time) {
        assertEquals(title, getClaimTitle());
        assertEquals(description, getClaimDescription());
        assertEquals(date, getClaimDate());
        assertEquals(time, getClaimTime());
    }

    public void checkClaim(String text) {
        Allure.step("Открытие нужной претензии");
        claimsScreen.claimsList.check(matches(isDisplayed()));
        if (isDisplayedWithSwipe(onView(withText(text)), 0, true)) {
            onView(withText(text)).perform(click());
        }
    }


}
