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

public class ViewAssignments extends AppCompatActivity {

    int userID, courseID;

    ListView aList;
    ArrayList<HashMap<String, String>> aRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignments);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setTitle("Assignments");

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception ex){
            //Toast.makeText(ViewClasses.this,ex.toString(), Toast.LENGTH_SHORT).show();
            showSimpleDialog(ex.toString(), "Error");
        }

        Intent myIntent = getIntent();
        userID = 0;
        try{
            userID = Integer.parseInt(myIntent.getStringExtra("userID"));
        }catch (Exception ex){
            userID = 0;
            //Toast.makeText(ViewChapter.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        courseID = 0;
        try{
            courseID = Integer.parseInt(myIntent.getStringExtra("courseID"));
        }catch (Exception ex){
            courseID = 0;
            //Toast.makeText(ViewChapter.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        aRows = new ArrayList<>();

        aList = (ListView) findViewById(R.id.aList);

        try{
        new SoapCall().execute();

        aList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    String aID = aRows.get(position).get("aID").toString();
                    String chapterID = aRows.get(position).get("chapterID").toString();
                    String aName = aRows.get(position).get("aName").toString();
                    String aDes = aRows.get(position).get("aDes").toString();

                    showSimpleDialog(aDes,aName);

                    //showSimpleDialog(aID+"","");
                    /*
                    Intent myIntent = new Intent(ViewAssignments.this, AssignmentDetail.class);
                    myIntent.putExtra("aID", aID);
                    myIntent.putExtra("userID", Integer.toString(userID));
                    myIntent.putExtra("chapterID", chapterID);
                    myIntent.putExtra("courseID", Integer.toString(courseID));
                    startActivity(myIntent);
                    */
                    //showSimpleDialog(aID+","+userID+","+chapterID,"");
                }
                catch (Exception ex){
                    showSimpleDialog(ex.toString(),"Error");
                }
            }
        });}
        catch (Exception ex){
            showSimpleDialog(ex.toString(),"Error");
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_viewcourses:

                    Intent myIntent = new Intent(ViewAssignments.this, StudentHome.class);
                    myIntent.putExtra("courseID", courseID);
                    myIntent.putExtra("userID", Integer.toString(userID));
                    startActivity(myIntent);

                    return true;

            }
            return false;
        }

    };

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

                //showSimpleDialog(result, userID +", "+courseID);

                try {


                    JSONObject jsonObj = new JSONObject(result);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("Assignments");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        int aID = c.getInt("aID");
                        int chapterID = c.getInt("chapterID");
                        String aName = c.getString("aName");
                        String aDes = c.getString("aDes");
                        String chapterName = c.getString("chapterName");
                        String chapterDes = c.getString("chapterDescription");

                        try {

                            HashMap<String, String> assignment = new HashMap<>();

                            // adding each child node to HashMap key => value
                            assignment.put("aID", ((Integer)aID).toString());
                            assignment.put("aName", aName);
                            assignment.put("aDes", aDes);
                            assignment.put("chapterID", ((Integer)chapterID).toString());
                            assignment.put("chapterName",chapterName);
                            assignment.put("chapterDescription", chapterDes);

                            // adding contact to contact list
                            aRows.add(assignment);
                        }
                        catch (Exception ex){
                            showSimpleDialog(ex.toString(),"");
                        }

                    }
                    try {
                        ListAdapter adapter = new SimpleAdapter(
                                ViewAssignments.this, aRows,
                                R.layout.layout_assigned_assignments, new String[]{"chapterName", "chapterDescription", "aName"}, new int[]{R.id.chapterNameLB,
                                R.id.chapterDesLB, R.id.assignmentName});

                        aList.setAdapter(adapter);
                    }
                    catch (Exception ex){
                        showSimpleDialog(ex.toString(),"Error");
                    }
                    //Toast.makeText(ViewUsers.this,rowUsers.toString(), Toast.LENGTH_SHORT).show();


                } catch (final JSONException e) {

                    showSimpleDialog(e.toString(), "Error");
                    Toast.makeText(ViewAssignments.this,e.toString(), Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(ViewAssignments.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            String response = "";
            try {
                GetData data = new GetData();
                response = data.GetAssignedAssignment(userID, courseID);
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(ViewAssignments.this);
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
