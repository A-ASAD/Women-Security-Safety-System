package com.example.womensecuritysafetysystem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class view_templates extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_templates);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        RecyclerView rv = (RecyclerView)findViewById(R.id.temp_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        UserTemplates t1 = new UserTemplates("Aenean ut tortor ultrices, hendrerit justo ac, viverra lectus. Nunc bibendum justo eu bibendum lacinia. Curabitur eleifend eleifend vestibulum. Aliquam at mattis quam. Cras vel augue sem. Nulla aliquet accumsan placerat. Nunc nec pulvinar leo.",1);
        UserTemplates t2 = new UserTemplates("Ut neque turpis, pharetra eget neque a, ullamcorper faucibus nisl. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Aliquam faucibus quis ipsum sit amet pellentesque. Sed erat neque, venenatis id est in, viverra tristique diam. Suspendisse suscipit nunc a purus ornare, eu facilisis odio elementum. Curabitur pulvinar lectus tortor, sit amet maximus ex vestibulum sit amet. Curabitur volutpat condimentum interdum.",2);
        UserTemplates t3 = new UserTemplates("Ut mauris libero, lacinia ornare condimentum vestibulum, malesuada ut leo. In scelerisque, sapien sed molestie fringilla, elit dui lacinia nibh, ut efficitur erat velit at ante. Nam molestie, nisi at porta hendrerit, turpis dui volutpat lectus, sit amet mollis ipsum dui sit amet est.",3);
        UserTemplates t4 = new UserTemplates("Cras semper dolor condimentum interdum pellentesque.",4);
        UserTemplates t5 = new UserTemplates(" Fusce justo enim, accumsan id diam id, mattis luctus libero. Sed dignissim consequat nunc ac tristique. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Morbi rhoncus et dolor non suscipit.",5);

        ArrayList <UserTemplates> list = new ArrayList<>();
        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);
        list.add(t5);
        rv.setLayoutManager(llm);
        TemplateRVAdapter adapter = new TemplateRVAdapter(this,list);
        rv.setAdapter(adapter);


    }
}