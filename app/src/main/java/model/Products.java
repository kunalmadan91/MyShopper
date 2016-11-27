package model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KUNAL on 25-11-2016.
 */

public class Products implements Parcelable{

    private String name;
    private String category;
    private int price;
    private int imageId;
    private String description;
    private int productId;


    public Products(String name, String category, int price, int imageId, String description,int productId) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageId = imageId;
        this.description = description;
        this.productId = productId;
    }


    protected Products(Parcel in) {
        name = in.readString();
        category = in.readString();
        price = in.readInt();
        imageId = in.readInt();
        description = in.readString();
        productId = in.readInt();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);
        dest.writeInt(price);
        dest.writeInt(imageId);
        dest.writeString(description);
        dest.writeInt(productId);
    }
}
