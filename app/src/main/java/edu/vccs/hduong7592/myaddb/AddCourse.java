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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Classes.GetData;

import static edu.vccs.hduong7592.myaddb.R.id.CourseNameTxt;
import static edu.vccs.hduong7592.myaddb.R.id.assignmentNameTxt;
import static edu.vccs.hduong7592.myaddb.R.id.descriptionTxt;

public class AddCourse extends AppCompatActivity {

    int userID, courseID;

    EditText courseName, courseSession;
    Button submitBtn, cancelBtn, deleteBtn;
    String cname, csession, action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        setTitle("View Classes");

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception ex){
            //Toast.makeText(ViewClasses.this,ex.toString(), Toast.LENGTH_SHORT).show();
            showSimpleDialog(ex.toString(), "Error", "");
        }

        courseName = (EditText) findViewById(R.id.CourseNameTxt);
        courseSession = (EditText) findViewById(R.id.CourseSessionTxt);
        submitBtn = (Button) findViewById(R.id.SubmitBtn);
        cancelBtn = (Button) findViewById(R.id.CancelBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        deleteBtn .setVisibility(View.GONE);

        Intent myIntent = getIntent();
        userID = 0;
        try{
            userID = Integer.parseInt(myIntent.getStringExtra("userID"));
        }catch (Exception ex){
            userID = 0;
            //Toast.makeText(AddCourse.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        courseID = 0;
        try{
            courseID = Integer.parseInt(myIntent.getStringExtra("courseID"));
        }catch (Exception ex){
            courseID = 0;
            //Toast.makeText(AddCourse.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        if(courseID!=0){
            action = "GetData";
            GetCourseData(courseID);
            deleteBtn.setVisibility(View.VISIBLE);
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cname = courseName.getText().toString();
                csession = courseSession.getText().toString();
                if(cname.equals("")){
                    Toast.makeText(AddCourse.this, "Course name is required!", Toast.LENGTH_SHORT).show();
                    courseName.requestFocus();
                }
                else{
                    if(csession.equals("")) {
                        Toast.makeText(AddCourse.this, "Course session is required!", Toast.LENGTH_SHORT).show();
                        courseSession.requestFocus();
                    }
                    else{
                        if(courseID!=0) {
                            UpdateCourse(courseID);
                        }
                        else {
                            AddNewCourse(courseID);
                        }
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AddCourse.this, TeacherHome.class);
                myIntent.putExtra("userID",Integer.toString(userID));
                startActivity(myIntent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(courseID!=0){
                    DeleteCourse(courseID);
                }
            }
        });
    }

    private void DeleteCourse(int courseID) {
        action = "Delete";
        new SoapCall().execute();
    }

    private void AddNewCourse(int courseID) {
        action = "AddNew";
        new SoapCall().execute();
    }

    private void UpdateCourse(int courseID) {
        action = "Update";
        new SoapCall().execute();
    }

    private void GetCourseData(int courseID) {
        action = "GetData";
        new SoapCall().execute();
    }

    private void SubmitNewCourse(String cname, String csession, int userID) {
        //Toast.makeText(AddCourse.this, "Add course, name: "+cname+", "+csession+", "+userID, Toast.LENGTH_SHORT).show();
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

            if(result != null){
                //ResultLB.setText(result);
                if(result.equals("Exist")){
                    Toast.makeText(AddCourse.this,"Course already exist in the database.", Toast.LENGTH_SHORT).show();
                } else if(result.equals("Error")){
                    Toast.makeText(AddCourse.this,"Error! Please try again!", Toast.LENGTH_SHORT).show();
                } else if(result.equals("Success")){
                    if(action.equals("Update")){
                        showSimpleDialog("Course updated.", "","Success");
                    }
                    else if(action.equals("AddNew")){
                        showSimpleDialog("Course added.", "","Success");
                    }
                    else if(action.equals("Delete")){
                        showSimpleDialog("Course deleted.", "","Success");
                    }
                } else {

                    try {
                        JSONObject jsonObj = new JSONObject(result);

                        cname = jsonObj.getString("courseName");
                        csession = jsonObj.getString("courseSession");

                        courseName.setText(cname);
                        courseSession.setText(csession);

                        //Toast.makeText(ViewUsers.this,rowUsers.toString(), Toast.LENGTH_SHORT).show();


                    } catch (final JSONException e) {

                        showSimpleDialog(e.toString(), "Error", "");
                        Toast.makeText(AddCourse.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }else{
                Toast.makeText(AddCourse.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            GetData data = new GetData();
            String response = "";
            if(courseID != 0){
                if(action.equals("GetData")){
                    response = data.GetCourseDetail(courseID);
                }
                else if(action.equals("Update")){
                    response = data.UpdateCourse(cname, csession, courseID, userID);
                }
                else if(action.equals("Delete")){
                    response = data.DeleteCourse(courseID, userID);
                }
            }
            else{
                response = data.AddCourse(cname, csession, userID);
            }

            return response;
        }
    }

    /**
     * Alert box
     * @param Message
     */

    public void showSimpleDialog(String Message, String Title, final String Result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddCourse.this);
        builder.setCancelable(false);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Result.equals("Success")){
                    Intent myIntent = new Intent(AddCourse.this, TeacherHome.class);
                    myIntent.putExtra("userID",Integer.toString(userID));
                    startActivity(myIntent);
                }
            }
        });
        builder.create().show();
    }
}
