package edu.gatech.seclass.jobcompare;

import android.provider.BaseColumns;

public class WeightsDB {

    private WeightsDB() {
    }

    public static class WeightsDetails implements BaseColumns {
        public static final String TABLE_NAME = "Weights";
        public static final String COLUMN_YS = "YearlySalary";
        public static final String COLUMN_SB = "SigningBonus";
        public static final String COLUMN_YB = "YearlyBonus";
        public static final String COLUMN_RB = "RetirementBenefits";
        public static final String COLUMN_LT = "LeaveTime";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_YS + " INTEGER," +
                COLUMN_SB + " INTEGER," +
                COLUMN_YB + " INTEGER," +
                COLUMN_RB + " INTEGER," +
                COLUMN_LT + " INTEGER" + ")";

    }
}
