package edu.gatech.seclass.jobcompare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateJob extends AppCompatActivity {

    private EditText jobtitle;
    private EditText company;
    private Spinner location;
    private TextView costOfLiving;
    private EditText yearlySalary;
    private EditText signingBonus;
    private EditText yearlyBonus;
    private EditText rBenefits;
    private EditText ltime;
    private Button update_bt_cjd;

    final String[] locations = new String[]{
            "",
            "Albuquerque, NM (US)", "Anchorage, AK (US)", "Atlanta, GA (US)", "Austin, TX (US)",
            "Baltimore, MD (US)", "Boise, ID (US)", "Boston, MA (US)", "Burlington, VA (US)",
            "Charleston, SC (US)", "Charlotte, NC (US)", "Chicago, IL (US)", "Cincinnati, OH (US)", "Cleveland, OH (US)", "Colorado Springs, CO (US)",
            "Dallas, TX (US)", "Denver, CO (US)", "Detroit, MI (US)",
            "Fort Worth, TX (US)",
            "Honolulu, Hawaii (US)", "Houston, TX (US)",
            "Indianapolis, IN (US)",
            "Jacksonville, FL (US)", "Jersey City, NJ (US)",
            "Kansas City, MI (US)",
            "Las Vegas, NV (US)", "Los Angeles, CA (US)", "Louisville, KY (US)",
            "Memphis, TN (US)", "Miami, FL (US)", "Milwaukee, WI (US)", "Minneapolis - St.Paul, MN (US)", "Mountain View, CA (US)",
            "Nashville, TN (US)", "New Orleans, LA (US)", "New York City, NY (US)",
            "Oakland, CA (US)", "Omaha, NB (US)", "Orlando, FL (US)",
            "Philadelphia, PA (US)", "Phoenix, AZ (US)", "Pittsburgh, PA (US)", "Portland, OR (US)", "Providence, RI (US)",
            "Raleigh, NC (US)", "Riverside, CA (US)",
            "Sacramento, CA (US)", "Salt Lake City, UT (US)", "San Antonio, TX (US)", "San Diego, CA (US)", "San Francisco, CA (US)", "San Jose, CA (US)", "Seattle, WA (US)", "Spokane, WA (US)", "St. Louis, MI (US)",
            "Tampa, FL (US)", "Tucson, AZ (US)",
            "Washington DC (US)",
            "Calgary (Canada)", "Edmonton (Canada)", "Halifax (Canada)", "Kelowna (Canada)", "Montreal (Canada)", "Ottawa (Canada)", "Quebec City (Canada)", "Toronto (Canada)", "Vancouver (Canada)", "Victoria (Canada)", "Winnipeg (Canada)", "Hamilton (Bermuda)"};

    final String[] location_values = new String[]{
            "100",
            "134", "186", "163", "172",
            "168", "143", "215", "167",
            "164", "169", "200", "140", "156", "156",
            "161", "196", "151",
            "168",
            "206", "153",
            "147",
            "147", "236",
            "145",
            "160", "211", "143",
            "131", "202", "150", "171", "264",
            "156", "157", "260",
            "223", "143", "169",
            "180", "167", "171", "190", "162",
            "154", "157",
            "188", "169", "145", "189", "252", "203", "205", "152", "152",
            "162", "145",
            "226",
            "150", "145", "139", "144", "139", "156", "129", "177", "171", "156", "139", "299"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_job);

        jobtitle = (EditText) findViewById(R.id.EditText_JT);
        company = (EditText) findViewById(R.id.EditText_Comp);
        location = (Spinner) findViewById(R.id.Spinner_Loc);
        costOfLiving = (TextView) findViewById(R.id.TextView_COL);
        yearlySalary = (EditText) findViewById(R.id.EditText_YS);
        signingBonus = (EditText) findViewById(R.id.EditText_SB);
        yearlyBonus = (EditText) findViewById(R.id.EditText_YB);
        rBenefits = (EditText) findViewById(R.id.EditText_RB);
        ltime = (EditText) findViewById(R.id.EditText_LT);

        SQLiteDatabase database = new JobOffersDBSQLiteHelper(this).getReadableDatabase();
        String selection = JobOffersDB.JobDetails.COLUMN_STATUS + " = ?";
        String[] selectionArgs = { "current job" };
        Cursor cursor = database.query(JobOffersDB.JobDetails.TABLE_NAME, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            jobtitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_TITLE)));
            company.setText(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_COMPANY)));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, locations);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            location.setAdapter(adapter);

            String loc = cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_LOCATION));
            location.setSelection(((ArrayAdapter)location.getAdapter()).getPosition(loc));


            location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id){
                    costOfLiving.setText(location_values[location.getSelectedItemPosition()]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView){
                    return;
                }
            });

            yearlySalary.setText(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_YS)));
            signingBonus.setText(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_SB)));
            yearlyBonus.setText(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_YB)));
            rBenefits.setText(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_RB)));
            ltime.setText(cursor.getString(cursor.getColumnIndexOrThrow(JobOffersDB.JobDetails.COLUMN_LT)));
        }
        cursor.close();

        update_bt_cjd = (Button) findViewById(R.id.update_bt_cjd);

        update_bt_cjd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateToDB();
            }
        });
    }

    public boolean checkTitle() {
        String jtitle = jobtitle.getText().toString();
        if (jtitle.trim().length() == 0){
            return true;
        }
        return false;
    }

    public boolean checkComp() {
        String jcomp = company.getText().toString();
        if (jcomp.trim().length() == 0){
            return true;
        }
        return false;
    }

    public boolean checkLocation() {
        String jloc = location.getSelectedItem().toString();
        if (jloc.trim().length() == 0){
            return true;
        }
        return false;
    }

    public boolean checkYS() {
        String ys = yearlySalary.getText().toString();
        if ((!TextUtils.isDigitsOnly(ys)) || (ys.trim().length() == 0) || (Integer.parseInt(ys) < 0)){
            return true;
        }
        return false;
    }

    public boolean checkSB() {
        String sb = signingBonus.getText().toString();
        if ((!TextUtils.isDigitsOnly(sb)) || (sb.trim().length() == 0) || (Integer.parseInt(sb) < 0)){
            return true;
        }
        return false;
    }

    public boolean checkYB() {
        String yb = yearlyBonus.getText().toString();
        if ((!TextUtils.isDigitsOnly(yb)) || (yb.trim().length() == 0) || (Integer.parseInt(yb) < 0)){
            return true;
        }
        return false;
    }

    public boolean checkRB() {
        String rb = rBenefits.getText().toString();
        if  ((rb.trim().length() == 0) || (Float.parseFloat(rb) < 0)){
            return true;
        }
        return false;
    }

    public boolean checkLT() {
        String lt = ltime.getText().toString();
        if ((!TextUtils.isDigitsOnly(lt)) || (lt.trim().length() == 0) || (Integer.parseInt(lt) < 0)){
            return true;
        }
        return false;
    }

    public void checkForm(){
        if (checkTitle()) jobtitle.setError("Invalid Title");
        if (checkComp()) company.setError("Invalid Company");
        if (checkLocation()) ((TextView)location.getSelectedView()).setError("Invalid Location");
        if (checkYS()) yearlySalary.setError("Invalid Yearly Salary");
        if (checkSB()) signingBonus.setError("Invalid Signing Bonus");
        if (checkYB()) yearlyBonus.setError("Invalid Yearly Bonus");
        if (checkRB()) rBenefits.setError("Invalid Retirement Benefits");
        if (checkLT()) ltime.setError("Invalid Leave Time");
    }


    public void updateToDB() {

        if (checkTitle()) {
            jobtitle.setError("Invalid Title");
            checkForm();
            return;
        }

        if (checkComp()) {
            company.setError("Invalid Company");
            checkForm();
            return;
        }

        if (checkLocation()) {
            ((TextView)location.getSelectedView()).setError("Invalid Location");
            checkForm();
            return;
        }

        if (checkYS()) {
            yearlySalary.setError("Invalid Yearly Salary");
            checkForm();
            return;
        }

        if (checkSB()) {
            signingBonus.setError("Invalid Signing Bonus");
            checkForm();
            return;
        }

        if (checkYB()) {
            yearlyBonus.setError("Invalid Yearly Bonus");
            checkForm();
            return;
        }

        if (checkRB()) {
            rBenefits.setError("Invalid Retirement Benefits");
            checkForm();
            return;
        }

        if (checkLT()) {
            ltime.setError("Invalid Leave Time");
            checkForm();
            return;
        }

        SQLiteDatabase updatedDB = new JobOffersDBSQLiteHelper(this).getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(JobOffersDB.JobDetails.COLUMN_TITLE, jobtitle.getText().toString());
        values.put(JobOffersDB.JobDetails.COLUMN_COMPANY, company.getText().toString());
        values.put(JobOffersDB.JobDetails.COLUMN_LOCATION, location.getSelectedItem().toString());

        String col = location_values[location.getSelectedItemPosition()];
        float coln = Float.parseFloat(col);
        values.put(JobOffersDB.JobDetails.COLUMN_COL, coln);

        String ys = yearlySalary.getText().toString();
        long ysn = Integer.parseInt(ys);
        values.put(JobOffersDB.JobDetails.COLUMN_YS, ysn);

        String sb = signingBonus.getText().toString();
        long sbn = Integer.parseInt(sb);
        values.put(JobOffersDB.JobDetails.COLUMN_SB, sbn);

        String yb = yearlyBonus.getText().toString();
        long ybn = Integer.parseInt(yb);
        values.put(JobOffersDB.JobDetails.COLUMN_YB, ybn);

        String rb = rBenefits.getText().toString();
        float rbn = (float) (Math.round(Float.parseFloat(rb) * 100.0)/100.0);
        values.put(JobOffersDB.JobDetails.COLUMN_RB, rbn);

        String lt = ltime.getText().toString();
        int ltn = Integer.parseInt(lt);
        values.put(JobOffersDB.JobDetails.COLUMN_LT, ltn);

        float ays = (float) (Math.round(ysn * (100/coln) * 100.0)/100.0);
        float asb = (float) (Math.round(sbn * (100/coln) * 100.0)/100.0);
        float ayb = (float) (Math.round(ybn * (100/coln) * 100.0)/100.0);
        values.put(JobOffersDB.JobDetails.COLUMN_AYS, ays);
        values.put(JobOffersDB.JobDetails.COLUMN_ASB, asb);
        values.put(JobOffersDB.JobDetails.COLUMN_AYB, ayb);

        String selection = JobOffersDB.JobDetails.COLUMN_STATUS + " LIKE ?";
        String[] selectionArgs = { "current job" };

        try {
            updatedDB.update(JobOffersDB.JobDetails.TABLE_NAME, values, selection, selectionArgs);
        } catch (SQLException e){
            Toast.makeText(this, "error in updating", Toast.LENGTH_SHORT).show();
        }

        long totalRow = DatabaseUtils.queryNumEntries(updatedDB, JobOffersDB.JobDetails.TABLE_NAME);

        Toast.makeText(this, "Current job updated", Toast.LENGTH_SHORT).show();

    }



    //called when the "Exit" button is clicked
    public void mainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                //flag to set not another instance
        startActivity(intent);
    }
}