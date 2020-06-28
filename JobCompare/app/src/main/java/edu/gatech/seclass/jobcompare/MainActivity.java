package edu.gatech.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_OFFER = "edu.gatech.seclass.jobcompare.MESSAGE_OFFER";

    private Button cjo_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the "Current job details" button */
    public void jobDetails(View view) {
        Intent intent = new Intent(this, EnterUpdateJob.class);
        startActivity(intent);
    }

    /** Called when the user taps the "Job offers" button */
    public void jobOffers(View view) {
        Intent intent = new Intent(this, CurrentJobDetails.class);
        String msg_offer = "jobOffer";
        intent.putExtra(EXTRA_MESSAGE_OFFER, msg_offer);
        startActivity(intent);
    }

    /** Called when the user taps the "Comparison settings" button */
    public void comaprisonSettings(View view) {
        createWeightsTable();
        Intent intent = new Intent(this, ComparisonSettings.class);
        startActivity(intent);
    }


    public void createWeightsTable() {

        SQLiteDatabase wdb = new WeightsDBSQLiteHelper(this).getReadableDatabase();

        String[] projection = {WeightsDB.WeightsDetails._ID};

        Cursor cursor = wdb.query(
                WeightsDB.WeightsDetails.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.getCount() == 0) {

            SQLiteDatabase weights_DB = new WeightsDBSQLiteHelper(this).getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(WeightsDB.WeightsDetails.COLUMN_YS, 1.0);
            values.put(WeightsDB.WeightsDetails.COLUMN_SB, 1.0);
            values.put(WeightsDB.WeightsDetails.COLUMN_YB, 1.0);
            values.put(WeightsDB.WeightsDetails.COLUMN_RB, 1.0);
            values.put(WeightsDB.WeightsDetails.COLUMN_LT, 1.0);

            long newRowId = weights_DB.insert(WeightsDB.WeightsDetails.TABLE_NAME, null, values);

            Toast.makeText(this, "Weights Table created. The new Row ID is " + newRowId, Toast.LENGTH_LONG).show();
        }

    }


        /** Called when the user taps the "Compare job offers" button */
    public void compareJobOffers(View view) {

        cjo_bt = (Button) findViewById(R.id.cjo_bt);

        SQLiteDatabase database = new JobOffersDBSQLiteHelper(this).getReadableDatabase();

        String[] projection = {JobOffersDB.JobDetails.COLUMN_STATUS};
        String selection = JobOffersDB.JobDetails.COLUMN_STATUS + " = ?";
        String[] selectionArgs = { "offer" };

        Cursor cursor = database.query(
                JobOffersDB.JobDetails.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount() == 0) {
            //cjo_bt.setError("No job offer to compare");
            Toast.makeText(this, "No job offer to compare", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, CompareJobs.class);
            startActivity(intent);
        }
    }
}