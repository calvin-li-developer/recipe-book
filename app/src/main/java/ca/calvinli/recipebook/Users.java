package ca.calvinli.recipebook;

import java.io.Serializable;

public class Users implements Serializable {
    private String fullName;
    private String email;

    public Users (String fullName, String email)
    {
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
