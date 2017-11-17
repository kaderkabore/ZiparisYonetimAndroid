package oceannet.com.ziparisyonetim.MODELS;

/**
 * Created by oceannet on 31/10/17.
 */

public class Product {

        String  ProductID, Image,ProductName,Description;

        Double Price  ;
        int Stock;

    public Product(String productID, String image, String productName, String description, Double price, int stock) {
        ProductID = productID;
        Image = image;
        ProductName = productName;
        Description = description;
        Price = price;
        Stock = stock;
    }


    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }
}
