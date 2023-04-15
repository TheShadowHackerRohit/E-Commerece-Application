package com.example.e_commerce;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;

public class Ecommerce extends Application {

   // LogIn login = new LogIn();

    private final int width = 500 , height = 400 , headerLine = 50;
    ProductList productList = new ProductList();

    Pane bodyPane ;

    GridPane footerBar;



    Order order = new Order();
    ObservableList<Product> cartItemList = FXCollections.observableArrayList();

    Button signInButton = new Button("Sign In");

    Button placeOrderButton = new Button("Place Order");
    Label welcomeLabel = new Label("Welcome Customer");

    Customer loggedInCustomer = null;

    private  void  addItemsToCart(Product product){
        if(cartItemList.contains(product)){
            return;
        }
        cartItemList.add(product);
        System.out.println("Products in Cart "+ cartItemList.stream().count());
    }

    private GridPane headerBar(){

        TextField searchBar = new TextField();
        Button searchButton   = new Button("Search");
        Button cartButton = new Button("Cart");
        Button ordersButton = new Button("Orders");

        ordersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(order.getorders());
            }
        });



        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                     bodyPane.getChildren().clear();
                     bodyPane.getChildren().add(productList.getAllProducts());
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productList.productsInCart(cartItemList));
            }
        });



        GridPane header = new GridPane();


        header.setHgap(10);

        header.add(searchBar,0,0);
        header.add(searchButton,1,0);
        header.add(signInButton,2,0);
        header.add(welcomeLabel,3,0);
        header.add(cartButton,4,0);
        header.add(ordersButton,5,0);

        return header;
    }


    private  GridPane loginPage(){
        Label userLabel = new Label(" User Name");

        Label passLabel = new Label("Password");

        TextField username = new TextField();
        username.setText("rohit@gmail.com");
        username.setPromptText("Enter User Name");

        //TextField password = new TextField();
        PasswordField password = new PasswordField();// hide when type
        password.setText("abc");

        password.setPromptText("Enter Password");
        
        Button loginButton = new Button("LogIn");

        Label messageLabel = new Label("LogIn - Message");


        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    String user = username.getText();
                    String pass = password.getText();

                    loggedInCustomer  = LogIn.customerLogIn(user,pass);
                   if(loggedInCustomer != null){
                        messageLabel.setText("Login Successful !!");
                        welcomeLabel.setText("Welcome "+ loggedInCustomer.getName());
                   }
                   else {
                       messageLabel.setText("Login Failed!");
                   }
            }
        });



        GridPane loginPane = new GridPane();

        loginPane.setTranslateY(50);
        //loginPane.setTranslateX(50);

        loginPane.setVgap(10);
        loginPane.setHgap(10);

        loginPane.add(userLabel,0,0);
        loginPane.add(username,1,0);
        loginPane.add(passLabel,0,1);
        loginPane.add(password,1,1);
        loginPane.add(loginButton,0,2);
        loginPane.add(messageLabel,1,2);

        return  loginPane;
    }

    private  void showDialog(String message){

        Dialog<String> dialog = new Dialog<String>();
        //Setting the title
        dialog.setTitle("Order Status");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        //Setting the content of the dialog
        dialog.setContentText(message);
        //Adding buttons to the dialog pane
        dialog.getDialogPane().getButtonTypes().add(type);

            dialog.showAndWait();

    }
    private  GridPane footerBar(){

        Button buyNowButton = new Button("Buy NOw");
        Button addToCartButton = new Button("Add To Cart");


        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                boolean orderStatus = false;

                if(product != null && loggedInCustomer != null){
                   orderStatus = order.placeOrder(loggedInCustomer,product);
                }
                if(orderStatus == true){
                    showDialog("Order Successful");
                }
                else{

                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                addItemsToCart(product);
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int orderCount = 0;

                if(!cartItemList.isEmpty() && loggedInCustomer != null){
                    orderCount = order.placeOrderMultipleProducts(cartItemList,loggedInCustomer);
                }
                if(orderCount > 0){
                    showDialog("Order for " + orderCount + "products placed Successfully");
                }
                else{
                        //
                }
            }
        });

        GridPane footer = new GridPane();
        footer.setHgap(10);
        footer.setTranslateY(headerLine + height);
        footer.add(buyNowButton,0,0);
        footer.add(addToCartButton,1,0);
        footer.add(placeOrderButton,2,0);

        return footer;

    }
    private  Pane createContent(){
       Pane root = new Pane();

       root.setPrefSize(width ,height+ 2 * headerLine);

       bodyPane = new Pane();
       bodyPane.setPrefSize(width,height);
       bodyPane.setTranslateY(headerLine);
       bodyPane.setTranslateX(10);
       footerBar = footerBar();

       bodyPane.getChildren().addAll(loginPage());

       //root.getChildren().add(headerBar());
       root.getChildren().addAll(headerBar(),
//               loginPage(),
//               productList.getAllProducts())
                    bodyPane,
                    footerBar
               );


       
       return root;
    }

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(createContent(),600,600,Color.BLACK);


        Image icon = new Image("C:\\Users\\rohit\\IdeaProjects\\E_Commerce\\src\\main\\resources\\com\\example\\e_commerce\\logo-e-commerce.png");
        stage.getIcons().add(icon);

        stage.setTitle("E-Commerce");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}