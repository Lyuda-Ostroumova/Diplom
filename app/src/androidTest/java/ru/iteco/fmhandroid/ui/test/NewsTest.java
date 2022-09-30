package ru.iteco.fmhandroid.ui.test;

import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.random;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomCategory;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.data.Resources;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.CommonSteps;
import ru.iteco.fmhandroid.ui.steps.ControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.CreateNewsSteps;
import ru.iteco.fmhandroid.ui.steps.EditNewsSteps;
import ru.iteco.fmhandroid.ui.steps.FilterNewsSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;

@RunWith(AllureAndroidJUnit4.class)
public class NewsTest {

    AuthSteps authSteps = new AuthSteps();
    NewsSteps newsSteps = new NewsSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    ControlPanelSteps controlPanelSteps = new ControlPanelSteps();
    FilterNewsSteps filterScreen = new FilterNewsSteps();
    Resources resources = new Resources();
    CreateNewsSteps createNewsSteps = new CreateNewsSteps();
    CommonSteps commonSteps = new CommonSteps();
    EditNewsSteps editNewsSteps = new EditNewsSteps();


    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            newsSteps.isNewsScreen();
        } catch (NoMatchingViewException e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
            SystemClock.sleep(5000);
        } finally {
            mainScreenSteps.clickAllNews();
        }
    }

    @Test
    @DisplayName("Проерка элементов экрана News")
    public void shouldCheckNewsScreenElements() {
        newsSteps.isNewsScreen();
    }

    @Test
    @DisplayName("Проверка сортировки")
    public void shouldSortNews() {
        int position = 0;
        String firstNewsTitle = newsSteps.getFirstNewsTitle(position);
        newsSteps.clickSortBtn();
        SystemClock.sleep(3000);
        newsSteps.clickSortBtn();
        String firstNewsTitleAfterSecondSorting = newsSteps.getFirstNewsTitleAfterSecondSorting(position);
        assertEquals(firstNewsTitle, firstNewsTitleAfterSecondSorting);
    }


    @Test
    @DisplayName("Развернуть любую новость")
    public void shouldShowNewsContent() {
        int position = random(0, 1, 2, 3);
        newsSteps.openNews(position);
        newsSteps.newsContentIsDisplayed(position);
    }

    @Test
    @DisplayName("Фильтр по дате")
    public void shouldFilterByDate() {
        String newsDate = resources.newsPublicationDate;
        newsSteps.openFilter();
        filterScreen.isFilterScreen();
        filterScreen.enterStartDate(newsDate);
        filterScreen.enterEndDate(newsDate);
        filterScreen.clickFilter();
        newsSteps.checkFirstNewsPublicationDate();
    }

    @Test
    @DisplayName("Нет новостей, соответствующих критериям")
    public void shouldShowNothingToShowScreen() {
        String newsDate = resources.dateForNonExistentNews;
        newsSteps.openFilter();
        filterScreen.isFilterScreen();
        filterScreen.enterStartDate(newsDate);
        filterScreen.enterEndDate(newsDate);
        filterScreen.clickFilter();
        commonSteps.checkNewsButterflyImage();
        commonSteps.checkNothingToShowScreen();
    }

    @Test
    @DisplayName("Отмена фильтрации")
    public void shouldCancelFilter() {
        String newsDate = resources.newsPublicationDate;
        newsSteps.openFilter();
        filterScreen.isFilterScreen();
        filterScreen.enterStartDate(newsDate);
        commonSteps.clickCancel();
        newsSteps.isNewsScreen();
    }

    @Test
    @DisplayName("Перейти в панель управления")
    public void shouldTransferToControlPanel() {
        newsSteps.clickEditBtn();
        controlPanelSteps.isControlPanelScreen();
    }

    @Test
    @DisplayName("Изменить порядок отображения новостей в панели управления")
    public void shouldChangeNewsOrder() {
        newsSteps.clickEditBtn();
        controlPanelSteps.checkControlPanelSorting();
    }

    @Test
    @DisplayName("Проверка чек-боксов расширенного фильтра")
    public void shouldOpenFilter() {
        newsSteps.clickEditBtn();
        controlPanelSteps.openNewsFilterScreen();
        filterScreen.clickOnActiveCheckBox();
        filterScreen.checkActiveCheckBoxStatus(false);
        filterScreen.clickOnNotActiveCheckBox();
        filterScreen.checkNotActiveCheckBoxStatus(false);
    }

    @Test
    @DisplayName("Фильтр новостей через панель управления по статусу")
    public void shouldCheckFilteredNewsStatus() {
        newsSteps.clickEditBtn();
        controlPanelSteps.openNewsFilterScreen();
        filterScreen.clickOnNotActiveCheckBox();
        filterScreen.clickFilter();
        SystemClock.sleep(3000);
        controlPanelSteps.checkActiveNewsStatus();
        controlPanelSteps.openNewsFilterScreen();
        filterScreen.clickOnActiveCheckBox();
        filterScreen.clickFilter();
        SystemClock.sleep(3000);
        controlPanelSteps.checkNotActiveNewsStatus();
    }

    @Test
    @DisplayName("Нет новостей, соответствующих критериям, в панели управления")
    public void shouldShowNothingToShowScreenInControlPanel() {
        String newsDate = resources.dateForNonExistentNews;
        newsSteps.clickEditBtn();
        controlPanelSteps.openNewsFilterScreen();
        filterScreen.enterStartDate(newsDate);
        filterScreen.enterEndDate(newsDate);
        filterScreen.clickFilter();
        commonSteps.checkControlPanelButterflyImage();
        commonSteps.checkNothingToShowScreen();
    }

    @Test
    @DisplayName("Отмена фильтра новостей через панель управления")
    public void shouldCancelFiltering() {
        newsSteps.clickEditBtn();
        controlPanelSteps.openNewsFilterScreen();
        commonSteps.clickCancel();
        SystemClock.sleep(3000);
        controlPanelSteps.isControlPanelScreen();
    }

    @Test
    @DisplayName("Создать новость на кирилице")
    public void shouldCreateNewsCyr() {
        int position = 0;
        String titleText = resources.newsTitleCyr;
        String descriptionText = resources.newsDescriptionCyr;
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.createNews(randomCategory(), titleText, resources.newsPublicationDate, resources.newsPublicationTime, descriptionText);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        controlPanelSteps.checkCreatedNews(position, titleText, descriptionText);
        mainScreenSteps.goToNewsScreen();
        SystemClock.sleep(3000);
        newsSteps.openNews(position);
        SystemClock.sleep(3000);
        String createdDescription = newsSteps.getCreatedNewsDescription(position);
        assertEquals(descriptionText, createdDescription);
    }

    @Test
    @DisplayName("Создать новость на латинице")
    public void shouldCreateNewsLatin() {
        int position = 0;
        String titleText = resources.newsTitleLatin;
        String descriptionText = resources.newsDescriptionLatin;
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.createNews(randomCategory(), titleText, resources.newsPublicationDate, resources.newsPublicationTime, descriptionText);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        controlPanelSteps.checkCreatedNews(position, titleText, descriptionText);
        mainScreenSteps.goToNewsScreen();
        newsSteps.openNews(position);
        SystemClock.sleep(3000);
        String createdDescription = newsSteps.getCreatedNewsDescription(position);
        assertEquals(descriptionText, createdDescription);
    }

    @Test
    @DisplayName("Создать новость со спец символами")
    public void shouldCreateNewsWithSymbols() {
        String titleText = resources.newsTitleSymbols;
        String descriptionText = resources.newsDescriptionSymbols;
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.createNews(randomCategory(), titleText, resources.newsPublicationDate, resources.newsPublicationTime, descriptionText);
        commonSteps.clickSave();
        SystemClock.sleep(3000);
        commonSteps.checkWrongData("Wrong format data", true);
    }

    @Test
    @DisplayName("Создать новость с пробелами")
    public void shouldCreateNewsWithSpaces() {
        String titleText = resources.newsTitleSpace;
        String descriptionText = resources.newsDescriptionSpace;
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.createNews(randomCategory(), titleText, " ", " ", descriptionText);
        commonSteps.clickSave();
        commonSteps.checkErrorToast(R.string.empty_fields, true);
    }

    @Test
    @DisplayName("Создать новость с пустым полем Description")
    public void shouldNotCreateNewsWithEmptyDescription() {
        String titleText = resources.newsTitleCyr;
        String descriptionText = "";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.createNews(randomCategory(), titleText, resources.newsPublicationDate, resources.newsPublicationTime, descriptionText);
        commonSteps.clickSave();
        commonSteps.checkErrorToast(R.string.empty_fields, true);
    }

    @Test
    @DisplayName("Создать новость с пустыми полями")
    public void shouldNotCreateNewsWithEmptyFields() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        commonSteps.clickSave();
        commonSteps.checkErrorToast(R.string.empty_fields, true);
    }

    @Test
    @DisplayName("Создать новость с ручным вводом невалидного часа")
    public void shouldShowInvalidHourWarning() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.fillInNewsCategory(randomCategory());
        createNewsSteps.fillInNewsTitle(resources.newsTitleCyr);
        createNewsSteps.fillInPublicationDate(resources.newsPublicationDate);
        createNewsSteps.clickTimeField();
        commonSteps.manualTimeInput("25", "25");
        commonSteps.checkWrongTimeError();
    }

    @Test
    @DisplayName("Создать новость с ручным вводом невалидных минут")
    public void shouldShowInvalidMinuteWarning() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.fillInNewsCategory(randomCategory());
        createNewsSteps.fillInNewsTitle(resources.newsTitleCyr);
        createNewsSteps.fillInPublicationDate(resources.newsPublicationDate);
        createNewsSteps.clickTimeField();
        commonSteps.manualTimeInput("15", "75");
        commonSteps.checkWrongTimeError();
    }

    @Test
    @DisplayName("Отменить создание новости")
    public void shouldCancelNewsCreation() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.fillInPublicationDate(resources.newsPublicationDate);
        createNewsSteps.fillInTime(resources.newsPublicationTime);
        commonSteps.clickCancel();
        commonSteps.clickOkBtn();
        controlPanelSteps.isControlPanelScreen();
    }

    @Test
    @DisplayName("Отменить создание новости и вернуться к созданию")
    public void shouldCancelNewsCreationAndReturn() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.fillInPublicationDate(resources.newsPublicationDate);
        createNewsSteps.fillInTime(resources.newsPublicationTime);
        commonSteps.clickCancel();
        commonSteps.clickCancelInDialog();
        createNewsSteps.isCreatingNewsScreen();
    }


    @Test
    @DisplayName("Изменить статус созданной новости")
    public void shouldEditNews() {
        int position = 0;
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), resources.newsTitleCyr, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(2000);
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.isEditNewsScreen();
        editNewsSteps.editStatus(); // to "not active"
        SystemClock.sleep(2000);
        controlPanelSteps.checkNotActiveNewsStatus();
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.editStatus(); // to "active"
        SystemClock.sleep(2000);
        controlPanelSteps.checkActiveNewsStatus();
    }

    @Test
    @DisplayName("Редактирование новости")
    public void shouldEditNewsTitleAndDescription() {
        int position = 0;
        String newTitle = "Новое название";
        String newDescription = "Новое описание";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), resources.newsTitleCyr, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(2000);
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.isEditNewsScreen();
        editNewsSteps.editTitle(newTitle);
        editNewsSteps.editDescription(newDescription);
        commonSteps.clickSave();
        controlPanelSteps.clickOnRandomlySelectedNewsItem(position);
        assertEquals(newTitle, controlPanelSteps.getEditedNewsTitle(position));
        assertEquals(newDescription, controlPanelSteps.getEditedNewsDescription(position));
    }

    @Test
    @DisplayName("Отмена редактирование новости")
    public void shouldCancelNewsEditing() {
        int position = 0;
        String newTitle = "Новое название";
        String newDescription = "Новое описание";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), resources.newsTitleCyr, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(2000);
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.isEditNewsScreen();
        editNewsSteps.editTitle(newTitle);
        editNewsSteps.editDescription(newDescription);
        commonSteps.clickCancel();
        commonSteps.clickOkBtn();
        controlPanelSteps.isControlPanelScreen();
        assertEquals(resources.newsTitleCyr, controlPanelSteps.getEditedNewsTitle(position));
    }

    @Test
    @DisplayName("Отмена редактирование новости и возврат к редактированию")
    public void shouldCancelNewsEditingAndReturnToEditing() {
        int position = 0;
        String newTitle = "Новое название";
        String newDescription = "Новое описание";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), resources.newsTitleCyr, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(2000);
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.isEditNewsScreen();
        editNewsSteps.editTitle(newTitle);
        editNewsSteps.editDescription(newDescription);
        commonSteps.clickCancel();
        commonSteps.clickCancelInDialog();
        editNewsSteps.isEditNewsScreen();
    }

    @Test
    @DisplayName("Удаление новости")
    public void shouldDeleteNews() {
        String title = "Удаленная новость";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), title, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(2000);
        controlPanelSteps.deleteNews();
        controlPanelSteps.confirmDeleting();
        SystemClock.sleep(2000);
    }

    @Test
    @DisplayName("Отмена удаления новости")
    public void shouldCancelDeleting() {
        int position = 0;
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), resources.newsTitleCyr, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        SystemClock.sleep(2000);
        controlPanelSteps.deleteNews();
        controlPanelSteps.cancelDeleting();
        controlPanelSteps.isControlPanelScreen();
        SystemClock.sleep(2000);
        controlPanelSteps.checkCreatedNews(position, resources.newsTitleCyr, resources.newsDescriptionCyr);
    }

}