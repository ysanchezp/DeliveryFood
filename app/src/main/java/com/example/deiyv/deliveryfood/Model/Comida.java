package com.example.deiyv.deliveryfood.Model;

public class Comida {
    private String Name,Image,Precio,MenuID;

    public Comida() {
    }

    public Comida(String name, String image, String precio, String menuID) {
        Name = name;
        Image = image;
        Precio = precio;
        MenuID = menuID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }
}
