package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class EditClaimsScreen {
    public ViewInteraction editClaimScreenName = onView(withText("Editing"));
    public ViewInteraction editClaimTitleField = onView(withId(R.id.title_edit_text));
    public ViewInteraction editClaimDescriptionField = onView(withId(R.id.description_edit_text));

}
