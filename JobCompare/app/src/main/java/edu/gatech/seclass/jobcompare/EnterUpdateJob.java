package edu.gatech.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EnterUpdateJob extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_CURRENT = "edu.gatech.seclass.jobcompare.MESSAGE_CURRENT";

    private Button enterBtn;
    private Button updateBtn;
    private Button mmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_update_job);
    }

    public void EnterJob(View view){

        SQLiteDatabase database = new JobOffersDBSQLiteHelper(this).getReadableDatabase();

        String[] projection = {JobOffersDB.JobDetails._ID};
        String selection = JobOffersDB.JobDetails.COLUMN_STATUS + " = ?";
        String[] selectionArgs = { "current job" };

        Cursor cursor = database.query(JobOffersDB.JobDetails.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (cursor.getCount() == 1) {
            Toast.makeText(this, "Current job exists.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, CurrentJobDetails.class);
            String msg_cur = "currentJob";
            intent.putExtra(EXTRA_MESSAGE_CURRENT, msg_cur);
            startActivity(intent);
        }

    }

    public void UpdateJob(View view){

        SQLiteDatabase database = new JobOffersDBSQLiteHelper(this).getReadableDatabase();

        String[] projection = {JobOffersDB.JobDetails._ID};
        String selection = JobOffersDB.JobDetails.COLUMN_STATUS + " = ?";
        String[] selectionArgs = { "current job" };

        Cursor cursor = database.query(JobOffersDB.JobDetails.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No current job to update", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, UpdateJob.class);
            startActivity(intent);
        }

    }

    //called when the "Exit" button is clicked
    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                //flag to set not another instance
        startActivity(intent);
    }
}