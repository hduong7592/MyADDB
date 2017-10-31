package edu.vccs.hduong7592.myaddb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import Classes.GetData;

public class AssignmentDetail extends AppCompatActivity {

    int chapterID, aID, userID, courseID;
    EditText assignmentNameTxt, descriptionTxt, assignToTxt;
    Button saveBtn, cancelBtn;

    String aName, aDes, aTo, action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_detail);

        setTitle("Assignment");

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception ex){
            //Toast.makeText(ViewClasses.this,ex.toString(), Toast.LENGTH_SHORT).show();
            showSimpleDialog(ex.toString(), "Error", "");
        }

        Intent myIntent = getIntent();
        userID = 0;
        try{
            userID = Integer.parseInt(myIntent.getStringExtra("userID"));
        }catch (Exception ex){
            userID = 0;
            //Toast.makeText(AssignmentDetail.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        chapterID = 0;
        try{
            chapterID = Integer.parseInt(myIntent.getStringExtra("chapterID"));
        }catch (Exception ex){
            chapterID = 0;
            //Toast.makeText(AssignmentDetail.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        aID = 0;
        try{
            aID = Integer.parseInt(myIntent.getStringExtra("aID"));
        }catch (Exception ex){
            aID = 0;
            //Toast.makeText(AssignmentDetail.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        courseID = 0;
        try{
            courseID = Integer.parseInt(myIntent.getStringExtra("courseID"));
        }catch (Exception ex){
            courseID = 0;
            //Toast.makeText(AssignmentDetail.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        assignmentNameTxt = (EditText) findViewById(R.id.assignmentNameTxt);
        descriptionTxt = (EditText) findViewById(R.id.descriptionTxt);
        assignToTxt = (EditText) findViewById(R.id.assignToTxt);
        saveBtn = (Button) findViewById(R.id.SaveBtn);
        cancelBtn = (Button) findViewById(R.id.CancelBtn);


            if (aID != 0) {
                //new SoapCall().execute();
                GetAssignmentDetail(aID);
            }


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aName = assignmentNameTxt.getText().toString();
                aDes = descriptionTxt.getText().toString();

                if(aName.equals("")){
                    Toast.makeText(AssignmentDetail.this, "Assignment name is required!", Toast.LENGTH_LONG).show();
                    assignmentNameTxt.requestFocus();
                }
                else{
                    if(aDes.equals("")){
                        Toast.makeText(AssignmentDetail.this, "Description is required!", Toast.LENGTH_LONG).show();
                        descriptionTxt.requestFocus();
                    }
                    else{
                        //new SoapCall().execute();
                        if(aID != 0){
                            UpdateAssignment();
                        }
                        else{
                            AddAssignment();
                        }
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AssignmentDetail.this, ViewChapter.class);
                myIntent.putExtra("userID",Integer.toString(userID));
                myIntent.putExtra("chapterID",Integer.toString(chapterID));
                myIntent.putExtra("courseID",Integer.toString(courseID));
                startActivity(myIntent);
            }
        });

    }

    private void AddAssignment() {
        action = "Addnew";
        new SoapCall().execute();
    }

    private void UpdateAssignment() {
        action = "Update";
        new SoapCall().execute();
    }

    private void GetAssignmentDetail(int aID) {
        action = "GetData";
        new SoapCall().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class SoapCall extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String message = "";
            if(result != null) {

                if (result.equals("Success")) {
                    if(action.equals("Update")){
                        message = "Update successful!";
                    }
                    else{
                        message = "Assignment added!";
                    }

                    showSimpleDialog(message,"","Success");

                } else if (result == "Error") {
                    Toast.makeText(AssignmentDetail.this, "Error!", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {

                        JSONObject jsonObj = new JSONObject(result);

                        aName = jsonObj.getString("aName");
                        aDes = jsonObj.getString("aDescription");
                        aTo = jsonObj.getString("assignedTo");

                        assignmentNameTxt.setText(aName);
                        descriptionTxt.setText(aDes);
                        assignToTxt.setText(aTo);
                        //Toast.makeText(ViewUsers.this,rowUsers.toString(), Toast.LENGTH_SHORT).show();


                    } catch (final JSONException e) {

                        showSimpleDialog(e.toString(), "Error", "");
                        Toast.makeText(AssignmentDetail.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else{
                Toast.makeText(AssignmentDetail.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            String response ="";
            GetData data = new GetData();
            if(aID != 0){
                if(action.equals("Update")){
                    response = data.UpdateAssignment(aName, aDes, userID, aID);
                }
                else{
                response = data.GetAssignmentDetail(aID);
                }
            }
            else{
                response = data.AddAssignment(aName, aDes, chapterID, userID);
            }

            return response;
        }
    }

    /**
     * Alert box
     * @param Message
     */

    public void showSimpleDialog(String Message, String Title, final String Result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AssignmentDetail.this);
        builder.setCancelable(false);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Result.equals("Success")){
                    Intent myIntent = new Intent(AssignmentDetail.this, ViewChapter.class);
                    myIntent.putExtra("userID",Integer.toString(userID));
                    myIntent.putExtra("chapterID",Integer.toString(chapterID));
                    myIntent.putExtra("courseID",Integer.toString(courseID));
                    startActivity(myIntent);
                }
            }
        });
        builder.create().show();
    }
}
