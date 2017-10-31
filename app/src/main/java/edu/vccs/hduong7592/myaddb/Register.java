package edu.vccs.hduong7592.myaddb;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import Classes.GetData;

public class Register extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    EditText CodeTxt, FirstNameTxt, LastNameTxt, EmailTxt, UsernameTxt, PasswordTxt, RePasswordTxt;
    Button SubmitBtn, CancelBtn;

    String Code = "";
    String FirstName = "";
    String LastName = "";
    String Email = "";
    String Username = "";
    String Password = "";
    String RePassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CodeTxt = (EditText) findViewById(R.id.CodeTxt);
        FirstNameTxt = (EditText) findViewById(R.id.FirstNameTxt);
        LastNameTxt = (EditText) findViewById(R.id.LastNameTxt);
        EmailTxt = (EditText) findViewById(R.id.EmailTxt);
        UsernameTxt = (EditText) findViewById(R.id.UsernameTxt);
        PasswordTxt = (EditText) findViewById(R.id.PasswordTxt);
        RePasswordTxt = (EditText) findViewById(R.id.RePasswordTxt);
        SubmitBtn = (Button) findViewById(R.id.SubmitBtn);
        CancelBtn = (Button) findViewById(R.id.CancelBtn);

        spinner = (Spinner) findViewById(R.id.spinner);

        adapter = ArrayAdapter.createFromResource(this, R.array.Register_Code, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String Value = adapterView.getItemAtPosition(position).toString();

                if(Value.equals("STUDENT")){
                    CodeTxt.setText(Value);
                }
                else{
                    CodeTxt.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Code = CodeTxt.getText().toString();
                FirstName = FirstNameTxt.getText().toString();
                LastName = LastNameTxt.getText().toString();
                Email = EmailTxt.getText().toString();
                Username = UsernameTxt.getText().toString();
                Password = PasswordTxt.getText().toString();
                RePassword = RePasswordTxt.getText().toString();

                if(Password.equals(RePassword)){
                    RegisterUser(Code, FirstName, LastName, Email, Username, Password);
                }
                else{
                    showSimpleDialog("Password does not match","Error","Error");
                }
            }
        });
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, Home.class);
                startActivity(i);
            }
        });
    }

    private void RegisterUser(String code, String firstName, String lastName, String email, String username, String password) {
        new SoapCall().execute();
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


                try {
                    JSONObject jsonObj = new JSONObject(result);

                    String FirstName = jsonObj.getString("FirstName");
                    String LastName = jsonObj.getString("LastName");
                    String SessionID = jsonObj.getString("SessionID");
                    String Status = jsonObj.getString("Status");
                    int Role = jsonObj.getInt("UserRole");

                    String re = FirstName +" - "+LastName +"\n"+SessionID+"\n"+Status +" Role: "+Role;

                    Toast.makeText(Register.this,re.toString(), Toast.LENGTH_SHORT).show();

                    if(Status.equals("Registered")){
                        showSimpleDialog("Registered successsfully! Please login!", "Congratulation", "Success");
                    }
                    else{
                        showSimpleDialog(re, "Error", "Error");
                    }

                } catch (final JSONException e) {

                    Toast.makeText(Register.this,e.toString(), Toast.LENGTH_SHORT).show();
                }


            }else{
                Toast.makeText(Register.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            GetData RegisterUser = new GetData();
            String response = RegisterUser.RegisterUser(Code, FirstName, LastName, Email, Username, Password);
            return response;
        }
    }

    /**
     * Alert box
     * @param Message
     */

    public void showSimpleDialog(String Message, String Title, final String Result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
        builder.setCancelable(false);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Result.equals("Success")){
                    Intent newintent = new Intent(Register.this, Home.class);
                    startActivity(newintent);
                }
            }
        });
        builder.create().show();
    }
}
