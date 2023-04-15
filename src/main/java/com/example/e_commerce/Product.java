package com.example.e_commerce;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;



//    public Product(int i, String laptop, double v) {
//    }

    public int getId(){
        return id.get();
    }
    public String getName(){
        return name.get();
    }
    public Double getPrice(){
        return price.get();
    }

    public  Product(int id, String name, double price){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public static  ObservableList<Product> getAllProduct(){
        String allProductList = "select * from products";

        // serach wala khud se krna hai

        //select * from products where name like '%<searchString>%'
        //select * from products where name like '%Lenovo%'

        return getProducts(allProductList);
    }

    public static  ObservableList<Product> getProducts(String query) {
        DataBaseConnection dbConn = new DataBaseConnection();
        ResultSet rs = dbConn.getQueryTable(query);
        ObservableList<Product> result = FXCollections.observableArrayList();
        try {
            if (rs != null) {
                while (rs.next()) {
                    //Taking out values from ResultSet
                    result.add(new Product(
                            rs.getInt("pid"),
                            rs.getString("name"),
                            rs.getDouble("price")
                    )
                    );

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    return  result;

    }

}
