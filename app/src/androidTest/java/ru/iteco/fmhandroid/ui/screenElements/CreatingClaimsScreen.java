package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class CreatingClaimsScreen {
    public ViewInteraction creatingClaimsScreenName = onView(allOf(withId(R.id.custom_app_bar_title_text_view), withText("Creating")));

    public ViewInteraction titleField = onView(withId(R.id.title_edit_text));
    public ViewInteraction executorField = onView(withId(R.id.executor_drop_menu_auto_complete_text_view));
    public ViewInteraction claimDateField = onView(withId(R.id.date_in_plan_text_input_edit_text));
    public ViewInteraction claimTimeField = onView(withId(R.id.time_in_plan_text_input_edit_text));
    public ViewInteraction claimDescriptionField = onView(withId(R.id.description_edit_text));
}
