package ru.iteco.fmhandroid.ui.data;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


import android.os.IBinder;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.Root;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import ru.iteco.fmhandroid.R;


import io.bloco.faker.Faker;

public class Helper {
    public Helper() {
    }

    public static class AuthInfo {
        private final String login;
        private final String pass;

        public AuthInfo(String login, String pass) {
            this.login = login;
            this.pass = pass;
        }

        public String getLogin() {
            return login;
        }

        public String getPass() {
            return pass;
        }
    }

    public static AuthInfo authInfo() {
        String login = "login2";
        String pass = "password2";
        return new AuthInfo(login, pass);
    }

    public static AuthInfo invalidAuthData() {
        Faker faker = new Faker();
        String invalidLogin = faker.name.firstName() + faker.name.lastName();
        String invalidPass = faker.internet.password();
        String login = invalidLogin;
        String pass = invalidPass;
        return new AuthInfo(login, pass);
    }

    public static AuthInfo invalidLoginData() {
        Faker faker = new Faker();
        String invalidLogin = faker.name.firstName() + faker.name.lastName();
        String pass = "password2";
        String login = invalidLogin;
        return new AuthInfo(login, pass);
    }

    public static AuthInfo invalidPassData() {
        Faker faker = new Faker();
        String invalidPass = faker.internet.password();
        String pass = invalidPass;
        String login = "login2";
        return new AuthInfo(login, pass);
    }

    public static AuthInfo emptyLogin() {
        String pass = "password2";
        String login = "";
        return new AuthInfo(login, pass);
    }

