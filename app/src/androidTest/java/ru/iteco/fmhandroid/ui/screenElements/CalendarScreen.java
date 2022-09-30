package ru.iteco.fmhandroid.ui.screenElements;

import androidx.test.espresso.ViewInteraction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import static ru.iteco.fmhandroid.ui.data.Helper.childAtPosition;


public class CalendarScreen {
    public ViewInteraction nextMonthButton = onView(
            allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageButton")), withContentDescription("Next month"),
                    childAtPosition(
                            withClassName(is("android.widget.LinearLayout")), allOf(withClassName(is("android.widget.DayPickerView")),
                                    childAtPosition(
                                            withClassName(is("android.widget.LinearLayout")), withClassName(is("com.android.internal.widget.DialogViewAnimator")),
                                            0)),
                            2)));

    public ViewInteraction previousMonthButton = onView(
            allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageButton")), withContentDescription("Previous month"),
                    childAtPosition(
                            withClassName(is("android.widget.LinearLayout")), allOf(withClassName(is("android.widget.DayPickerView")),
                                    childAtPosition(
                                            withClassName(is("android.widget.LinearLayout")), withClassName(is("com.android.internal.widget.DialogViewAnimator")),
                                            0)),
                            1)));

    public ViewInteraction buttonOfTheYear = onView(
            allOf(withClassName(is("com.google.android.material.textview.MaterialTextView")), withText("2022"),
                    childAtPosition(
                            withClassName(is("android.widget.LinearLayout")), childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")), withClassName(is("android.widget.LinearLayout")),
                                    0),
                            0)));

    public ViewInteraction calendarMonthView = onView(withClassName(is("android.widget.DatePicker")));


}
