package com.example.womensecuritysafetysystem;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

public class TemplateRVAdapter extends RecyclerView.Adapter<TemplateRVAdapter.ViewHolder> {

    Context context;
    ArrayList<UserTemplates> templates;



    public TemplateRVAdapter(Context context, ArrayList<UserTemplates> templates)
    {
        this.context = context;
        this.templates= templates;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView template_text;
        Button delete;

        public ViewHolder(View itemView)
        {
            super(itemView);
            template_text = itemView.findViewById(R.id.template_text);
            delete = itemView.findViewById(R.id.delete_temp);
        }
    }

    @NonNull
    @Override
    public TemplateRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View templateRow= LayoutInflater.from(context).inflate(R.layout.template_row,parent, false);
        return new ViewHolder(templateRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.template_text.setText(templates.get(position).template_text);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tid=templates.get(position).template_id;
                DBHelper db =new DBHelper(context);
                int rv = db.getWritableDatabase().delete("templates","id = ?",new String[]{String.valueOf(tid)});
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setMessage("Template deleted successfully!\nPress Ok to continue.");
                alertbox.setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                templates.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,templates.size());
                            }
                        });
                alertbox.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }

}
