package edu.vccs.hduong7592.myaddb;

import android.content.DialogInterface;
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
import java.util.List;

import Classes.GetData;
import Classes.User;

public class ViewUsers extends AppCompatActivity {

    ListView UserList;
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        setTitle("View Users");
        contactList = new ArrayList<>();

        new SoapCall().execute();

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception ex){
            //Toast.makeText(ViewClasses.this,ex.toString(), Toast.LENGTH_SHORT).show();
            showSimpleDialog(ex.toString(), "Error");
        }

        UserList = (ListView) findViewById(R.id.UserList);

        UserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = contactList.get(position).get("id").toString();
                //Toast.makeText(ViewUsers.this, name, Toast.LENGTH_LONG).show();
                showSimpleDialog(name, "");
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

            if(result != null){
                //showSimpleDialog(result, "");

                try {


                    JSONObject jsonObj = new JSONObject(result);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("Users");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        int userID = c.getInt("UserID");
                        String firstName = c.getString("FirstName");
                        String lastName = c.getString("LastName");
                        String email = c.getString("Email");
                        String username = c.getString("Username");
                        String refID = c.getString("RefID");

                        int role = c.getInt("UserRole");
                        String sessionID = c.getString("SessionID");
                        String status = c.getString("Status");

                        try {

                            HashMap<String, String> contact = new HashMap<>();

                            // adding each child node to HashMap key => value
                            contact.put("id", ((Integer)userID).toString());
                            contact.put("name", username);
                            contact.put("email", email);
                            contact.put("mobile", "123");

                            // adding contact to contact list
                            contactList.add(contact);
                        }
                        catch (Exception ex){
                            showSimpleDialog(ex.toString(),"");
                        }

                    }

                    ListAdapter adapter = new SimpleAdapter(
                            ViewUsers.this, contactList,
                            R.layout.userslayout, new String[]{"name", "id", "email"}, new int[]{R.id.NameLB,
                            R.id.UserIDLB, R.id.UserEmailLB});

                    UserList.setAdapter(adapter);

                    //Toast.makeText(ViewUsers.this,rowUsers.toString(), Toast.LENGTH_SHORT).show();


                } catch (final JSONException e) {

                    showSimpleDialog(e.toString(), "Error");
                    Toast.makeText(ViewUsers.this,e.toString(), Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(ViewUsers.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            GetData users = new GetData();
            String response = users.GetUsers();
            return response;
        }
    }

    /**
     * Alert box
     * @param Message
     */

    public void showSimpleDialog(String Message, String Title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ViewUsers.this);
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
