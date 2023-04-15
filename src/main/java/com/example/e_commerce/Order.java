package com.example.e_commerce;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class Order {

     TableView<Product> orderTable;

    public   boolean placeOrder(Customer customer, Product product){
        try {
            //insert into orders(customers_id, product_id, status) values (1, 1 , 'Ordered');
            String placeOrder = "insert into orders(customers_id, product_id, status) values ("
                    + customer.getId()+","+ product.getId() + ", 'Ordered')";


            DataBaseConnection dbConn = new DataBaseConnection();

            return dbConn.insertUpdate(placeOrder);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public   int placeOrderMultipleProducts(ObservableList<Product> productObservableList, Customer customer){
        int count = 0;
        for(Product product : productObservableList){
            if(placeOrder(customer,product)){
                count++;
            }

        }
        return count;
    }
    public  Pane getorders(){

        ObservableList<Product> productList = Product.getAllProduct();

        return  createTableFromList(productList);

    }
    public  Pane createTableFromList(ObservableList<Product> orderList){

        TableColumn id = new TableColumn("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

//        ObservableList<Product> data = FXCollections.observableArrayList();
//        data.addAll(new Product(123,"Laptop A",(double)534.5),
//                new Product (1534,"Laptop B",(double)236.5));


        orderTable = new TableView<>();
        orderTable.setItems(orderList);
        orderTable.getColumns().addAll(id,name,price);

        Pane tablePane = new Pane();
        tablePane.getChildren().add(orderTable);

        return tablePane;

    }

    public   Pane getOrders(Customer customer ){
        //    select orders.oid, products.name, products.price
        //    from orders
        //    inner join products on orders.product_id = products.pid
        //    where customers_id = 6;

        String order =  " select orders.oid, products.name, products.price from orders inner join products on orders.product_id = products.pid where customers_id = "+ customer.getId() ;
        ObservableList<Product> orderList = Product.getProducts(order);

        return createTableFromList(orderList);
    }



}
