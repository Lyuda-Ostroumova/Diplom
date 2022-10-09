package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static ru.iteco.fmhandroid.ui.data.Helper.childAtPosition;
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;


import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import ru.iteco.fmhandroid.R;


public class MainScreenElements {
    // верхняя панель:
    public ViewInteraction tradeMark = onView(withId(R.id.trademark_image_view));
    public ViewInteraction menuBtn = onView(withId(R.id.main_menu_image_button));
    public ViewInteraction ourMissionBtn = onView(withId(R.id.our_mission_image_button));
    public ViewInteraction logOutBtn = onView(withId(R.id.authorization_image_button));
    public ViewInteraction logOut = onView(withText("Log out"));

    // кнопка Menu
    public ViewInteraction titleMain = onView(withText("Main"));
    public ViewInteraction titleClaims = onView(withText("Claims"));
    public ViewInteraction titleNews = onView(withText("News"));
    public ViewInteraction titleAbout = onView(withText("About"));

    // новости
    public ViewInteraction news = onView(withText("News"));
    public ViewInteraction newsUnit = onView(withId(R.id.news_list_recycler_view));
    public ViewInteraction allNewsBtn = onView(withId(R.id.all_news_text_view));
    public ViewInteraction expandSingleNews = onView(allOf(withId(R.id.news_list_recycler_view), childAtPosition(withId(R.id.all_news_cards_block_constraint_layout), 0)));
    public ViewInteraction expandNewsFeedButton = onView(
            allOf(withId(R.id.expand_material_button),
                    childAtPosition(
                            withClassName(is("android.widget.LinearLayout")), childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")), withId(R.id.container_list_news_include_on_fragment_main),
                                    0),
                            4)));

    // претензии
    public ViewInteraction claims = onView(withText("Claims"));
    public ViewInteraction claimsUnit = onView(withId(R.id.claim_list_recycler_view));
    public ViewInteraction allClaimsBtn = onView(withId(R.id.all_claims_text_view));
    public ViewInteraction newClaimBtn = onView(withId(R.id.add_new_claim_material_button));

    public ViewInteraction expandClaimsFeedButton = onView(
            allOf(withId(R.id.expand_material_button),
                    childAtPosition(
                            withClassName(is("android.widget.LinearLayout")), childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")), withId(R.id.container_list_claim_include_on_fragment_main),
                                    0),
                            3)));

    public ViewInteraction singleClaim = onView(allOf(withId(R.id.claim_list_recycler_view),
            childAtPosition(withId(R.id.all_claims_cards_block_constraint_layout), 4)));

    public ViewInteraction claimList = onView(
            allOf(withId(R.id.claim_list_recycler_view),
                    childAtPosition(
                            withClassName(is("android.widget.LinearLayout")), withId(R.id.all_claims_cards_block_constraint_layout),
                            4)));


}
