package com.example.womensecuritysafetysystem;

public class UserGuardian {
    public String guardian_name;
    public String phone_no;
    public int guardian_id;
    public String email;

    public UserGuardian(String name, String phone, String mail, int id)
    {
        this.email = mail;
        this.guardian_name = name;
        this.guardian_id = id;
        this.phone_no = phone;

    }
}
