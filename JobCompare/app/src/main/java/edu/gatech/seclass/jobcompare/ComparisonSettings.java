package edu.gatech.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ComparisonSettings extends AppCompatActivity {

    private EditText wys;
    private EditText wsb;
    private EditText wyb;
    private EditText wrb;
    private EditText wlt;

    private Button save_bt_cs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_settings);

        wys = (EditText) findViewById(R.id.EditText_wys);
        wsb = (EditText) findViewById(R.id.EditText_wsb);
        wyb = (EditText) findViewById(R.id.EditText_wyb);
        wrb = (EditText) findViewById(R.id.EditText_wrb);
        wlt = (EditText) findViewById(R.id.EditText_wlt);

        save_bt_cs = (Button) findViewById(R.id.save_bt_cs);

        save_bt_cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWeights();
            }
        });

    }

    public boolean checkWYS() {
        String wysv = wys.getText().toString();
        if (wysv.trim().length() == 0){
            return true;
        }
        return false;
    }

    public boolean checkWSB() {
        String wsbv = wsb.getText().toString();
        if (wsbv.trim().length() == 0){
            return true;
        }
        return false;
    }

    public boolean checkWYB() {
        String wybv = wyb.getText().toString();
        if (wybv.trim().length() == 0){
            return true;
        }
        return false;
    }

    public boolean checkWRB() {
        String wrbv = wrb.getText().toString();
        if (wrbv.trim().length() == 0){
            return true;
        }
        return false;
    }

    public boolean checkWLT() {
        String wltv = wlt.getText().toString();
        if (wltv.trim().length() == 0){
            return true;
        }
        return false;
    }

    public void checkAllWeights(){
        if (checkWYS()) wys.setError("Invalid weight");
        if (checkWSB()) wsb.setError("Invalid weight");
        if (checkWYB()) wyb.setError("Invalid weight");
        if (checkWRB()) wrb.setError("Invalid weight");
        if (checkWLT()) wlt.setError("Invalid weight");
    }

    public void saveWeights() {

        if (checkWYS()) {
            wys.setError("Invalid weight");
            checkAllWeights();
            return;
        }

        if (checkWSB()) {
            wsb.setError("Invalid weight");
            checkAllWeights();
            return;
        }

        if (checkWYB()) {
            wyb.setError("Invalid weight");
            checkAllWeights();
            return;
        }

        if (checkWRB()) {
            wrb.setError("Invalid weight");
            checkAllWeights();
            return;
        }

        if (checkWLT()) {
            wlt.setError("Invalid weight");
            checkAllWeights();
            return;
        }

        SQLiteDatabase weights_DB = new WeightsDBSQLiteHelper(this).getWritableDatabase();

        String ys = wys.getText().toString();
        String sb = wsb.getText().toString();
        String yb = wyb.getText().toString();
        String rb = wrb.getText().toString();
        String lt = wlt.getText().toString();

        float ysn = Float.parseFloat(ys);
        float sbn = Float.parseFloat(sb);
        float ybn = Float.parseFloat(yb);
        float rbn = Float.parseFloat(rb);
        float ltn = Float.parseFloat(lt);

        String update = "UPDATE " + WeightsDB.WeightsDetails.TABLE_NAME + " SET " +
                WeightsDB.WeightsDetails.COLUMN_YS + " = " + ysn + ", " +
                WeightsDB.WeightsDetails.COLUMN_SB + " = " + sbn + ", " +
                WeightsDB.WeightsDetails.COLUMN_YB + " = " + ybn + ", " +
                WeightsDB.WeightsDetails.COLUMN_RB + " = " + rbn + ", " +
                WeightsDB.WeightsDetails.COLUMN_LT + " = " + ltn + " WHERE " +
                WeightsDB.WeightsDetails._ID + " = 1";

        weights_DB.execSQL(update);

        long totalRow = DatabaseUtils.queryNumEntries(weights_DB, WeightsDB.WeightsDetails.TABLE_NAME);
        Toast.makeText(this, "Total row updated : " + totalRow, Toast.LENGTH_SHORT).show();

    }

    //called when "Exit" button is clicked
    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                //flag to set not another instance
        startActivity(intent);
    }
}