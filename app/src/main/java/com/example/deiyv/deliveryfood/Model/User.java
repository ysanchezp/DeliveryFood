package com.example.deiyv.deliveryfood.Model;

public class User {
    private String name;
    private String Telefono;
    private String Password;
    private String Dni;

    public User() {
    }

    public User(String name, String password, String dni) {
        this.name = name;
        Password = password;
        Dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDni() {
        return Dni;
    }

    public void setDni(String dni) {
        Dni = dni;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
