package edu.gatech.seclass.jobcompare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.test.AndroidTestCase;

import edu.gatech.seclass.jobcompare.JobOffersDB;
import edu.gatech.seclass.jobcompare.JobOffersDB.JobDetails;
import edu.gatech.seclass.jobcompare.JobOffersDBSQLiteHelper;


/**
 * This is a Georgia Tech provided code example for use in assigned private GT repositories. Students and other users of this template
 * code are advised not to share it with other students or to make it available on publicly viewable websites including
 * repositories such as github and gitlab.  Such sharing may be investigated as a GT honor code violation. Created for CS6300.
 */


//@RunWith(AndroidJUnit4.class)

@RunWith(AndroidJUnit4.class)
public class TestExamples {

    @Rule
    public ActivityTestRule<MainActivity> tActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void Screenshot1() {
        onView(withId(R.id.jo_bt)).perform(click());
        onView(withId(R.id.EditText_JT)).perform(clearText(), replaceText("Scientist"));
        onView(withId(R.id.EditText_Comp)).perform(clearText(), replaceText("ABC"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.save_bt_cjd)).perform(click());
        onView(withId(R.id.EditText_YS)).check(matches(hasErrorText("Invalid Yearly Salary")));
    }

    @Test
    public void Screenshot2() {
        onView(withId(R.id.jo_bt)).perform(click());
        onView(withId(R.id.EditText_JT)).perform(clearText(), replaceText("Scientist"));
        onView(withId(R.id.EditText_Comp)).perform(clearText(), replaceText("ABC"));
        onView(withId(R.id.Spinner_Loc)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Boston, MA (US)"))).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.save_bt_cjd)).perform(click());
        onView(withId(R.id.EditText_SB)).check(matches(hasErrorText("Invalid Signing Bonus")));
    }

    @Test
    public void Screenshot3() {
        onView(withId(R.id.jo_bt)).perform(click());
        onView(withId(R.id.EditText_JT)).perform(clearText(), replaceText("Engineer"));
        onView(withId(R.id.EditText_Comp)).perform(clearText(), replaceText("XYZ"));
        onView(withId(R.id.Spinner_Loc)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("New York City, NY (US)"))).perform(click());
        onView(withId(R.id.EditText_YS)).perform(clearText(), replaceText("millions"));
        onView(withId(R.id.EditText_SB)).perform(clearText(), replaceText("1000"));
        onView(withId(R.id.EditText_YB)).perform(clearText(), replaceText("1000"));
        onView(withId(R.id.EditText_RB)).perform(clearText(), replaceText("3.2"));
        onView(withId(R.id.EditText_LT)).perform(clearText(), replaceText("21"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.save_bt_cjd)).perform(click());
        onView(withId(R.id.EditText_YS)).check(matches(hasErrorText("Invalid Yearly Salary")));
    }

    @Test
    public void Screenshot4() {
        onView(withId(R.id.jo_bt)).perform(click());
        onView(withId(R.id.EditText_JT)).perform(clearText(), replaceText("Engineer"));
        onView(withId(R.id.EditText_Comp)).perform(clearText(), replaceText("XYZ"));
        onView(withId(R.id.Spinner_Loc)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("New York City, NY (US)"))).perform(click());
        onView(withId(R.id.EditText_YS)).perform(clearText(), replaceText("2000"));
        onView(withId(R.id.EditText_SB)).perform(clearText(), replaceText("1000"));
        onView(withId(R.id.EditText_YB)).perform(clearText(), replaceText("1000"));
        onView(withId(R.id.EditText_RB)).perform(clearText(), replaceText("3.2"));
        onView(withId(R.id.EditText_LT)).perform(clearText(), replaceText("21"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.save_bt_cjd)).perform(click());

        SQLiteDatabase db = new JobOffersDBSQLiteHelper(this).getReadableDatabase();
        assertTrue(db.isOpen());
        //db.query(JobOffersDB.JobDetails.TABLE_NAME, String[] JobOffersDB.JobDetails.COLUMN_TITLE, null, null, null, null);

    }



    /***

    @Test
    public void Screenshot3() {
        onView(withId(R.id.cjd_bt)).perform(click());
        onView(withId(R.id.Enter_btn)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.save_bt_cjd)).perform(click());
        onView(withId(R.id.EditText_SB)).check(matches(hasErrorText("Invalid Signing Bonus")));
    }


    @RunWith(AndroidJUnit4.class)
    public class SimpleEntityReadWriteTest {
        private SQLiteDatabase userDao;
        private JobOffersDB db;

        @Before
        public void createDb() {
            Context context = ApplicationProvider.getApplicationContext();
            db = Room.inMemoryDatabaseBuilder(context, JobOffersDB.class).build();
            userDao = db.getUserDao();
        }

        @After
        public void closeDb() throws IOException {
            db.close();
        }

        @Test
        public void writeUserAndReadInList() throws Exception {
            User user = TestUtil.createUser(3);
            user.setName("george");
            userDao.insert(user);
            List<User> byName = userDao.findUsersByName("george");
            assertThat(byName.get(0), equalTo(user));
        }
    }
        */


}
