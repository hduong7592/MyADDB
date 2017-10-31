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

public class ViewChapter extends AppCompatActivity {

    int userID, chapterID, courseID;


    ListView aList;
    ArrayList<HashMap<String, String>> aRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chapter);

        setTitle("Chapter Detail");

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

        chapterID = 0;
        try{
            chapterID = Integer.parseInt(myIntent.getStringExtra("chapterID"));
        }catch (Exception ex){
            chapterID = 0;
            //Toast.makeText(ViewChapter.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        courseID = 0;
        try{
            courseID = Integer.parseInt(myIntent.getStringExtra("courseID"));
        }catch (Exception ex){
            courseID = 0;
            //Toast.makeText(ViewChapter.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        aRows = new ArrayList<>();

        aList = (ListView) findViewById(R.id.aList);

        new SoapCall().execute();

        aList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    String aID = aRows.get(position).get("aID").toString();
                    //showSimpleDialog(aID+"","");

                    Intent myIntent = new Intent(ViewChapter.this, AssignmentDetail.class);
                    myIntent.putExtra("aID", aID);
                    myIntent.putExtra("userID", Integer.toString(userID));
                    myIntent.putExtra("chapterID", Integer.toString(chapterID));
                    myIntent.putExtra("courseID", Integer.toString(courseID));
                    startActivity(myIntent);

                    //showSimpleDialog(aID+","+userID+","+chapterID,"");
                }
                catch (Exception ex){
                    showSimpleDialog(ex.toString(),"Error");
                }
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent myIntent;
            switch (item.getItemId()) {

                case R.id.navigation_viewchapter:

                    myIntent = new Intent(ViewChapter.this, ViewCourse.class);
                    myIntent.putExtra("userID", Integer.toString(userID));
                    myIntent.putExtra("chapterID", Integer.toString(chapterID));
                    myIntent.putExtra("courseID", Integer.toString(courseID));
                    startActivity(myIntent);

                    return true;

                case R.id.navigation_addassignment:

                    myIntent = new Intent(ViewChapter.this, AssignmentDetail.class);
                    myIntent.putExtra("userID", Integer.toString(userID));
                    myIntent.putExtra("chapterID", Integer.toString(chapterID));
                    myIntent.putExtra("courseID", Integer.toString(courseID));

                    startActivity(myIntent);

                    return true;
                case R.id.navigation_editchapter:

                    myIntent = new Intent(ViewChapter.this, AddChapter.class);
                    myIntent.putExtra("userID", Integer.toString(userID));
                    myIntent.putExtra("chapterID", Integer.toString(chapterID));
                    myIntent.putExtra("courseID", Integer.toString(courseID));
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

                //showSimpleDialog(result, "");

                try {


                    JSONObject jsonObj = new JSONObject(result);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("Assignments");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        int aID = c.getInt("aID");
                        String aName = c.getString("aName");
                        String aDescription = c.getString("aDescription");
                        String aTo = c.getString("assignedTo");

                        try {

                            HashMap<String, String> assignment = new HashMap<>();

                            // adding each child node to HashMap key => value
                            assignment.put("aID", ((Integer)aID).toString());
                            assignment.put("aName", aName);
                            assignment.put("aDescription",aDescription);
                            assignment.put("aTo", "Assigned To: "+aTo);

                            // adding contact to contact list
                            aRows.add(assignment);
                        }
                        catch (Exception ex){
                            showSimpleDialog(ex.toString(),"");
                        }

                    }
                    try {
                        ListAdapter adapter = new SimpleAdapter(
                                ViewChapter.this, aRows,
                                R.layout.layout_assignments, new String[]{"aName", "aTo"}, new int[]{R.id.aNameLB,
                                R.id.assignedToLB});

                        aList.setAdapter(adapter);
                    }
                    catch (Exception ex){
                        showSimpleDialog(ex.toString(),"Error");
                    }
                    //Toast.makeText(ViewUsers.this,rowUsers.toString(), Toast.LENGTH_SHORT).show();


                } catch (final JSONException e) {

                    showSimpleDialog(e.toString(), "Error");
                    Toast.makeText(ViewChapter.this,e.toString(), Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(ViewChapter.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            GetData data = new GetData();
            String response = data.GetAssignment(chapterID);
            return response;
        }
    }
    /**
     * Alert box
     * @param Message
     */

    public void showSimpleDialog(String Message, String Title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ViewChapter.this);
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
