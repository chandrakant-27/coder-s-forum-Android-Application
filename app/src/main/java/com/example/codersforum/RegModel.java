package com.example.codersforum;

public class RegModel {
    private String email;
    private String name;
    private String pass;

    RegModel(String email,String name,String pass)
    {
        this.email=email;
        this.pass=pass;
        this.name=name;

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    RegModel()
    {

    }
}
