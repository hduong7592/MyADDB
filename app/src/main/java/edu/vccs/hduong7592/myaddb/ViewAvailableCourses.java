package edu.vccs.hduong7592.myaddb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Classes.GetData;

public class ViewAvailableCourses extends AppCompatActivity {

    int roleID, userID, courseID;
    String action;

    ListView courseList;
    ArrayList<HashMap<String, String>> courseRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_available_courses);

        setTitle("Available Courses");
        action = "GetData";
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception ex){
            //Toast.makeText(ViewClasses.this,ex.toString(), Toast.LENGTH_SHORT).show();
            showSimpleDialog(ex.toString(), "Error");
        }

        Intent myIntent = getIntent();
        roleID = 0;
        try{
            roleID = Integer.parseInt(myIntent.getStringExtra("userRole"));
        }
        catch (Exception ex){
            roleID = 0;
            //Toast.makeText(TeacherHome.this, ex.toString(), Toast.LENGTH_LONG).show();
        }
        userID = 0;
        try{
            userID = Integer.parseInt(myIntent.getStringExtra("userID"));
        }catch (Exception ex){
            userID = 0;
            Toast.makeText(ViewAvailableCourses.this, ex.toString(), Toast.LENGTH_LONG).show();
        }


        courseRows = new ArrayList<>();

        courseList = (ListView) findViewById(R.id.courseList);

        if(action.equals("GetData")) {
            new SoapCall().execute();
        }
            courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    try {

                        courseID = Integer.parseInt(courseRows.get(position).get("courseID").toString());
                        showAddCourseDialog("Do you want to add this course?", "", courseID, userID);

                    } catch (Exception ex) {
                        showSimpleDialog(ex.toString(), "Error");
                    }
                }
            });

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

            if(result != null) {

                //showSimpleDialog(result, "");
                if (result.equals("Success")) {

                    Intent myIntent = new Intent(ViewAvailableCourses.this, StudentHome.class);
                    myIntent.putExtra("roleID", Integer.toString(roleID));
                    myIntent.putExtra("userID", Integer.toString(userID));
                    startActivity(myIntent);

                }
                else if(result.equals("Exist")){
                    showSimpleDialog("You already enrolled in this course.","");

                } else
                    {
                    try {


                        JSONObject jsonObj = new JSONObject(result);

                        // Getting JSON Array node
                        JSONArray contacts = jsonObj.getJSONArray("Courses");

                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);

                            int courseID = c.getInt("courseID");
                            String courseName = c.getString("courseName");
                            String courseSession = c.getString("courseSession");
                            String courseOwner = c.getString("courseOwner");
                            String available = c.getString("available");
                            String chapterCount = c.getString("chapterCount");

                            try {

                                HashMap<String, String> course = new HashMap<>();

                                // adding each child node to HashMap key => value
                                course.put("courseID", ((Integer) courseID).toString());
                                course.put("courseName", courseName + " - " + courseSession);
                                course.put("courseSession", courseSession);
                                course.put("courseOwner", "Professor: " + courseOwner);
                                course.put("chapterCount", "Chapter Count: " + chapterCount);

                                // adding contact to contact list
                                courseRows.add(course);
                            } catch (Exception ex) {
                                showSimpleDialog(ex.toString(), "");
                            }

                        }
                        try {
                            ListAdapter adapter = new SimpleAdapter(
                                    ViewAvailableCourses.this, courseRows,
                                    R.layout.layout_courses, new String[]{"courseName", "chapterCount", "courseOwner"}, new int[]{R.id.CourseNameLB,
                                    R.id.ChapterCountLB, R.id.ownerLB});


                            courseList.setAdapter(adapter);
                        } catch (Exception ex) {
                            showSimpleDialog(ex.toString(), "Error");
                        }
                        //Toast.makeText(ViewUsers.this,rowUsers.toString(), Toast.LENGTH_SHORT).show();


                    } catch (final JSONException e) {

                        showSimpleDialog(e.toString(), "Error");
                        Toast.makeText(ViewAvailableCourses.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else{
                Toast.makeText(ViewAvailableCourses.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            GetData data = new GetData();
            String response = "";
            if(action.equals("GetData")){
                try{
                    response = data.GetAllCourses(userID);}
                catch (Exception ex){
                    showSimpleDialog(ex.toString(),"Error");
                }
            }
            else if(action.equals("Assign")){
                try{
                    response = data.AssignCourse(userID, courseID);
                }
                catch (Exception ex){
                    showSimpleDialog(ex.toString(),"Error");
                }
            }
            return response;
        }
    }

    /**
     * Alert box
     * @param Message
     */

    public void showSimpleDialog(String Message, String Title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ViewAvailableCourses.this);
        builder.setCancelable(false);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void showAddCourseDialog(String Message, String Title, int courseID, int userID) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ViewAvailableCourses.this);
        builder.setCancelable(false);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
                action = "Nothing";
            }
        }).setPositiveButton("Add course", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                action = "Assign";
                new SoapCall().execute();
            }
        });
        builder.create().show();
    }
}
