package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Helper;

public class FilterNewsScreen {
    public ViewInteraction filterScreenName = onView(withText("Filter news"));
    public ViewInteraction categoryField = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    public ViewInteraction startDateField = onView(withId(R.id.news_item_publish_date_start_text_input_edit_text));
    public ViewInteraction endDateField = onView(withId(R.id.news_item_publish_date_end_text_input_edit_text));
    public ViewInteraction filterBtn = onView(withText("FILTER"));
    public ViewInteraction cancelBtn = onView(withId(R.id.cancel_button));




}
