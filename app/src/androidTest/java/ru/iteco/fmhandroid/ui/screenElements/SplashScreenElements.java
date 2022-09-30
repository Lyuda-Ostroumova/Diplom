package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

public class SplashScreenElements {
    public ViewInteraction splashScreenImage = onView(withId(R.id.splashscreen_image_view));
    public ViewInteraction splashScreenLoadingIndicator = onView(withId(R.id.splash_screen_circular_progress_indicator));
    public ViewInteraction splashScreenQuote = onView(withId(R.id.splashscreen_text_view));
}
