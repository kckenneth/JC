package edu.gatech.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompareJobs extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CHECKED_IDS = "edu.gatech.seclass.jobcompare.MESSAGE_CHECKED_IDS";

    private Button mm;
    private Button cj;

    private ArrayList<String> listItems;
    private ArrayList<String> idItems;
    private ArrayAdapter adapter;

    private ArrayList sourceIDs;

    private ListView offerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_jobs);

        //Update Job Offer database based on rankings calculation
        updateJobOffersDB();

        offerList = (ListView) findViewById(R.id.offerList);

        //Reading the updated Job Offer database
        SQLiteDatabase database = new JobOffersDBSQLiteHelper(this).getReadableDatabase();

        String[] projection = {JobOffersDB.JobDetails.COLUMN_TITLE, JobOffersDB.JobDetails.COLUMN_COMPANY, JobOffersDB.JobDetails.COLUMN_STATUS, JobOffersDB.JobDetails._ID};
        String sortOrder = JobOffersDB.JobDetails.COLUMN_RANKINGS + " DESC";

        Cursor cursor = database.query(JobOffersDB.JobDetails.TABLE_NAME, projection, null, null, null, null, sortOrder);

        listItems = new ArrayList<>();
        idItems = new ArrayList<>();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No job to show", Toast.LENGTH_SHORT).show();
        } else {
            int i = 1;
            while(cursor.moveToNext()){
                String cj_rank = String.valueOf(i);
                String cj_status = cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_STATUS));
                String cj_title = cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_TITLE));
                String cj_com = cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_COMPANY));
                String offer = cj_rank + " : " + cj_title + ", " + cj_com + " ( " + cj_status + " )";
                listItems.add(offer);

                String rowid = cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails._ID));
                idItems.add(rowid);
                i++;
            }

            //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, listItems);
            offerList.setAdapter(adapter);
            offerList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        }

    }

    public void updateJobOffersDB() {

        String wys = new String();
        String wsb = new String();
        String wyb = new String();
        String wrb = new String();
        String wlt = new String();

        SQLiteDatabase wdb = new WeightsDBSQLiteHelper(this).getReadableDatabase();
        String[] projection = {WeightsDB.WeightsDetails.COLUMN_YS, WeightsDB.WeightsDetails.COLUMN_SB, WeightsDB.WeightsDetails.COLUMN_YB, WeightsDB.WeightsDetails.COLUMN_RB, WeightsDB.WeightsDetails.COLUMN_LT};
        Cursor cursor = wdb.query(WeightsDB.WeightsDetails.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            wys = cursor.getString(cursor.getColumnIndexOrThrow(WeightsDB.WeightsDetails.COLUMN_YS));
            wsb = cursor.getString(cursor.getColumnIndexOrThrow(WeightsDB.WeightsDetails.COLUMN_SB));
            wyb = cursor.getString(cursor.getColumnIndexOrThrow(WeightsDB.WeightsDetails.COLUMN_YB));
            wrb = cursor.getString(cursor.getColumnIndexOrThrow(WeightsDB.WeightsDetails.COLUMN_RB));
            wlt = cursor.getString(cursor.getColumnIndexOrThrow(WeightsDB.WeightsDetails.COLUMN_LT));
        }
        cursor.close();

        float wysn = Float.parseFloat(wys);
        float wsbn = Float.parseFloat(wsb);
        float wybn = Float.parseFloat(wyb);
        float wrbn = Float.parseFloat(wrb);
        float wltn = Float.parseFloat(wlt);

        //total weight
        float tw = wysn + wsbn + wybn + wrbn + wltn;

        SQLiteDatabase database = new JobOffersDBSQLiteHelper(this).getWritableDatabase();

        String update = "UPDATE " + JobOffersDB.JobDetails.TABLE_NAME +
                " SET " + JobOffersDB.JobDetails.COLUMN_RANKINGS + " = " +
                wysn/tw + " * CAST(AdjustedYearlySalary AS FLOAT) + " +
                wsbn/tw + " * CAST(AdjustedSigningBonus AS FLOAT) + " +
                wybn/tw + " * CAST(AdjustedYearlyBonus AS FLOAT) + " +
                wrbn/tw + " * CAST(RetirementBenefits AS FLOAT) * CAST(AdjustedYearlySalary AS FLOAT) + " +
                wltn/(tw * 260.0) + " * CAST(LeaveTime AS FLOAT) * CAST(AdjustedYearlySalary AS FLOAT)";

        database.execSQL(update);

        long totalRow = DatabaseUtils.queryNumEntries(database, JobOffersDB.JobDetails.TABLE_NAME);
        Toast.makeText(this, "Total row updated : " + totalRow, Toast.LENGTH_SHORT).show();

    }

    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                //flag to set not another instance
        startActivity(intent);
    }

    public void twoJobs(View view){

        //cj = (Button) findViewById(R.id.CJ_bt);

        if (offerList.getCheckedItemCount() != 2){
            //cj.setError("Please select two jobs");
            Toast.makeText(this, "Please select two jobs", Toast.LENGTH_SHORT).show();
        } else {

            SparseBooleanArray checkedId = offerList.getCheckedItemPositions();

            sourceIDs = new ArrayList<>();

            for (int i = 0; i < offerList.getCount(); i++) {
                if (checkedId.get(i)) {
                    sourceIDs.add(idItems.get(i));
                }
            }
            Toast.makeText(this, "selected ids : " + checkedId, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "sourceID: " + sourceIDs, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, TwoJobs.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                //flag to set not another instance

            intent.putExtra(CHECKED_IDS, sourceIDs);

            startActivity(intent);

        }
    }



}