    public static AuthInfo emptyPassword() {
        String pass = "";
        String login = "login2";
        return new AuthInfo(login, pass);
    }

    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static Matcher<View> childAtPosition(Matcher<View> matcher, final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    public static ViewAction nestedScrollTo() {
        return new ViewAction() {

            @Override
            public Matcher<View> getConstraints() {
                return Matchers.allOf(
                        isDescendantOfA(isAssignableFrom(NestedScrollView.class)),
                        withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE));
            }

            @Override
            public String getDescription() {
                return "View is not NestedScrollView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                try {
                    NestedScrollView nestedScrollView = (NestedScrollView)
                            findFirstParentLayoutOfClass(view);
                    if (nestedScrollView != null) {
                        nestedScrollView.scrollTo(0, view.getTop());
                    } else {
                        throw new Exception("Unable to find NestedScrollView parent.");
                    }
                } catch (Exception e) {
                    throw new PerformException.Builder()
                            .withActionDescription(this.getDescription())
                            .withViewDescription(HumanReadables.describe(view))
                            .withCause(e)
                            .build();
                }
                uiController.loopMainThreadUntilIdle();
            }

        };
    }

    public static class TextHelpers {
        public static String getText(ViewInteraction matcher) {
            final String[] text = new String[1];
            ViewAction va = new ViewAction() {

                @Override
                public Matcher<View> getConstraints() {
                    return isAssignableFrom(TextView.class);
                }

                @Override
                public String getDescription() {
                    return "Text of the view";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    TextView tv = (TextView) view;
                    text[0] = tv.getText().toString();
                }
            };

            matcher.perform(va);

            return text[0];
        }
    }

    private static View findFirstParentLayoutOfClass(View view) {
        ViewParent parent = new FrameLayout(view.getContext());
        ViewParent incrementView = null;
        int i = 0;
        while (parent != null && !(parent.getClass() == NestedScrollView.class)) {
            if (i == 0) {
                parent = findParent(view);
            } else {
                parent = findParent(incrementView);
            }
            incrementView = parent;
            i++;
        }
        return (View) parent;
    }

    private static ViewParent findParent(View view) {
        return view.getParent();
    }

    private static ViewParent findParent(ViewParent view) {
        return view.getParent();
    }

    public static class Rand {

        static final Random rand = new Random();

        @SafeVarargs
        public static int randomClaims(@NonNull int... items) {
            return items[rand.nextInt(items.length)];
        }

        @SafeVarargs
        public static int randomNews(@NonNull int... items) {
            return items[rand.nextInt(items.length)];
        }

        @SafeVarargs
        public static int random(@NonNull int... items) {
            return items[rand.nextInt(items.length)];
        }

        public static String randomExecutor() {
            String[] executor = {
                    "Смирнов Петр Петрович",
                    "Иванов Данил Данилович",
                    "Петров Егор Егорович",
                    "Сидоров Дмитрий Дмитреевич",
                    "Тестов Тест Тестович",
                    "Netology Diplom QAMID",
            };
            return executor[rand.nextInt(executor.length)];
        }

        public static String randomCategory() {
            String[] category = {
                    "Объявление",
                    "День рождения",
                    "Зарплата",
                    "Профсоюз",
                    "Праздник",
                    "Массаж",
                    "Благодарность",
                    "Нужна помощь"
            };
            return category[rand.nextInt(category.length)];
        }

    }

    public static class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class Text {

        public static String getText(ViewInteraction matcher) {
            final String[] text = new String[1];
            ViewAction viewAction = new ViewAction() {

                @Override
                public Matcher<View> getConstraints() {
                    return isAssignableFrom(TextView.class);
                }

                @Override
                public String getDescription() {
                    return "Text of the view";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    TextView textView = (TextView) view;
                    text[0] = textView.getText().toString();
                }
            };

            matcher.perform(viewAction);

            return text[0];
        }

    }

    public static String getCurrentDate() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(currentDate);
    }

    public static String getCurrentTime() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(currentDate);
    }

    public static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }

    public static ViewAction waitForElement(final Matcher matcher, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with attribute <" + matcher + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = matcher;

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        try { // found view with required ID
                            if (viewMatcher.matches(child)) {
                                return;
                            }
                        } catch (NoMatchingViewException e) {
                            // ignore
                        }

                        uiController.loopMainThreadForAtLeast(50);
                    }

                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };

    }

    public static void elementWaiting(Matcher matcher, int millis) {
        onView(isRoot()).perform(waitForElement(matcher, millis));
    }

    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    public static boolean isDisplayedWithSwipe(ViewInteraction locator, int recycler,
                                               boolean finishSwipe) {
        try {
            locator.check(matches(isDisplayed()));
            return true;
        } catch (NoMatchingViewException ignored) {
        }
        boolean invis = true;
        int n = 1;
        while (invis) {
            try {
                if (recycler == 1) {
                    onView(allOf(withId(R.id.news_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                } else {
                    onView(allOf(withId(R.id.claim_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                }
            } catch (PerformException e) {
                return false;
            }
            try {
                locator.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));//.check(matches(isDisplayed()));
                invis = false;
            } catch (NoMatchingViewException e) {
                invis = true;
            }
            n++;
            if (!invis & finishSwipe) {
                try {
                    if (recycler == 1) {
                        onView(allOf(withId(R.id.news_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                    } else {
                        onView(allOf(withId(R.id.claim_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                    }
                } catch (PerformException e) {
                    return false;
                }
            }
            if (n > 400) {
                return false;
            }
            SystemClock.sleep(2000);
        }
        return true;
    }

    public static boolean fastDown(ViewInteraction locator, int recycler, boolean finishSwipe) {
        try {
            locator.check(matches(isDisplayed()));
            return true;
        } catch (NoMatchingViewException ignored) {
        }
        boolean invis = true;
        int n = 1;
        while (invis) {
            try {
                if (recycler == 1) {
                    onView(allOf(withId(R.id.news_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                } else {
                    onView(allOf(withId(R.id.claim_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                }
            } catch (PerformException e) {
                return false;
            }
            try {
                locator.check(matches(isDisplayed()));
                invis = false;
            } catch (NoMatchingViewException e) {
                invis = true;
            }
            n++;
            if (!invis & finishSwipe) {
                try {
                    if (recycler == 1) {
                        onView(allOf(withId(R.id.news_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                    } else {
                        onView(allOf(withId(R.id.claim_list_recycler_view), isDisplayed())).perform(actionOnItemAtPosition(n, swipeUp()));
                    }
                } catch (PerformException e) {
                    return false;
                }
            }
            if (n > 400) {
                return false;
            }
            SystemClock.sleep(2000);
        }
        return true;
    }

}

