package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomCategory;
import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;

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
import ru.iteco.fmhandroid.ui.screenElements.ControlPanelScreen;
import ru.iteco.fmhandroid.ui.screenElements.NewsScreen;
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
    ControlPanelScreen controlPanelScreen = new ControlPanelScreen();
    FilterNewsSteps filterScreen = new FilterNewsSteps();
    Resources resources = new Resources();
    CreateNewsSteps createNewsSteps = new CreateNewsSteps();
    CommonSteps commonSteps = new CommonSteps();
    EditNewsSteps editNewsSteps = new EditNewsSteps();
    NewsScreen newsScreen = new NewsScreen();


    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        elementWaiting(withId(R.id.splashscreen_image_view), 3000);
        try {
            elementWaiting(withText("all claims"), 5000);
        } catch (NoMatchingViewException e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
        } finally {
            elementWaiting(withText("all claims"),  10000);
            mainScreenSteps.clickAllNews();
        }
    }

    @Test
    @DisplayName("Проерка элементов экрана News")
    @Description("Корректность отображения всех элементов экарна News")
    public void shouldCheckNewsScreenElements() {
        newsSteps.isNewsScreen();
    }

    @Test
    @DisplayName("Проверка сортировки")
    @Description("При нажатии на кнопку сортировки меняется порядок отображения новостей по дате создания новости")
    public void shouldSortNews() {
        int position = 0;
        String firstNewsTitle = newsSteps.getFirstNewsTitle(position);
        newsSteps.clickSortBtn();
        newsScreen.allNewsList.perform(swipeDown());
        newsSteps.clickSortBtn();
        newsScreen.allNewsList.perform(swipeDown());
        elementWaiting(withId(R.id.all_news_cards_block_constraint_layout), 10000);
        String firstNewsTitleAfterSecondSorting = newsSteps.getFirstNewsTitleAfterSecondSorting(position);
        assertEquals(firstNewsTitle, firstNewsTitleAfterSecondSorting);
    }


    @Test
    @DisplayName("Развернуть любую новость")
    @Description("При нажатии на новость разворачивается ее содержание")
    public void shouldShowNewsContent() {
        int position = 0;
        newsSteps.openNews(position);
        newsSteps.newsContentIsDisplayed(position);
    }

    @Test
    @DisplayName("Фильтр по дате")
    @Description("Отображабтся только те новости, которые были созданы в указанный промежуток времени")
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
    @Description("При отсуствии новостей, удовлетворяющим критериям поиска (дате создания), на экране видна надпись There is nothing here yet")
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
    @Description("Выход из экрана фильтра без фильтрации новостей")
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
    @Description("При нажатии на кнопку в виде блокнота с карандашом пользователь переходит на вкладку Control panel")
    public void shouldTransferToControlPanel() {
        newsSteps.clickEditBtn();
        controlPanelSteps.isControlPanelScreen();
    }

    @Test
    @DisplayName("Изменить порядок отображения новостей в панели управления")
    @Description("При нажатии на кнопку сортировки меняется порядок отображения новостей по дате создания новости")
    public void shouldChangeNewsOrder() {
        newsSteps.clickEditBtn();
        controlPanelSteps.checkControlPanelSorting();
    }

    @Test
    @DisplayName("Проверка чек-боксов расширенного фильтра")
    @Description("При нажатии на чек-боксы они становятся неактивными")
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
    @Description("При фильтре новостей по статусу Active/Not Active в списке новостей отображаются только новости с этим статусом")
    public void shouldCheckFilteredNewsStatus() {
        newsSteps.clickEditBtn();
        controlPanelSteps.openNewsFilterScreen();
        filterScreen.clickOnNotActiveCheckBox();
        filterScreen.clickFilter();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.checkActiveNewsStatus();
        controlPanelSteps.openNewsFilterScreen();
        filterScreen.clickOnActiveCheckBox();
        filterScreen.clickFilter();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.checkNotActiveNewsStatus();
    }

    @Test
    @DisplayName("Нет новостей, соответствующих критериям, в панели управления")
    @Description("При отсутсвии новостей, удовлетворяющим критериям поиска, пользователь видит экран с надписьб There is nothing here yet")
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
    @Description("Выход из фильтра с помощью кнопки отмена")
    public void shouldCancelFiltering() {
        newsSteps.clickEditBtn();
        controlPanelSteps.openNewsFilterScreen();
        commonSteps.clickCancel();
        controlPanelSteps.isControlPanelScreen();
    }

    @Test
    @DisplayName("Создать новость на кирилице")
    @Description("При заполнении текстовых полей данным на кириллице и вводе валидных значений в поля с датой и временем создается новость на кириллице")
    public void shouldCreateNewsCyr() {
        int position = 0;
        String titleText = resources.newsTitleCyr;
        String descriptionText = resources.newsDescriptionCyr;
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.createNews(randomCategory(), titleText, resources.newsPublicationDate, resources.newsPublicationTime, descriptionText);
        commonSteps.clickSave();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.checkCreatedNews(position, titleText, descriptionText);
        mainScreenSteps.goToNewsScreen();
        newsScreen.allNewsList.perform(swipeDown());
        newsSteps.openNews(position);
        String createdDescription = newsSteps.getCreatedNewsDescription(position);
        assertEquals(descriptionText, createdDescription);
    }

    @Test
    @DisplayName("Создать новость на латинице")
    @Description("При заполнении текстовых полей данным на латинице и вводе валидных значений в поля с датой и временем создается новость на латинице")
    public void shouldCreateNewsLatin() {
        int position = 0;
        String titleText = resources.newsTitleLatin;
        String descriptionText = resources.newsDescriptionLatin;
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.createNews(randomCategory(), titleText, resources.newsPublicationDate, resources.newsPublicationTime, descriptionText);
        commonSteps.clickSave();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.checkCreatedNews(position, titleText, descriptionText);
        mainScreenSteps.goToNewsScreen();
        newsScreen.allNewsList.perform(swipeDown());
        newsSteps.openNews(position);
        String createdDescription = newsSteps.getCreatedNewsDescription(position);
        assertEquals(descriptionText, createdDescription);
    }

    @Test
    @DisplayName("Удаление новости")
    @Description("При нажатии и подтверждении удаления новость удаляется")
    public void shouldDeleteNews() {
        String title = "Удаленная новость";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), title, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.deleteNews(title);
        controlPanelSteps.confirmDeleting();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.isControlPanelScreen();
    }

    @Test
    @DisplayName("Отмена удаления новости")
    @Description("Если отмена не подтверждена, новость не удаляется")
    public void shouldCancelDeleting() {
        String title = "Неудаляемая новость";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), title, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.deleteNews(title);
        controlPanelSteps.cancelDeleting();
        controlPanelSteps.isControlPanelScreen();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.checkNewsExists(title);
    }

    @Test
    @DisplayName("Создать новость со спец символами")
    @Description("При заполнении текстовых полей спец символами и вводе валидных значений в поля с датой и временем новость не создается")
    public void shouldCreateNewsWithSymbols() {
        String titleText = resources.newsTitleSymbols;
        String descriptionText = resources.newsDescriptionSymbols;
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        createNewsSteps.createNews(randomCategory(), titleText, resources.newsPublicationDate, resources.newsPublicationTime, descriptionText);
        commonSteps.clickSave();
        commonSteps.checkWrongData("Wrong format data", true);
    }

    @Test
    @DisplayName("Создать новость с пробелами")
    @Description("При заполнении текстовых полей пробелом новость не создается, всплывает предупреждение о необходимости зполнить поля")
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
    @Description("При незаполненном поле Description новсть не создается, всплывает предупреждение о необходимости заоплнить поле")
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
    @Description("Новость с пустыми полями не создается, всплывает предупреждение о необходимости заполнить поля полях")
    public void shouldNotCreateNewsWithEmptyFields() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.isCreatingNewsScreen();
        commonSteps.clickSave();
        commonSteps.checkErrorToast(R.string.empty_fields, true);
    }

    @Test
    @DisplayName("Создать новость с ручным вводом невалидного часа")
    @Description("При ручном вводе невалидного часа при создании новости всплывает предупреждение о невалидном значении времени")
    public void shouldShowInvalidHourWarning() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.fillInNewsCategory(randomCategory());
        createNewsSteps.fillInNewsTitle(resources.newsTitleCyr);
        createNewsSteps.fillInPublicationDate(resources.newsPublicationDate);
        createNewsSteps.clickTimeField();
        commonSteps.manualTimeInput("25", "25");
        elementWaiting(withText("Enter a valid time"), 10000);
        commonSteps.checkWrongTimeError();
    }

    @Test
    @DisplayName("Создать новость с ручным вводом невалидных минут")
    @Description("При ручном вводе невалидного знаяения минут при создании новости всплывает предупреждение о невалидном значении времени")
    public void shouldShowInvalidMinuteWarning() {
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.fillInNewsCategory(randomCategory());
        createNewsSteps.fillInNewsTitle(resources.newsTitleCyr);
        createNewsSteps.fillInPublicationDate(resources.newsPublicationDate);
        createNewsSteps.clickTimeField();
        commonSteps.manualTimeInput("15", "75");
        elementWaiting(withText("Enter a valid time"), 10000);
        commonSteps.checkWrongTimeError();
    }

    @Test
    @DisplayName("Отменить создание новости")
    @Description("При нажатии на кнопку отмены и подтверждения отмены новость не создается")
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
    @Description("При нажатии на кнопку отмены без подтверждения пользователь продолжает создание новости")
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
    @Description("При нажатии на редактирование можно изменить статус новости с Active на Not Active и обратно. Новость отображается с новым статусом")
    public void shouldEditNewsStatus() {
        int position = 0;
        String newsTitle = resources.newsTitleCyr;
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), newsTitle, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.editStatus(); // to "not active"
        commonSteps.clickSave();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.checkNotActiveNewsStatus();
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.editStatus(); // to "active"
        commonSteps.clickSave();
        controlPanelSteps.checkActiveNewsStatus();
    }

    @Test
    @DisplayName("Редактирование новости")
    @Description("При нажатии на редактирвание новости и изменение данных новость отображается с новыми данными")
    public void shouldEditNewsTitleAndDescription() {
        int position = 0;
        String newTitle = "Отредактированное название";
        String newDescription = "Отредактированное описание";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), resources.newsTitleCyr, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
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
    @Description("При нажатии кнопку отмены и подтверждении отмены редактирования новость не изменятеся")
    public void shouldCancelNewsEditing() {
        int position = 0;
        String newTitle = "Новое название";
        String newDescription = "Новое описание";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), resources.newsTitleCyr,
                resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.isEditNewsScreen();
        editNewsSteps.editTitle(newTitle);
        editNewsSteps.editDescription(newDescription);
        commonSteps.clickCancel();
        commonSteps.clickOkBtn();
        controlPanelSteps.isControlPanelScreen();
        controlPanelScreen.blockOfNews.perform(swipeDown());
        controlPanelSteps.clickOnRandomlySelectedNewsItem(position);
        assertEquals(resources.newsTitleCyr, controlPanelSteps.getEditedNewsTitle(position));
    }

    @Test
    @DisplayName("Отмена редактирование новости и возврат к редактированию")
    @Description("Если отмена редактирования не подтверждается, редактирование новости может быть продолжено")
    public void shouldCancelNewsEditingAndReturnToEditing() {
        int position = 0;
        String newTitle = "Новое название";
        String newDescription = "Новое описание";
        newsSteps.clickEditBtn();
        controlPanelSteps.clickCreateNewsBtn();
        createNewsSteps.createNews(randomCategory(), resources.newsTitleCyr, resources.newsPublicationDate, resources.newsPublicationTime, resources.newsDescriptionCyr);
        commonSteps.clickSave();
        elementWaiting(withId(R.id.news_list_recycler_view), 10000);
        controlPanelSteps.clickEditNews(position);
        editNewsSteps.isEditNewsScreen();
        editNewsSteps.editTitle(newTitle);
        editNewsSteps.editDescription(newDescription);
        commonSteps.clickCancel();
        commonSteps.clickCancelInDialog();
        editNewsSteps.isEditNewsScreen();
    }

}