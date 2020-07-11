package com.example.womensecuritysafetysystem;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class GuardianRVAdapter extends RecyclerView.Adapter<GuardianRVAdapter.ViewHolder> {

    Context context;
    ArrayList<UserGuardian> guardians;



    public GuardianRVAdapter(Context context, ArrayList<UserGuardian> guardians)
    {
        this.context = context;
        this.guardians= guardians;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView guardian_name;
        TextView guardian_no;
        TextView guardian_mail;
        Button delete;
        Button edit;

        public ViewHolder(View itemView)
        {
            super(itemView);
            guardian_name = itemView.findViewById(R.id.guardian_name);
            guardian_mail = itemView.findViewById(R.id.guardian_mail);
            guardian_no = itemView.findViewById(R.id.guardian_no);
            delete = itemView.findViewById(R.id.guardian_del);
            edit = itemView.findViewById(R.id.guardian_edit);
        }
    }

    @NonNull
    @Override
    public GuardianRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View templateRow= LayoutInflater.from(context).inflate(R.layout.guardian_row,parent, false);
        return new ViewHolder(templateRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.guardian_name.setText(guardians.get(position).guardian_name);
        holder.guardian_mail.setText(guardians.get(position).email);
        holder.guardian_no.setText(guardians.get(position).phone_no);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gid=guardians.get(position).guardian_id;
                DBHelper db =new DBHelper(context);
                int rv = db.getWritableDatabase().delete("guardians","id = ?",new String[]{String.valueOf(gid)});
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setMessage(rv+" row deleted successfully!\nPress Ok to continue.");
                alertbox.setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                guardians.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,guardians.size());

                            }
                        });
                alertbox.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return guardians.size();
    }

}
