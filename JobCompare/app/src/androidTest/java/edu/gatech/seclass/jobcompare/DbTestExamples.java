package edu.gatech.seclass.jobcompare;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
/***
@RunWith(AndroidJUnit4.class)
public class DbTestExamples extends  {

    private JobOffersDBSQLiteHelper database;

    @Before
    public void setUp() throws Exception {
        SQLiteDatabase database = new JobOffersDBSQLiteHelper(this).getWritableDatabase();
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void addTitle() throws Exception {

        ContentValues values = new ContentValues();
        values.put(JobOffersDB.JobDetails.COLUMN_STATUS, "current job");
        values.put(JobOffersDB.JobDetails.COLUMN_TITLE, "Painter");
        values.put(JobOffersDB.JobDetails.COLUMN_COMPANY, "Home Depo");
        values.put(JobOffersDB.JobDetails.COLUMN_LOCATION, "Austin, TX (US)");
        values.put(JobOffersDB.JobDetails.COLUMN_COL, "172");
        values.put(JobOffersDB.JobDetails.COLUMN_YS, "1000");
        values.put(JobOffersDB.JobDetails.COLUMN_SB, "1000");
        values.put(JobOffersDB.JobDetails.COLUMN_YB, "1000");
        values.put(JobOffersDB.JobDetails.COLUMN_RB, "3.2");
        values.put(JobOffersDB.JobDetails.COLUMN_LT, "30");

        float ays = (float) (Math.round(1000 * (100/172) * 100.0)/100.0);
        float asb = (float) (Math.round(1000 * (100/172) * 100.0)/100.0);
        float ayb = (float) (Math.round(1000 * (100/172) * 100.0)/100.0);
        values.put(JobOffersDB.JobDetails.COLUMN_AYS, ays);
        values.put(JobOffersDB.JobDetails.COLUMN_ASB, asb);
        values.put(JobOffersDB.JobDetails.COLUMN_AYB, ayb);

        long newRowId = database.insert(JobOffersDB.JobDetails.TABLE_NAME, null, values);
    }
}*/
