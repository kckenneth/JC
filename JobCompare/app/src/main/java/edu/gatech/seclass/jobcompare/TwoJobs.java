package edu.gatech.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TwoJobs extends AppCompatActivity {

    private Button mm_bt;
    private Button caj_bt;

    private ListView ListView_Job1, ListView_Job2;

    private ArrayList<String> jobList1, jobList2;

    private ArrayAdapter adapter1, adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_jobs);

        ListView_Job1 = (ListView) findViewById(R.id.ListView_Job1);
        ListView_Job2 = (ListView) findViewById(R.id.ListView_Job2);

        Intent intent = getIntent();
        ArrayList sourceIDs = intent.getStringArrayListExtra(CompareJobs.CHECKED_IDS);

        jobList1 = jobArray(sourceIDs.get(0).toString());
        jobList2 = jobArray(sourceIDs.get(1).toString());

        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobList1);
        adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobList2);
        ListView_Job1.setAdapter(adapter1);
        ListView_Job2.setAdapter(adapter2);

    }

    public ArrayList<String> jobArray(String idx){

        SQLiteDatabase database = new JobOffersDBSQLiteHelper(this).getReadableDatabase();

        String[] projection = {
                JobOffersDB.JobDetails.COLUMN_TITLE,
                JobOffersDB.JobDetails.COLUMN_COMPANY,
                JobOffersDB.JobDetails.COLUMN_LOCATION,
                JobOffersDB.JobDetails.COLUMN_AYS,
                JobOffersDB.JobDetails.COLUMN_ASB,
                JobOffersDB.JobDetails.COLUMN_AYB,
                JobOffersDB.JobDetails.COLUMN_RB,
                JobOffersDB.JobDetails.COLUMN_LT
        };

        String selection = JobOffersDB.JobDetails._ID + " = ?";

        String[] selectionArgs = { idx };

        Cursor cursor = database.query(
                JobOffersDB.JobDetails.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        ArrayList<String> jobList = new ArrayList<>();

        while (cursor.moveToNext()) {
            jobList.add(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_TITLE)));
            jobList.add(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_COMPANY)));
            jobList.add(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_LOCATION)));
            jobList.add("$ " + cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_AYS)));
            jobList.add("$ " + cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_ASB)));
            jobList.add("$ " + cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_AYB)));
            jobList.add(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_RB)) + "%");
            jobList.add(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_LT)) + " days");
        }

        return jobList;
    }


    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                //flag to set not another instance
        startActivity(intent);
    }

    public void CompareAnotherJob(View view){
        Intent intent = new Intent(this, CompareJobs.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                //flag to set not another instance
        startActivity(intent);
    }

}