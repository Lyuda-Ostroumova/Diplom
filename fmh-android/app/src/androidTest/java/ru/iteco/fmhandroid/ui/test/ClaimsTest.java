package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomExecutor;

import android.content.pm.ActivityInfo;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.data.Resources;
import ru.iteco.fmhandroid.ui.screenElements.ClaimsScreen;
import ru.iteco.fmhandroid.ui.screenElements.CreatingClaimsScreen;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.CommentSteps;
import ru.iteco.fmhandroid.ui.steps.CommonSteps;
import ru.iteco.fmhandroid.ui.steps.CreateClaimSteps;
import ru.iteco.fmhandroid.ui.steps.EditClaimSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;
import ru.iteco.fmhandroid.ui.steps.SplashScreenSteps;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimsTest {

    AuthSteps authSteps = new AuthSteps();
    ClaimsSteps claimsSteps = new ClaimsSteps();
    ClaimsScreen claimsScreen = new ClaimsScreen();
    CreateClaimSteps createClaimSteps = new CreateClaimSteps();
    CreatingClaimsScreen creatingClaimsScreen = new CreatingClaimsScreen();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    Resources resources = new Resources();
    CommonSteps commonSteps = new CommonSteps();
    CommentSteps commentSteps = new CommentSteps();
    EditClaimSteps editClaimsSteps = new EditClaimSteps();
    SplashScreenSteps splashScreenSteps = new SplashScreenSteps();


    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        splashScreenSteps.appDownloading();
        try {
            mainScreenSteps.checkMainScreenLoaded();
        } catch (NoMatchingViewException e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
        } finally {
            mainScreenSteps.checkMainScreenLoaded();
            mainScreenSteps.clickAllClaims();
        }
    }

    @Test
    @DisplayName("Проерка элементов экрана Claims")
    @Description("Корректность отображения всех элементов экрана")
    public void shouldCheckClaimsScreenElements() {
        claimsSteps.isClaimsScreen();
    }

    @Test
    @DisplayName("Проерка элементов претензии")
    @Description("Корректность отображения всех элементов претензии")
    public void shouldCheckClaimElements() {
        int index = 0;
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimElements();
    }

    @Test
    @DisplayName("Открыть и закрыть претензию")
    @Description("При нажатии на пртенезию открывается ее содержание")
    public void shouldOpenElementAndReturn() {
        int index = 0;
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimElements();
        claimsSteps.returnToPreviousScreen();
        claimsSteps.isClaimsScreen();
    }

    @Test
    @DisplayName("Открыть окно фильтра претензий")
    @Description("При нажатии на кнопку фильтра открывается окно с фильтром претензий")
    public void shouldCheckClaimsFilterWindow() {
        claimsSteps.openFilterWindow();
        claimsSteps.isFilterWindow();
    }

    @Test
    @DisplayName("Убрать отметку во всех чек-боксах фильтра")
    @Description("При фильтрации пртенезий без статуса претензий не отображается, надпсиь There is nothing here yet")
    public void shouldCheckNoClaimsAreDisplayed() {
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.emptyScreenShown();
        commonSteps.checkClaimButterflyImage();
        commonSteps.checkNothingToShowScreen();
    }

    @Test
    @DisplayName("Отфильтровать претензии со статусом Open")
    @Description("При фильтрации претензий по статусу Open отображаются только претензии со статусом Open")
    public void shouldShowOpenClaims() {
        int index = 1;
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Open");
    }

    @Test
    @DisplayName("Отфильтровать претензии со статусом In Progress")
    @Description("При фильтрации претензий по статусу In progress отображаются только претензии со статусом In progress")
    public void shouldShowInProgressClaims() {
        int index = 2;
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("In progress");
    }

    @Test
    @DisplayName("Отфильтровать претензии со статусом Executed")
    @Description("При фильтрации претензий по статусу Executed отображаются только претензии со статусом Executed")
    public void shouldShowExecutedClaims() {
        int index = 1;
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        claimsSteps.clickInProgress();
        claimsSteps.clickExecuted();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Executed");
    }

    @Test //NB! Разное написание: в окне фильтра Cancelled, а в статусе Canceled
    @DisplayName("Отфильтровать претензии со статусом Cancelled")
    @Description("При фильтрации претензий по статусу Cancelled отображаются только претензии со статусом Cancelled")
    public void shouldShowCancelledClaims() {
        int index = 2;
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        claimsSteps.clickInProgress();
        claimsSteps.clickCancelled();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Canceled");
    }

    @Test
    @DisplayName("Создать претензию с валидными значениями на кириллице")
    @Description("При заполенении текстовых полей данными на кириллице и вводе валидных значений в поля дата и время создается претензия на кириллице")
    public void shouldCreateNewClaimCyr() {
        int index = 0;
        String executor = randomExecutor();
        String title = resources.claimTitleCyr;
        String description = resources.claimDescriptionCyr;
        String date = "01.01.1980";
        String time = "01:00";
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(title);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(date);
        createClaimSteps.fillInTime(time);
        createClaimSteps.fillItDescription(description);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsScreen.claimsList.perform(swipeDown());
        claimsSteps.isClaimsScreen();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkCreatedClaimElement(title, description, date, time);

    }

    @Test
    @DisplayName("Создать претензию с валидными значениями на латинице")
    @Description("При заполенении текстовых полей данными на латинице и вводе валидных значений в поля дата и время создается претензия на латинице")
    public void shouldCreateNewClaimLatin() {
        int index = 0;
        String executor = randomExecutor();
        String title = resources.claimTitleLatin;
        String description = resources.claimDescriptionLatin;
        String date = "01.01.1980";
        String time = "01:00";
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(title);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(date);
        createClaimSteps.fillInTime(time);
        createClaimSteps.fillItDescription(description);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.isClaimsScreen();
        claimsScreen.claimsList.perform(swipeDown());
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkCreatedClaimElement(title, description, date, time);
    }

    @Test
    @DisplayName("Ручной ввод невалидного часа")
    @Description("При ручном вводе в поле время невалидного часа всплывает предупреждение о невалидном времени")
    public void shouldShowWrongHourWarning() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        createClaimSteps.clickTimeField();
        commonSteps.manualTimeInput("25", "25");
        commonSteps.checkInvalidTimeError();
    }

    @Test
    @DisplayName("Ручной ввод невалидных минут")
    @Description("При ручном вводе в поле время невалидных минут всплывает предупреждение о невалидном времени")
    public void shouldShowWrongMinuteWarning() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        createClaimSteps.clickTimeField();
        commonSteps.manualTimeInput("15", "75");
        commonSteps.checkInvalidTimeError();
    }

    @Test
    @DisplayName("Создать претензию с ручным вводом валидного времени")
    @Description("При ручном вводе в поле время валидного времени претензия создается")
    public void shouldCreateClaimWithManualTimeInput() {
        int index = 0;
        String executor = randomExecutor();
        String title = resources.claimTitleLatin;
        String description = resources.claimDescriptionLatin;
        String date = "01.01.1980";
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(title);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(date);
        createClaimSteps.fillItDescription(description);
        createClaimSteps.clickTimeField();
        commonSteps.manualTimeInput("01", "00");
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.checkCreatedClaimElement(title, description, date, "01:00");
    }

    @Test
    @DisplayName("Создать претензию с пустой датой и временем")
    @Description("Если поля время и дата остаются пустыми, претензия не создается, предупреждение о необходимости заполнить пустые поля")
    public void shouldNotCreateClaimWithEmptyTimeAndDate() {
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        commonSteps.checkFillEmptyFieldsMessage();
    }

    @Test
    @DisplayName("Создать пустую претензию")
    @Description("Претензия с пустыми полями не заполняется, всплывает предупреждение о необходимости заполнить пустые поля")
    public void shouldNotCreateEmptyClaim() {
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        commonSteps.clickSave();
        commonSteps.checkFillEmptyFieldsMessage();
    }

    @Test
    @DisplayName("Создать претензию с названием более 50 символов")
    @Description("При вводе в поле Title названия из более чем 50 символов сохраняются только 50 символов")
    public void shouldCheckTitleLength() {
        int index = 0;
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();;
        createClaimSteps.fillInTitle(resources.claimTitle51);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsScreen.claimsList.perform(swipeDown());
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.getClaimTitle();
        assertEquals("В этом названии теперь больше пятидесяти символов!", claimsSteps.getClaimTitle());
    }

    @Test
    @DisplayName("Создать претензию с пробелами в названии и описании")
    @Description("При заполнении полей title и description пробелами претензия не создается, предупреждение о необходимости заполнить пустые поля")
    public void shouldNotCreateClaimsWithSpaces() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleSpace);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillInTime(resources.claimPublicationTime);
        createClaimSteps.fillItDescription(resources.claimDescriptionSpace);
        commonSteps.clickSave();
        commonSteps.checkFillEmptyFieldsMessage();
    }

    @Test // претензия сохраняется
    @DisplayName("Создать претензию со спец символами")
    @Description("Претензия со спец симовлами в title и description не сохраняется")
    public void shouldNotCreateClaimsWithSymbols() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleSymbols);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillInTime(resources.claimPublicationTime);
        createClaimSteps.fillItDescription(resources.claimDescriptionSymbols);
        commonSteps.clickSave();
        commonSteps.checkWrongData("Wrong format data", true);
    }

    @Test
    @DisplayName("Повернуть экран при создании претензии ")
    @Description("При повороте экрана во время создания претензии введенные данные остаются в полях")
    public void shouldCreateClaimWithScreenRotation() {
        int index = 0;
        String title = resources.claimTitleLatin;
        String description = resources.claimDescriptionLatin;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(title);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        ActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        createClaimSteps.timeFieldLoaded();
        creatingClaimsScreen.claimTimeField.perform(swipeUp()).perform(swipeUp()).perform(swipeUp());
        createClaimSteps.fillItDescription(description);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        ActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        claimsSteps.claimsListLoaded();
        claimsSteps.isClaimsScreen();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        assertEquals(title, claimsSteps.getClaimTitle());
        assertEquals(description, claimsSteps.getClaimDescription());

    }

    @Test
    @DisplayName("Нажать отмену, вернуться к созданию и отменить создание претензии")
    @Description("При нажатии отмены без подтверждения продолжается слздание претензии, при подтверждении отмены создание претензии прекращается")
    public void shouldCancelClaimCreation() {
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        commonSteps.clickCancel();
        commonSteps.checkChangesMessage();
        commonSteps.clickCancelInDialog();
        createClaimSteps.isCreatingClaimScreen();
        commonSteps.clickCancel();
        commonSteps.checkChangesMessage();
        commonSteps.clickOkBtn();
        claimsSteps.isClaimsScreen();
    }

    @Test
    @DisplayName("Изменить статус претензии с Open на In progress и обратно")
    @Description("При нажатии на кнопку Take to work статус претензии меняется на In progress, при нажатии на throw off претензия меняет статус на Open")
    public void shouldChangeClaimStatusToInProgressAndBack() {
        int index = 1;
        String comment = "Повар заболел";
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.isClaimsScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.checkClaimStatus("Open");
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickTakeToWork();
        claimsSteps.checkClaimStatus("In progress");
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickThrowOff();
        claimsSteps.addCommentWhenStatusChange(comment);
        commonSteps.clickOkBtn();
        claimsSteps.checkClaimStatus("Open");
        claimsSteps.checkCommentToClaim(comment);
    }

    @Test
    @DisplayName("Изменить статус претензии с Open на Executed")
    @Description("При нажатии на кнопку Take to work статус претензии меняется на In progress, при нажатии на to execute статус претензии меняется на Executed")
    public void shouldChangeClaimStatusToExecuted() {
        int index = 0;
        String comment = "Приняли к сведению";
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle("Какая-то претензия");
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.isClaimsScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Open");
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickTakeToWork();
        claimsSteps.checkClaimStatus("In progress");
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickToExecute();
        claimsSteps.addCommentWhenStatusChange(comment);
        commonSteps.clickOkBtn();
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Executed");
        claimsSteps.checkCommentToClaim(comment);
    }

    @Test
    @DisplayName("Изменить статус претензии с Open на Cancelled")
    @Description("При нажатии на кнопку Take to work статус претензии меняется на In progress, при нажатии на cancel статус претензии меняется на Canceled")
    public void shouldChangeClaimStatusToCancelled() {
        int index = 0;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.isClaimsScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Open");
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickCancelClaim();
        claimsSteps.checkClaimStatus("Canceled");
    }

    @Test
    @DisplayName("Добавить комментарий на кириллице к претензии")
    @Description("При нажатии на кнопку добавить комментарий открывается окно комментраия, поле заполняется текстом на кириллице, комментарий сохравняется")
    public void shouldAddCyrCommentToClaim() {
        int index = 0;
        String comment = resources.commentCyr;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commonSteps.clickSave();
        claimsSteps.claimFullyOpened();
        claimsScreen.statusIcon.perform(swipeUp()).perform(swipeUp()).perform(swipeUp());
        claimsSteps.checkCommentToClaim(comment);
    }

    @Test
    @DisplayName("Добавить комментарий на латинице к претензии")
    @Description("При нажатии на кнопку добавить комментарий открывается окно комментраия, поле заполняется текстом на латинице, комментарий сохравняется")
    public void shouldAddLatinCommentToClaim() {
        int index = 0;
        String comment = resources.commentLatin;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commonSteps.clickSave();
        claimsSteps.claimFullyOpened();
        claimsScreen.statusIcon.perform(swipeUp()).perform(swipeUp()).perform(swipeUp());
        claimsSteps.checkCommentToClaim(comment);
    }

    @Test
    @DisplayName("Добавить комментарий с пробелом")
    @Description("При нажатии на кнопку добавить комментарий открывается окно комментраия, при заполнении поля комментария пробелом, комментарий не сохраняется, всплывает уведомление" +
            "о необходимости заполнить пустое поле")
    public void shouldShowWarningForCommentWithSpace() {
        int index = 0;
        String comment = resources.commentSpace;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commonSteps.clickSave();
        commonSteps.checkEmptyFieldToast();
    }

    @Test
    @DisplayName("Добавить пустой комментарий")
    @Description("При нажатии на кнопку добавить комментарий открывается окно комментраия, при сохранении пустого поля, комментарий не сохраняется, всплывает уведомление" +
            "о необходимости заполнить пустое поле")
    public void shouldShowWarningForEmptyComment() {
        int index = 0;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commonSteps.clickSave();
        commonSteps.checkEmptyFieldToast();
    }

    @Test // комментарий сохраняется
    @DisplayName("Добавить комментарий со спец символами")
    @Description("При вводе в поле комметнарий спец символов комметнарий не сохраняется")
    public void shouldShowWarningForCommentWithSymbols() {
        int index = 0;
        String comment = resources.commentSymbols;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commonSteps.clickSave();
        commonSteps.checkWrongData("Wrong format data", true);
    }

    @Test
    @DisplayName("Редактировать комментарий")
    @Description("При нажатии на кнопку редактирования комменатрия и изменения текста комментария, отображается измененный комменатрий")
    public void shouldEditComment() {
        int index = 0;
        String initialComment = resources.commentCyr;
        String editedComment = resources.editedComment;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(initialComment);
        commonSteps.clickSave();
        claimsSteps.clickCommentEditBtn(index);
        commentSteps.addComment(editedComment);
        commonSteps.clickSave();
        claimsSteps.claimFullyOpened();
        claimsSteps.checkCommentToClaim(editedComment);
    }

    @Test
    @DisplayName("Отменить редактирование комментария")
    @Description("При нажатии на отмену редактироваия комментария комментарий не сохраняется")
    public void shouldCancelCommentEditing() {
        int position = 0;
        int index = 0;
        int commentIndex = 0;
        String initialComment = resources.commentCyr;
        String editedComment = resources.editedComment;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(initialComment);
        commonSteps.clickSave();
        claimsSteps.claimFullyOpened();
        String initialCommentContent = claimsSteps.getClaimComment(position);
        claimsSteps.clickCommentEditBtn(commentIndex);
        commentSteps.addComment(editedComment);
        commonSteps.clickCancel();
        claimsSteps.claimFullyOpened();
        String commentAfterCancelledEditing = claimsSteps.getClaimComment(position);
        assertEquals(initialCommentContent, commentAfterCancelledEditing);
    }

    @Test
    @DisplayName("Отменить создание комментария")
    @Description("При нажатии на отмену комментарий не добавляется")
    public void shouldCancelCommentAddition() {
        int index = 0;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commonSteps.clickCancel();
        claimsSteps.claimFullyOpened();
        claimsSteps.statusIconIsDisplayed();
    }

    @Test // при повороте экрана не сохраняется текст в поле ввода комментария
    @DisplayName("Поворот экрана при добавлении комментария")
    @Description("При поповроте экрана в процессе создания комментария введенный в поле текст остается")
    public void shouldPreserveCommentWhenRotatingScreen() {
        int index = 0;
        String comment = "поворот экрана";
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.1980");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commentSteps.commentInputLoaded();
        ActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        commentSteps.commentInputLoaded();
        ActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        commentSteps.commentInputLoaded();
        commonSteps.clickSave();
        claimsSteps.claimFullyOpened();
        assertEquals("поворот экрана", claimsSteps.getClaimComment(0));

    }

    @Test
    @DisplayName("Редактировать можно только претензию в статусе Open")
    @Description("При попытке отредактирвать претензию в статусе In progress, Executed или Cancelled всплывает уведомление Редактирвание возможно только в статусе Open")
    public void shouldCheckEditingIsOnlyPossibleInOpenStatus() {
        int index = 0;
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickEditClaim();
        commonSteps.checkUnableToEditClaimToast();
        claimsSteps.returnToPreviousScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        claimsSteps.clickExecuted();
        commonSteps.clickOkBtn();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickEditClaim();
        commonSteps.checkUnableToEditClaimToast();
        claimsSteps.returnToPreviousScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickExecuted();
        claimsSteps.clickCancelled();
        commonSteps.clickOkBtn();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickEditClaim();
        commonSteps.checkUnableToEditClaimToast();
        claimsSteps.returnToPreviousScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickCancelled();
        claimsSteps.clickOpen();
        commonSteps.clickOkBtn();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickEditClaim();
        editClaimsSteps.isEditClaimScreen();
    }

    @Test
    @DisplayName("Редактировать претензию")
    @Description("При нажатии на редактирвание и редактировании данных в полях претензии претензия отображается с новми данными")
    public void shouldEditClaim() {
        int index = 0;
        String editedTitle = resources.claimEditedTitle;
        String editedDescription = resources.claimEditedDescription;
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickEditClaim();
        editClaimsSteps.changeClaimTitle(editedTitle);
        editClaimsSteps.changeDescription(editedDescription);
        commonSteps.clickSave();
        assertEquals(editedTitle, claimsSteps.getClaimTitle());
        assertEquals(editedDescription, claimsSteps.getClaimDescription());
    }

    @Test
    @DisplayName("Отменить редактирование претензию")
    @Description("При отмене редактирования без подтверждения продолжается редактирование, при подтверждении отмены претензия не редактируется")
    public void shouldCancelClaimEditing() {
        int index = 0;
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickEditClaim();
        commonSteps.clickCancel();
        commonSteps.checkCancellationToast();
        commonSteps.clickCancelInDialog();
        editClaimsSteps.isEditClaimScreen();
        commonSteps.clickCancel();
        commonSteps.checkCancellationToast();
        commonSteps.clickOkBtn();
        claimsSteps.statusIconIsDisplayed();
    }

}
