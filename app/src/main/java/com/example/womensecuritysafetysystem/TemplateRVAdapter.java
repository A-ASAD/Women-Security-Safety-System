package com.example.womensecuritysafetysystem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
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
        Button edit;

        public ViewHolder(View itemView)
        {
            super(itemView);
            template_text = itemView.findViewById(R.id.template_text);
            delete = itemView.findViewById(R.id.delete_temp);
            edit = itemView.findViewById(R.id.edit_temp);
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
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }

}
