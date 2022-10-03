package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;


public class FilterClaimsWindow {
    public ViewInteraction filterWindowName = onView(withId(R.id.claim_filter_dialog_title));
    public ViewInteraction openCheckBox = onView(allOf(withId(R.id.item_filter_open), withText("Open")));
    public ViewInteraction inProgressCheckBox = onView(allOf(withId(R.id.item_filter_in_progress), withText("In progress")));
    public ViewInteraction executedCheckBox = onView(allOf(withId(R.id.item_filter_executed), withText("Executed")));
    public ViewInteraction cancelledCheckBox = onView(allOf(withId(R.id.item_filter_cancelled), withText("Cancelled")));
}
