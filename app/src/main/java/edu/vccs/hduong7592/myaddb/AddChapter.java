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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Classes.GetData;

public class AddChapter extends AppCompatActivity {

    int courseID, userID, chapterID;
    String chapterName, chapterDes, action, message;
    EditText chapterNameTxt, chapterDesTxt;
    Button submitBtn, cancelBtn, deleteBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chapter);

        setTitle("Add Chapter");

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
            Toast.makeText(AddChapter.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        courseID = 0;
        try{
            courseID = Integer.parseInt(myIntent.getStringExtra("courseID"));
        }catch (Exception ex){
            courseID = 0;
            Toast.makeText(AddChapter.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        chapterID = 0;
        try{
            chapterID = Integer.parseInt(myIntent.getStringExtra("chapterID"));
        }catch (Exception ex){
            chapterID = 0;
            Toast.makeText(AddChapter.this, ex.toString(), Toast.LENGTH_LONG).show();
        }
        
        chapterNameTxt = (EditText) findViewById(R.id.chapterNameTxt);
        chapterDesTxt = (EditText) findViewById(R.id.chapterDesTxt);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        deleteBtn .setVisibility(View.GONE);

        if(chapterID != 0){
            GetChapterData(chapterID);
            setTitle("Chapter Detail");
            deleteBtn.setVisibility(View.VISIBLE);
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                chapterName = chapterNameTxt.getText().toString();
                chapterDes = chapterDesTxt.getText().toString();
                
                if(chapterName.equals("")){
                    Toast.makeText(AddChapter.this, "Chapter name is required!", Toast.LENGTH_LONG).show();
                    chapterNameTxt.requestFocus();
                }
                else{
                    if(chapterDes.equals("")){
                        Toast.makeText(AddChapter.this, "Chapter description is required!", Toast.LENGTH_LONG).show();
                        chapterDesTxt.requestFocus();
                    }
                    else{
                        if(chapterID!=0){
                            UpdateChapter(chapterID);
                        }
                        else {
                            AddNewChapter(chapterID);
                        }
                    }
                }
                
            }
        });
        
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(AddChapter.this, ViewCourse.class);
                myIntent.putExtra("userID",Integer.toString(userID));
                myIntent.putExtra("courseID",Integer.toString(courseID));
                startActivity(myIntent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chapterID!=0){
                    DeleteChapter(chapterID);
                }
            }
        });
    }

    private void DeleteChapter(int chapterID) {
        action = "Delete";
        new SoapCall().execute();
    }

    private void UpdateChapter(int chapterID) {
        action = "Update";
        new SoapCall().execute();
    }

    private void GetChapterData(int chapterID) {
        action = "GetData";
        new SoapCall().execute();
    }

    private void AddNewChapter(int chapterID) {
        action = "AddNew";
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
                    Toast.makeText(AddChapter.this,"Chapter already exist in the database.", Toast.LENGTH_SHORT).show();
                } else if(result.equals("Error")){
                    Toast.makeText(AddChapter.this,"Error! Please try again!", Toast.LENGTH_SHORT).show();
                } else if(result.equals("Success")){
                    if(action.equals("AddNew")){
                        message = "Chapter added.";
                    }
                    else if(action.equals("Update")){
                        message = "Chapter updated.";
                    }
                    else if(action.equals("Delete")){
                        message = "Chapter deleted.";
                    }
                    showSimpleDialog(message, "","Success");
                }
                else{
                    try {
                        JSONObject jsonObj = new JSONObject(result);

                        chapterName = jsonObj.getString("chapterName");
                        chapterDes = jsonObj.getString("chapterDescription");

                        chapterNameTxt.setText(chapterName);
                        chapterDesTxt.setText(chapterDes);

                        //Toast.makeText(ViewUsers.this,rowUsers.toString(), Toast.LENGTH_SHORT).show();


                    } catch (final JSONException e) {

                        showSimpleDialog(e.toString(), "Error", "");
                        Toast.makeText(AddChapter.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

            }else{
                Toast.makeText(AddChapter.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            GetData data = new GetData();
            String response = "";
            if(chapterID != 0){
                if(action.equals("GetData")){
                    response = data.GetChapterDetail(chapterID);
                }
                else if(action.equals("Update")){
                    response = data.UpdateChapter(chapterName, chapterDes, chapterID, userID);
                }
                else if(action.equals("Delete")){
                    response = data.DeleteChapter(chapterID, userID);
                }
            }
            else{
                if(action.equals("AddNew")){
                    response = data.AddChapter(chapterName, chapterDes, courseID, userID);
                }
            }

            return response;
        }
    }

    /**
     * Alert box
     * @param Message
     */

    public void showSimpleDialog(String Message, String Title, final String Result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddChapter.this);
        builder.setCancelable(false);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Result.equals("Success")){
                    Intent myIntent = new Intent(AddChapter.this, ViewCourse.class);
                    myIntent.putExtra("userID",Integer.toString(userID));
                    myIntent.putExtra("courseID",Integer.toString(courseID));
                    startActivity(myIntent);
                }
            }
        });
        builder.create().show();
    }
}
