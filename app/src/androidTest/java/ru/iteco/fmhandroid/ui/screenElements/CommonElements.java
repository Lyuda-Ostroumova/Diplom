package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.data.Helper;

public class CommonElements {

    public ViewInteraction deleteDialog = onView(withText("Are you sure you want to permanently delete the document? These changes cannot be reversed in the future."));
    public ViewInteraction cancelInDialog = onView(withText("Cancel")); // в диалоговых окнах
    public ViewInteraction okBtn = onView(withText("OK"));
    public ViewInteraction nothingToShowWarning = onView(withText("There is nothing here yet…"));
    public ViewInteraction refreshBtn = onView(withText("Refresh"));
    public ViewInteraction butterflyImageNews = onView(withId(R.id.empty_news_list_image_view));
    public ViewInteraction butterflyImageClaims= onView(withId(R.id.empty_claim_list_image_view));
    public ViewInteraction butterflyImageControlPanel= onView(withId(R.id.control_panel_empty_news_list_image_view));
    public ViewInteraction saveBtn = onView(withId(R.id.save_button));
    public ViewInteraction cancelBtn = onView(withId(R.id.cancel_button)); // большшая кнопка отмены
    public ViewInteraction wrongTimeError = onView(withText("Enter a valid time"));

    public ViewInteraction emptyToast(int id) {
        return onView(withText(id)).inRoot(new Helper.ToastMatcher());
    }

    public ViewInteraction errorToast(int id) {
        return onView(withText(id)).inRoot(new Helper.ToastMatcher());
    }

    public ViewInteraction emptyMessage(int id) {
        return onView(allOf(withId(android.R.id.message), withText(id)));
    }

    public ViewInteraction wrongData(String text) {
        return onView(allOf(withId(android.R.id.message), withText(text)));
    }

}
