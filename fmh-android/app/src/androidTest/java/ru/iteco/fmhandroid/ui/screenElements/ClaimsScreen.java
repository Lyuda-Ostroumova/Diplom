package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import static ru.iteco.fmhandroid.ui.data.Helper.childAtPosition;
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import android.view.View;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.core.IsInstanceOf;

import ru.iteco.fmhandroid.R;

public class ClaimsScreen {

    public ViewInteraction claimsScreenName = onView(withText("Claims"));
    public ViewInteraction claimsFilterBtn = onView(withId(R.id.filters_material_button));
    public ViewInteraction createClaimBtn = onView(withId(R.id.add_new_claim_material_button));
    public ViewInteraction claimsList = onView(withId(R.id.claim_list_recycler_view));
    public ViewInteraction titleLabel = onView(withId(R.id.title_label_text_view));
    public ViewInteraction title = onView(withId(R.id.title_text_view));
    public ViewInteraction executorLabel = onView(withId(R.id.executor_name_label_text_view));
    public ViewInteraction executorName = onView(withId(R.id.executor_name_text_view));
    public ViewInteraction dateLabel = onView(withId(R.id.plane_date_label_text_view));
    public ViewInteraction claimDate = onView(withId(R.id.plane_date_text_view));
    public ViewInteraction claimTime = onView(withId(R.id.plan_time_text_view));
    public ViewInteraction description = onView(withId(R.id.description_material_card_view));
    public ViewInteraction descriptionText = onView(withId(R.id.description_text_view));
    public ViewInteraction authorLabel = onView(withId(R.id.author_label_text_view));
    public ViewInteraction authorName = onView(withId(R.id.author_name_text_view));
    public ViewInteraction createdLabel = onView(withId(R.id.create_data_label_text_view));
    public ViewInteraction dateCreated = onView(withId(R.id.create_data_text_view));
    public ViewInteraction timeCreated = onView(withId(R.id.create_time_text_view));
    public ViewInteraction statusIcon = onView(withId(R.id.status_icon_image_view));
    public ViewInteraction statusChangingComment = onView(withId(R.id.editText));


    public ViewInteraction claimList(int index) {
        return onView(withIndex(withId(R.id.claim_list_card), index));
    }

    public ViewInteraction comment(String text) {
        return onView(withText(text));
    }

    public ViewInteraction takeToWorkClaim = onView(withText("take to work"));
    public ViewInteraction cancelClaim = onView(withText("Cancel"));
    public ViewInteraction throwOffClaim = onView(withText("Throw off"));
    public ViewInteraction toExecuteClaim = onView(withText("To execute"));

    public ViewInteraction addCommentBtn = onView(withId(R.id.add_comment_image_button));
    public ViewInteraction editClaimBtn = onView(withId(R.id.edit_processing_image_button));
    public ViewInteraction changeStatusBtn = onView(withId(R.id.status_processing_image_button));
    public ViewInteraction returnBtn = onView(withId(R.id.close_image_button));

    public ViewInteraction commentDescription(int position) {
        return onView(withIndex(withId(R.id.comment_description_text_view), position));
    }

    public ViewInteraction editComment(int index) {
        return onView(allOf(withIndex(withId(R.id.edit_comment_image_button), index),
                withContentDescription("button edit comment")));
    }

}