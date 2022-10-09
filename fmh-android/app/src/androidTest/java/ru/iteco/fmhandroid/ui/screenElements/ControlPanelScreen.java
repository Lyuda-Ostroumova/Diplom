package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import static ru.iteco.fmhandroid.ui.data.Helper.childAtPosition;
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class ControlPanelScreen {
    public ViewInteraction controlPanelScreenName = onView(withText("Control panel"));
    public ViewInteraction sortBtn = onView(withId(R.id.sort_news_material_button));
    public ViewInteraction newsFilterBtn = onView(withId(R.id.filter_news_material_button));
    public ViewInteraction createNewsBtn = onView(withId(R.id.add_news_image_view));
    public ViewInteraction blockOfNews = onView(withId(R.id.news_list_recycler_view));
    public ViewInteraction newsItemTitle = onView(withIndex(withId(R.id.news_item_title_text_view), 0));
    public ViewInteraction newsDescription = onView(withIndex(withId(R.id.news_item_description_text_view), 0));
    public ViewInteraction deleteNewsBtn(String newsTitle) {
        return onView(allOf(withId(R.id.delete_news_item_image_view), withParent(withParent(allOf(withId(R.id.news_item_material_card_view), withChild(withChild(withText(newsTitle))))))));
    }
    public ViewInteraction newsStatusActive = onView(withIndex(withId(R.id.news_item_published_text_view), 0));
    public ViewInteraction newsStatusNotActive = onView(withIndex(withId(R.id.news_item_published_text_view), 0));


}
