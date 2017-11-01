package edu.vccs.hduong7592.myaddb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Classes.GetData;


public class Home extends AppCompatActivity {

    TextView ResultLB;
    EditText UsernameTxt, PasswordTxt;
    Button LoginBtn, RegisterBtn;
    ProgressBar pb;

    public static String User, Pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Assignments Distributor");
        UsernameTxt = (EditText) findViewById(R.id.UsernameTxt);
        PasswordTxt = (EditText) findViewById(R.id.PasswordTxt);
        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        RegisterBtn = (Button) findViewById(R.id.RegisterBtn);

        pb = (ProgressBar) findViewById(R.id.pg);
        pb.setVisibility(View.GONE);


        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User = UsernameTxt.getText().toString();
                Pass = PasswordTxt.getText().toString();

                if(User.equals("")){
                    Toast.makeText(Home.this, "Username is required!", Toast.LENGTH_SHORT).show();
                    UsernameTxt.requestFocus();
                }
                else{
                    if(Pass.equals("")){
                        Toast.makeText(Home.this, "Password required!", Toast.LENGTH_SHORT).show();
                        PasswordTxt.requestFocus();
                    }
                    else{

                        pb.setVisibility(View.VISIBLE);

                        new SoapCall().execute();
                    }

                }
            }
        });

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Register.class);
                startActivity(i);
            }
        });
    }

    private class SoapCall extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            pb.setVisibility(View.GONE);

            if(result != null){
                //ResultLB.setText(result);


                try {
                    JSONObject jsonObj = new JSONObject(result);

                    String FirstName = jsonObj.getString("FirstName");
                    String LastName = jsonObj.getString("LastName");
                    String SessionID = jsonObj.getString("SessionID");
                    String Status = jsonObj.getString("Status");
                    int userID = jsonObj.getInt("UserID");
                    int Role = jsonObj.getInt("UserRole");

                    Intent myintent;

                    switch (Role){
                        case 1:
                            myintent = new Intent(Home.this, AdminHome.class);
                            myintent.putExtra("firstName", FirstName);
                            myintent.putExtra("lastName", LastName);
                            myintent.putExtra("userRole", Integer.toString(Role));
                            myintent.putExtra("userID", Integer.toString(userID));
                            startActivity(myintent);
                            break;
                        case 2:
                            myintent = new Intent(Home.this, AdminHome.class);
                            myintent.putExtra("firstName", FirstName);
                            myintent.putExtra("lastName", LastName);
                            myintent.putExtra("userRole", Integer.toString(Role));
                            myintent.putExtra("userID", Integer.toString(userID));
                            startActivity(myintent);
                            break;
                        case 3:
                            myintent = new Intent(Home.this, TeacherHome.class);
                            myintent.putExtra("firstName", FirstName);
                            myintent.putExtra("lastName", LastName);
                            myintent.putExtra("userRole", Integer.toString(Role));
                            myintent.putExtra("userID", Integer.toString(userID));
                            startActivity(myintent);
                            break;
                        case 4:
                            myintent = new Intent(Home.this, StudentHome.class);
                            myintent.putExtra("firstName", FirstName);
                            myintent.putExtra("lastName", LastName);
                            myintent.putExtra("userRole", Integer.toString(Role));
                            myintent.putExtra("userID", Integer.toString(userID));
                            startActivity(myintent);
                            break;
                        default:
                            //Do nothing
                    }

                } catch (final JSONException e) {

                    Toast.makeText(Home.this,e.toString(), Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(Home.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            GetData LogInData = new GetData();
            String response = LogInData.LogIn(User, Pass, "Mobile");
            return response;

        }
    }

}
