package edu.gatech.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.BaseColumns;

public final class JobOffersDB {
    private JobOffersDB() {
    }

    public static class JobDetails implements BaseColumns {
        public static final String TABLE_NAME = "Jobs";
        public static final String COLUMN_STATUS = "Status";
        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_COMPANY = "Company";
        public static final String COLUMN_LOCATION = "Location";
        public static final String COLUMN_COL = "CostOfLiving";
        public static final String COLUMN_YS = "YearlySalary";
        public static final String COLUMN_SB = "SigningBonus";
        public static final String COLUMN_YB = "YearlyBonus";
        public static final String COLUMN_RB = "RetirementBenefits";
        public static final String COLUMN_LT = "LeaveTime";
        public static final String COLUMN_AYS = "AdjustedYearlySalary";
        public static final String COLUMN_ASB = "AdjustedSigningBonus";
        public static final String COLUMN_AYB = "AdjustedYearlyBonus";
        public static final String COLUMN_RANKINGS = "Rankings";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STATUS + " TEXT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_COMPANY + " TEXT," +
                COLUMN_LOCATION + " TEXT," +
                COLUMN_COL + " INTEGER," +
                COLUMN_YS + " INTEGER," +
                COLUMN_SB + " INTEGER," +
                COLUMN_YB + " INTEGER," +
                COLUMN_RB + " REAL," +
                COLUMN_LT + " INTEGER," +
                COLUMN_AYS + " REAL," +
                COLUMN_ASB + " REAL," +
                COLUMN_AYB + " REAL," +
                COLUMN_RANKINGS + " REAL" + ")";

    }
}