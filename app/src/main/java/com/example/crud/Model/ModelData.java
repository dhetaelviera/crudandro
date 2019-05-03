package com.example.crud.Model;

public class ModelData {

    String username, email, nama, password;

    public ModelData(){}

    public ModelData(String username, String nama, String email, String password){
        this.email=email;
        this.username=username;
        this.nama=nama;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
