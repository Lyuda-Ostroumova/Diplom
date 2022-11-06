package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomExecutor;
import static ru.iteco.fmhandroid.ui.data.Helper.elementWaiting;

import androidx.test.espresso.PerformException;
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
import ru.iteco.fmhandroid.ui.screenElements.MainScreenElements;
import ru.iteco.fmhandroid.ui.steps.AboutUsSteps;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.CommonSteps;
import ru.iteco.fmhandroid.ui.steps.CreateClaimSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.steps.OurMissionSteps;
import ru.iteco.fmhandroid.ui.steps.SplashScreenSteps;

@RunWith(AllureAndroidJUnit4.class)
public class MainScreenTest {
    AuthSteps authSteps = new AuthSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    MainScreenElements mainScreenElements = new MainScreenElements();
    ClaimsSteps claimsSteps = new ClaimsSteps();
    NewsSteps newsSteps = new NewsSteps();
    AboutUsSteps aboutUsSteps = new AboutUsSteps();
    OurMissionSteps ourMissionSteps = new OurMissionSteps();
    CreateClaimSteps createClaim = new CreateClaimSteps();
    Resources resources = new Resources();
    CommonSteps commonSteps = new CommonSteps();
    SplashScreenSteps splashScreenSteps = new SplashScreenSteps();


    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        splashScreenSteps.appDownloading();
        try {
            mainScreenSteps.checkMainScreenLoaded();
        } catch (PerformException e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
        } finally {
            mainScreenSteps.checkMainScreenLoaded();
        }
    }

    @Test
    @DisplayName("Проверка элементов экрана")
    @Description("Проверка корректности отображения всех элементов экаран Main")
    public void shouldCheckMainScreenElements() {
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Проверка списка вкладок кнопки меню")
    @Description("При нажатии на кнопку меню в выпадающем списке есть разделы Main, Claims, News, About")
    public void shouldCheckActionMenuScreenList() {
        mainScreenSteps.clickActionMenuBtn();
        mainScreenSteps.checkMenuList();
    }

    @Test
    @DisplayName("Переход по вкладкам с помощью кнопки меню")
    @Description("При нажатии на название экрана в выпадающем списке кнопки меню, пользователь переходит на соответствующую вкладку приложения")
    public void shouldCheckTransitionToScreensViaMenuBtn() {
        mainScreenSteps.goToClaimsScreen();
        claimsSteps.isClaimsScreen();
        mainScreenSteps.goToMainScreen();
        mainScreenSteps.isMainScreen();
        mainScreenSteps.goToNewsScreen();
        newsSteps.isNewsScreen();
        mainScreenSteps.goToAboutScreen();
        aboutUsSteps.isAboutUsScreen();
    }

    @Test
    @DisplayName("Переход на вкладку Love is all")
    @Description("При нажатии на кнопку в виде бабочки пользователь переходит на вкладку Love is all")
    public void shouldCheckTransitionToOurMissionScreen() {
        mainScreenSteps.clickOurMissionBtn();
        ourMissionSteps.isOurMissionScreen();
    }

    @Test
    @DisplayName("Выход из приложения")
    @Description("При нажатии на кнопку в виде человечка пользователь может выйти из приложения")
    public void shouldCheckLogOut() {
        mainScreenSteps.clickLogOutBtn();
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Перейти на вкладку Новости с помощью All News и вернутся обратно")
    @Description("При нажатии на вкладке основного экрана кнопки All News пользователь переходит на вкладку News и может вернуться на оснвоной экран")
    public void shouldCheckAllNewsBtn() {
        mainScreenSteps.clickAllNews();
        newsSteps.isNewsScreen();
        mainScreenSteps.goToMainScreen();
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Перейти на вкладку Претензии с помощью All Claims и вернутся обратно")
    @Description("При нажатии на вкладке основного экрана кнопки All Claims пользователь переходит на вкладку Claims и может вернуться на оснвоной экран")
    public void shouldCheckAllClaimsBtn() {
        mainScreenSteps.clickAllClaims();
        claimsSteps.isClaimsScreen();
        mainScreenSteps.goToMainScreen();
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Развернуть/свернуть блок новостей")
    @Description("При нажатии на блок новостей новости сворачиваются, при повтороноа нажатии - разворачиваются")
    public void shouldShowOrHideNewsBlock() {
        mainScreenSteps.expandAllNews(); // свернуть новости
        mainScreenSteps.allNewsNotDisplayed();
        mainScreenSteps.expandAllNews();
        mainScreenSteps.allNewsDisplayed();
    }

    @Test
    @DisplayName("Развернуть/свернуть блок претензий")
    @Description("При нажатии на блок претензий претензии сворачиваются, при повтороноа нажатии - разворачиваются")
    public void shouldShowOrHideClaimsBlock() {
        mainScreenSteps.expandAllClaims(); // свернуть заявки
        mainScreenSteps.allClaimsNotDisplayed();
        mainScreenSteps.expandAllClaims();
        mainScreenSteps.allClaimsDisplayed();
    }

    @Test
    @DisplayName("Перейти к созданию претензии и вернуться обратно на главный экран")
    @Description("При нажатии на кнопку + пользователь переходит на экран создания претензии. При нажатии Cancel возвращается обратно на основной экран")
    public void shouldCheckNewClaimBtn() {
        mainScreenSteps.clickNewClaimBtn();
        createClaim.isCreatingClaimScreen();
        commonSteps.clickCancel();
        commonSteps.clickOkBtn();
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Создать претензию через кнопку +")
    @Description("Создание претнезии через кнопку +. Претензия появляется на сновоном экране")
    public void shouldCreateClaimViaPlusBtn() {
        int position = 0;
        String executor = randomExecutor();
        String title = "Новость на основном экране";
        String description = resources.claimDescriptionCyr;
        String date = "01.01.1980";
        String time = "01:00";
        mainScreenSteps.clickNewClaimBtn();
        createClaim.isCreatingClaimScreen();
        createClaim.fillInTitle(title);
        createClaim.fillInExecutor(executor);
        createClaim.fillInDate(date);
        createClaim.fillInTime(time);
        createClaim.fillItDescription(description);
        commonSteps.clickSave();
        mainScreenSteps.checkMainScreenLoaded();
        mainScreenElements.titleClaims.perform(swipeUp());
        mainScreenSteps.claimOnMainScreenLoaded();
        mainScreenSteps.clickClaimOnMainScreen(position);
        claimsSteps.claimFullyOpened();
        assertEquals(title, claimsSteps.getClaimTitle());
        assertEquals(description, claimsSteps.getClaimDescription());
        assertEquals(date, claimsSteps.getClaimDate());
        assertEquals(time, claimsSteps.getClaimTime());
    }

    @Test
    @DisplayName("Развернуть отдельную новость")
    @Description("При нажатии на отдельную новость разворачивается ее содержание")
    public void shouldExpandAndHideSingleNews() {
        int position = 0;
        mainScreenSteps.expandSingleNews(position);
        mainScreenSteps.descriptionIsDisplayed(position);
    }

    @Test
    @DisplayName("Развернуть/свернуть отдельную пртенезию")
    @Description("При нажатии на претензию открывается окно с претензией и ее содержанием")
    public void shouldExpandSingleClaim() {
        mainScreenSteps.clickClaimOnMainScreen(0);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimElements();
        claimsSteps.returnToPreviousScreen();
        mainScreenSteps.isMainScreen();

    }
}


