package edu.vccs.hduong7592.myaddb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Classes.GetData;

public class StudentHome extends AppCompatActivity {

    int roleID;
    int userID;
    String firstName;
    ListView courseList;
    ArrayList<HashMap<String, String>> courseRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
            Toast.makeText(StudentHome.this, ex.toString(), Toast.LENGTH_LONG).show();
        }
        firstName = "";

        try {
            firstName = myIntent.getStringExtra("firstName");
        } catch (Exception ex) {
            firstName = "";
        }

        if (firstName !=null &&  !firstName.isEmpty()){
            setTitle("Welcome "+firstName+"!");
        }
        else{
            setTitle("Welcome Student!");
        }

        courseRows = new ArrayList<>();

        courseList = (ListView) findViewById(R.id.courseList);


        try {
            new SoapCall().execute();

            courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    try {
                        String cID = courseRows.get(position).get("courseID").toString();
                        Intent myIntent = new Intent(StudentHome.this, ViewAssignments.class);
                        myIntent.putExtra("courseID", cID);
                        myIntent.putExtra("userID", Integer.toString(userID));
                        startActivity(myIntent);
                    } catch (Exception ex) {
                        showSimpleDialog(ex.toString(), "Error");
                    }
                }
            });
        }
        catch (Exception ex){
            showSimpleDialog(ex.toString(),"Error");
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_addcourse:

                    Intent myIntent = new Intent(StudentHome.this, ViewAvailableCourses.class);
                    myIntent.putExtra("userID", Integer.toString(userID));
                    startActivity(myIntent);

                    return true;
                case R.id.navigation_logout:
                    startActivity(new Intent(StudentHome.this, Home.class));
                    return true;
            }
            return false;
        }

    };

    private class SoapCall extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(result != null){

                //showSimpleDialog(result, "");

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
                        String assignmentCount = c.getString("assignmentCount");

                        try {

                            HashMap<String, String> course = new HashMap<>();

                            // adding each child node to HashMap key => value
                            course.put("courseID", ((Integer)courseID).toString());
                            course.put("courseName", courseName +" - "+courseSession);
                            course.put("courseSession", courseSession);
                            course.put("courseOwner", courseOwner);
                            course.put("assignmentCount", "Assignment Count: "+assignmentCount);

                            // adding contact to contact list
                            courseRows.add(course);
                        }
                        catch (Exception ex){
                            showSimpleDialog(ex.toString(),"");
                        }

                    }
                    try {
                        ListAdapter adapter = new SimpleAdapter(
                                StudentHome.this, courseRows,
                                R.layout.layout_student_courses, new String[]{"courseName", "courseOwner", "assignmentCount"}, new int[]{R.id.courseNameLB,
                                R.id.courseOwnerLB, R.id.assignmentCountLB});

                        courseList.setAdapter(adapter);
                    }
                    catch (Exception ex){
                        showSimpleDialog(ex.toString(),"Error");
                    }
                    //Toast.makeText(ViewUsers.this,rowUsers.toString(), Toast.LENGTH_SHORT).show();


                } catch (final JSONException e) {

                    showSimpleDialog(e.toString(), "Error");
                    Toast.makeText(StudentHome.this,e.toString(), Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(StudentHome.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            String response = "";
            try {
                GetData data = new GetData();
                response = data.GetAssignedCourses(userID);
            }
            catch (Exception ex){
                showSimpleDialog(ex.toString(),"");
            }
            return response;
        }
    }

    /**
     * Alert box
     * @param Message
     */

    public void showSimpleDialog(String Message, String Title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(StudentHome.this);
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
}
