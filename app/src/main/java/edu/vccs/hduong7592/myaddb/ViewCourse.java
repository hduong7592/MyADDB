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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Classes.GetData;

public class ViewCourse extends AppCompatActivity {

    int courseID, userID;

    ListView chapterList;
    ArrayList<HashMap<String, String>> chapterRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        setTitle("Course Detail");

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception ex){
            //Toast.makeText(ViewClasses.this,ex.toString(), Toast.LENGTH_SHORT).show();
            showSimpleDialog(ex.toString(), "Error");
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent myIntent = getIntent();
        courseID = 0;
        try{
            courseID = Integer.parseInt(myIntent.getStringExtra("courseID"));
        }catch (Exception ex){
            courseID = 0;
            //Toast.makeText(ViewCourse.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        userID = 0;
        try{
            userID= Integer.parseInt(myIntent.getStringExtra("userID"));
        }catch (Exception ex){
            userID = 0;
            //Toast.makeText(ViewCourse.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        chapterRows = new ArrayList<>();

        chapterList = (ListView) findViewById(R.id.chapterList);

        new SoapCall().execute();

        chapterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    String cID = chapterRows.get(position).get("chapterID").toString();
                    Intent myIntent = new Intent(ViewCourse.this, ViewChapter.class);
                    myIntent.putExtra("chapterID", cID);
                    myIntent.putExtra("userID", Integer.toString(userID));
                    myIntent.putExtra("courseID", Integer.toString(courseID));
                    startActivity(myIntent);
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

                case R.id.navigation_viewcourses:

                    myIntent = new Intent(ViewCourse.this, TeacherHome.class);
                    myIntent.putExtra("userID",Integer.toString(userID));
                    myIntent.putExtra("courseID",Integer.toString(courseID));
                    startActivity(myIntent);
                    return true;

                case R.id.navigation_addchapter:

                    myIntent = new Intent(ViewCourse.this, AddChapter.class);
                    myIntent.putExtra("userID",Integer.toString(userID));
                    myIntent.putExtra("courseID",Integer.toString(courseID));
                    startActivity(myIntent);
                    return true;

                case R.id.navigation_editcourse:

                    myIntent = new Intent(ViewCourse.this, AddCourse.class);
                    myIntent.putExtra("userID",Integer.toString(userID));
                    myIntent.putExtra("courseID",Integer.toString(courseID));
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
                    JSONArray contacts = jsonObj.getJSONArray("Chapters");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        int chapterID = c.getInt("chapterID");
                        String chapterName = c.getString("chapterName");
                        String chapterDescription = c.getString("chapterDescription");
                        String aCount = c.getString("aCount");

                        try {

                            HashMap<String, String> chapter = new HashMap<>();

                            // adding each child node to HashMap key => value
                            chapter.put("chapterID", ((Integer)chapterID).toString());
                            chapter.put("chapterName", chapterName);
                            chapter.put("chapterDescription",chapterDescription);
                            chapter.put("aCount", "Assignment Count: "+aCount);

                            // adding contact to contact list
                            chapterRows.add(chapter);
                        }
                        catch (Exception ex){
                            showSimpleDialog(ex.toString(),"");
                        }

                    }
                    try {
                        ListAdapter adapter = new SimpleAdapter(
                                ViewCourse.this, chapterRows,
                                R.layout.layout_chapters, new String[]{"chapterName", "chapterDescription","aCount"}, new int[]{R.id.ChapterNameLB,
                                R.id.ChapterDesLB, R.id.ACountLB});

                        chapterList.setAdapter(adapter);
                    }
                    catch (Exception ex){
                        showSimpleDialog(ex.toString(),"Error");
                    }
                    //Toast.makeText(ViewUsers.this,rowUsers.toString(), Toast.LENGTH_SHORT).show();


                } catch (final JSONException e) {

                    showSimpleDialog(e.toString(), "Error");
                    Toast.makeText(ViewCourse.this,e.toString(), Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(ViewCourse.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            GetData data = new GetData();
            String response = data.GetChapters(courseID);
            return response;
        }
    }

    /**
     * Alert box
     * @param Message
     */

    public void showSimpleDialog(String Message, String Title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ViewCourse.this);
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
