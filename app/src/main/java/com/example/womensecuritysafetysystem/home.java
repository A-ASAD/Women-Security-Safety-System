package com.example.womensecuritysafetysystem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class home extends AppCompatActivity {
    ArrayList<String> templates;
    GMailSender sender;
    String user="ssafity52@gmail.com";
    String password="mcproject";
    String sb,bd,rp;
    public static String username;
    Button btLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(home.this);
        String user=sp.getString("uname", null);
        if(user != null)
        {
            username = user;
        }
        else if(login.username != null)
        {
            username=login.username;
        }
        else
        {
            username=sign_up.username;
        }
        final TextView add_guardian = (TextView)findViewById(R.id.button3);
        add_guardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_guardian = new Intent(home.this, add_guardian.class);
                add_guardian.putExtra("recordNo", -1);
                startActivity(add_guardian);
            }
        });
        final TextView add_template = (TextView)findViewById(R.id.button5);
        add_template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_template = new Intent(home.this, add_template.class);
                startActivity(add_template);
            }
        });


        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        
        final TextView view_guardian= (TextView)findViewById(R.id.button4);
        view_guardian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view_guardian = new Intent(home.this, view_guardian.class);
                startActivity(view_guardian);
            }
        });

        final TextView view_templates = (TextView)findViewById(R.id.button6);
        view_templates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view_templates = new Intent(home.this, view_templates.class);
                startActivity(view_templates);
            }
        });

        findViewById(R.id.alert_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent select_template = new Intent(home.this, select_template.class);
                startActivity(select_template);
            }
        });
        btLocation= findViewById(R.id.alert_btn1);
        //Intitialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check permission
                if(ActivityCompat.checkSelfPermission(home.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
                    //When Permission Granted
                    getLocation();
                }
                else {
                    //When Permission Denied
                    ActivityCompat.requestPermissions(home.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            44);
                }
            }
        });
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize Location
                Location location = task.getResult();
                if(location !=null)
                {
                     try {
                        //Initialize GeoCoder
                        Geocoder geocoder = new Geocoder(home.this, Locale.getDefault());
                        //Initialize address list
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );

                         int uid=0;
                         String naam="";
                         DBHelper db = new DBHelper(home.this);
                         Cursor name = db.getReadableDatabase().query("users",null,"username = ?",new String[]{home.username},null,null,null);
                         while (name.moveToNext())
                         {
                             naam=name.getString(1);
                         }
                         sender = new GMailSender(user,password);
                         sb="Alert(Women Safety & Security)!";
                         Cursor cur = db.getReadableDatabase().query("users",null,"username = ?",new String[]{home.username},null,null,null);
                         while (cur.moveToNext())
                         {
                             uid=cur.getInt(0);
                         }
                         Cursor cursor = db.getReadableDatabase().query("guardians",null,"uid = ?",new String[]{String.valueOf(uid)},null,null,null);
                         if(cursor.getCount() > 0)
                         {
                             while (cursor.moveToNext())
                             {
                                 UserGuardian u1 = new UserGuardian(cursor.getString(1), cursor.getString(2),
                                         cursor.getString(3), cursor.getInt(0) );
                                 SendSMS( u1.phone_no,addresses.get(0).getAddressLine(0),u1.guardian_name);
                                 bd="**** SENDER ****\nName: "+naam.toUpperCase()+"\nE-mail: "+u1.email+
                                         "\n\n** MESSAGE **\nPlease help me. I'm at:\n"
                                         +addresses.get(0).getAddressLine(0)+"\n\n***\nThis mail is sent from Women Security System";
                                 rp=u1.email;
                                 new home.MyAsynClass().execute();
                             }
                         }
                         else
                         {
                             new AlertDialog.Builder(home.this)
                                     .setMessage("You have no guardians added yet!!")
                                     .setNegativeButton("OK", null)
                                     .show();
                         }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    class MyAsynClass extends AsyncTask<Void,Void,Void> {
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog=new ProgressDialog(home.this);
            pDialog.setMessage("Please wait, sending e-mails...");
            pDialog.show();
        }
        protected Void doInBackground(Void...mApi){
            try {
                sender.sendMail(sb,bd,user,rp);
                Log.d("send","done");
            } catch (Exception e) {
                Log.d("exceptionsending",e.toString());
            }
            return null;
        }
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            pDialog.cancel();
            Toast.makeText(home.this, "Mail Sent", Toast.LENGTH_SHORT).show();
        }
    }

    void SendSMS(String num, String msg, String name)
    {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage("Please help me. I'm at:\n"+msg+
                    "\n***\nThis message is sent from Women Security System");
            smsManager.sendMultipartTextMessage(num, null, parts,
                    null, null);
            Toast.makeText(this, "Message sent to "+name,Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this,ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void logout()
    {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(home.this);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("uname", null);
        Ed.commit();
        Intent login = new Intent(home.this, login.class);
        startActivity(login);
        Toast.makeText(home.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
