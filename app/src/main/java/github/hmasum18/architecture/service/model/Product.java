package github.hmasum18.architecture.service.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "product_table")
public class Product {
    @PrimaryKey //for room
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("image")
    @Expose
    private String image;

    @Ignore // ignore by room
    @SerializedName("rating")
    @Expose
    private Rating rating;

    @Expose(serialize = false, deserialize = false)
    private String ratingString;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getRatingString() {
        return rating.toString();
    }

    public void setRatingString(String ratingString) {
        this.rating = new Gson().fromJson(ratingString, Rating.class);
        this.ratingString = ratingString;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}