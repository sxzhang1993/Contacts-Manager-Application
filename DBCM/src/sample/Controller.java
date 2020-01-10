package sample;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Controller {

    static Connection conn = null;




    public Button conButt;

    @FXML //fxid: = "exitButton"
    private Button exitButton;

    @FXML //fxid: = "addButt"
    private Button addButt;

    @FXML //fxid: = "sButt"
    private Button sButt;

    @FXML //fxid: = "delButt"
    private Button delButt;

    @FXML //fxid: = "upButt"
    private Button upButt;



    @FXML // fx:id="TF1" firstname
    private TextField TF1;

    @FXML // fx:id="TF2" MI
    private TextField TF2;
    @FXML // fx:id="TF3" Lastname
    private TextField TF3;

    @FXML // fx:id="TF4"
    private TextField TF4;

    @FXML // fx:id="TF5"
    private TextField TF5;

    @FXML // fx:id="TF6"
    private TextField TF6;

    @FXML // fxid = UTF1
    private TextField UTF1;

    @FXML //
    private TextField UTF2;

    @FXML //
    private TextField UTF3;

    @FXML //
    private TextField UTF4;

    @FXML //
    private TextField UTF5;

    @FXML //
    private TextField UTF6;




    @FXML //onAction #exit[lat exit button
    void exitplat(ActionEvent event){

        Platform.exit();

        //exit


    }

    @FXML // add button
    void addContent(ActionEvent e) throws SQLException{

        try {
            String sql = "INSERT INTO CONTACT(fname, minit, lname, c_email, Addressinfo, c_phone) VALUES(?,?,?,?,?,?)";


            String fname = TF1.getText();
            String minit = TF2.getText();
            String lname = TF3.getText();

            String c_email = TF4.getText();
            String c_phone = TF5.getText();



            String Addressinfo = TF6.getText();

            Scanner sc = new Scanner(c_phone);
            //check if number == true for phone
            try
            {
                //System.out.println("Please input an integer");
                //nextInt will throw InputMismatchException
                //if the next token does not match the Integer
                //regular expression, or is out of range
                double usrInput=sc.nextDouble();
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, fname);
                statement.setString(2, minit);
                statement.setString(3, lname);

                statement.setString(4, c_email);
                statement.setString(5, Addressinfo);
                statement.setString(6, c_phone);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A new contact was inserted successfully!");
                }
            }
            catch(InputMismatchException exceptionp)
            {
                //Print "This is not an integer"
                //when user put other than integer
                System.out.println("Please enter numbers for phone(10 digits)");


            }





            //int rowsInserted = statement.executeUpdate();
            //if (rowsInserted > 0) {
                //System.out.println("A new contact was inserted successfully!");
           //}

        } catch(Exception eadd){
            System.out.println("error, Please fill in all the blanks");

        }


        //try (
                // PreparedStatement pstmt = conn.prepareStatement(sql)) {
                //pstmt.setString(1, fname);
                //pstmt.setString(2, minit);
                //pstmt.setString(3, lname);
                //pstmt.setDouble(4, c_id);
                //pstmt.setString(5, c_email);
               // pstmt.setDouble(6, c_phone);
               // pstmt.setString(7, Addressinfo);

                //pstmt.executeUpdate();
           // } catch (SQLException e1) {
               // System.out.println(e1.getMessage());
           // }



    }

    @FXML // delete button
    void delContent(ActionEvent e1) throws SQLException{
        try {
            String sql = "DELETE FROM Contact WHERE fname=?";
            String fname = TF1.getText();

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, fname);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A contact was deleted successfully!");
            }
        }catch(Exception edel){

            System.out.println("error");
        }
    }

    @FXML // search button
    void sContent(ActionEvent e2) throws SQLException{

        String fname1 = TF1.getText();


    try {
        //String fname1 = TF1.getText();

        String sql = "SELECT * FROM Contact WHERE fname='" + fname1 + "'";






    // to run the sql statement
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery(sql);



        if (rs.next()) {


            String fName = rs.getString(1);
            String minit = rs.getString(2);
            String lname = rs.getString(3);
            String email = rs.getString(4);
            String address = rs.getString(5);
            String phone = rs.getString(6);
            //TA1.setText(fName + "  "+ minit + "  " +lname +"  " + email + "  " + address +"  " + phone);
            UTF1.setText(fName);
            UTF2.setText(minit);
            UTF3.setText(lname);
            UTF4.setText(email);
            UTF5.setText(phone);
            UTF6.setText(address);








        }
        else{
            System.out.println("No Search Result");
        }

    } catch (Exception e) {
        System.err.println("Error");
    }



    }
    @FXML // update button
    void update(ActionEvent e3) throws SQLException{

        try{
            String sql = "UPDATE Contact SET c_email = ?, Addressinfo = ?, c_phone = ?  WHERE fname = ? AND minit = ? AND lname = ?";
            String fname = UTF1.getText();
            String minit = UTF2.getText();
            String lname = UTF3.getText();
            String email = UTF4.getText();
            String phone = UTF5.getText();
            String address = UTF6.getText();
            PreparedStatement ps = conn.prepareStatement(
                    sql);

            // set the preparedstatement parameters
            ps.setString(1,email);
            ps.setString(2,address);
            ps.setString(3,phone);
            ps.setString(4,fname);
            ps.setString(5,minit);
            ps.setString(6,lname);




            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();

            System.out.println("updated!\n"+ "go check in database" );


        } catch(Exception eup){

            System.err.println("error");
        }





    }



    //onAction conDB Connect button
    public void conDB(ActionEvent event){

        //connect to the database

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Contacts_Manager+\n" +
            // "&serverTimezone=CDT","root","rock96321478");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Contacts_Manager?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC&user=root&password=rock96321478");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CONTACT;");
            while (rs.next())
            {
                System.out.println(rs.getString("fname"));
            }
            rs.close();
            System.out.println("Success!!");
        }
        catch(Exception ex)
        {
            System.out.println("Error in connection: " + ex.getMessage());
        }
    }



}
