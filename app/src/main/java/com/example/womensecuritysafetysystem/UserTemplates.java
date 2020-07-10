package com.example.womensecuritysafetysystem;

public class UserTemplates {
    public String template_text;
    public int template_id;

    public UserTemplates(String text, int id)
    {
        this.template_id = id;
        this.template_text = text;
    }
}
