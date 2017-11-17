package oceannet.com.ziparisyonetim.MODELS;

import java.util.ArrayList;

/**
 * Created by oceannet on 31/10/17.
 */

public class OrderCurrent {


     int  OrderID;
    int Status;
    String  RoomName, FullName,Email,Description,BlockName,FloorName,CompanyName,OrderDateTime;


    ArrayList<Product>products = new ArrayList<Product>();


    public OrderCurrent(int orderID, int status, String roomName, String fullName, String email, String description, String blockName, String floorName, String companyName, String orderDateTime, ArrayList<Product> products) {
        OrderID = orderID;
        Status = status;
        RoomName = roomName;
        FullName = fullName;
        Email = email;
        Description = description;
        BlockName = blockName;
        FloorName = floorName;
        CompanyName = companyName;
        OrderDateTime = orderDateTime;
        this.products = products;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

    public String getFloorName() {
        return FloorName;
    }

    public void setFloorName(String floorName) {
        FloorName = floorName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getOrderDateTime() {
        return OrderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        OrderDateTime = orderDateTime;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
