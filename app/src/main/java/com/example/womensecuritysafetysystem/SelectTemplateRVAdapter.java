package com.example.womensecuritysafetysystem;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectTemplateRVAdapter extends RecyclerView.Adapter<SelectTemplateRVAdapter.ViewHolder> {

    Context context;
    ArrayList<String> templates;
    GMailSender sender;
    String user="ssafity52@gmail.com";
    String password="mcproject";
    String sb,bd,rp;
    public SelectTemplateRVAdapter(Context c, ArrayList<String> t)
    {
        context = c;
        templates = t;
    }
    @NonNull
    @Override
    public SelectTemplateRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View template= LayoutInflater.from(context).inflate(R.layout.select_template_row,parent, false);
        return new ViewHolder(template);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTemplateRVAdapter.ViewHolder holder, final int position) {
        holder.templateText.setText(templates.get(position));
        holder.templateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setMessage("Following message will be sent as [ SMS and Mail ]:\n\n\""+templates.get(position)+"\"\n\nPress Ok to confirm.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (context.checkSelfPermission(Manifest.permission.SEND_SMS) ==
                                            PackageManager.PERMISSION_DENIED) {
                                        String[] permissions = {Manifest.permission.SEND_SMS};
                                        ActivityCompat.requestPermissions((Activity) context, permissions, 10);
                                    }
                                    else
                                    {
                                        int uid=0;
                                        String naam="";
                                        String u_mail="";
                                        sender = new GMailSender(user,password);
                                        sb="Alert(Women Safety & Security)!";
                                        DBHelper db = new DBHelper(context);
                                        Cursor name = db.getReadableDatabase().query("users",null,"username = ?",new String[]{home.username},null,null,null);
                                        while (name.moveToNext())
                                        {
                                            naam=name.getString(1);
                                            u_mail=name.getString(4);
                                        }
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
                                                SendSMS( u1.phone_no,templates.get(position),u1.guardian_name);
                                                bd="**** SENDER ****\nName: "+naam.toUpperCase()+"\nE-mail: "+u_mail+
                                                        "\n\n** MESSAGE **\n"+templates.get(position)+
                                                        "\n\n***\nThis mail is sent from Women Security System";
                                                rp=u1.email;
                                                new MyAsynClass().execute();
                                            }
                                        }
                                        else
                                        {
                                            new AlertDialog.Builder(context)
                                                    .setMessage("You have no guardians added yet!!")
                                                    .setNegativeButton("OK", null)
                                                    .show();
                                        }
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Alert Cancelled!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }
    class MyAsynClass extends AsyncTask<Void,Void,Void> {
         ProgressDialog pDialog;
         int flag=1;
         @Override
         protected void onPreExecute(){
             super.onPreExecute();
             pDialog=new ProgressDialog(context);
             pDialog.setMessage("Please wait, sending e-mails...");
             pDialog.show();
         }
         protected Void doInBackground(Void...mApi){
             try {
                 sender.sendMail(sb,bd,user,rp);
                 Log.d("send","done");
             } catch (Exception e) {
                 flag=0;
                 Log.d("exceptionsending",e.toString());
             }
             return null;
         }
         protected void onPostExecute(Void result)
         {
             super.onPostExecute(result);
             pDialog.cancel();
             if(flag==0)
             {
                 Toast.makeText(context, "Mail Sending Failed!\nCheck your Network Connenction", Toast.LENGTH_SHORT).show();
             }
             else
                 Toast.makeText(context, "Mail sent", Toast.LENGTH_SHORT).show();
         }
    }

    void SendSMS(String num, String msg, String name)
    {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(msg+"\n***\nThis message is sent from Women Security System");
            smsManager.sendMultipartTextMessage(num, null, parts,
                    null, null);
            Toast.makeText(context, "Message sent to "+name,Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(context,ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        Button templateText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            templateText = (Button) itemView.findViewById(R.id.temp_select_txt);
        }
    }
}
