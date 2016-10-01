package trendsapps.org.trendsharer.Model;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Date;

public class HotDeal {
    private int ID;
    private String shop;
    private String discount;
    private String content = null;
    private int duration = 20;
    private Timestamp storedDate ;
    private boolean isPublishedByAnOwner = false;
    private byte[] image = null;
    private String imagePath = "";
    private boolean hasImage = false;

    public HotDeal(){
        this.hasImage = false;
    }
    public HotDeal(int ID,String shopName, String discount){
        this.ID = ID;
        this.shop = shopName;
        this.shop = discount;
        this.hasImage = false;
    }

    public HotDeal(String shopName, String discount){
        this.shop = shopName;
        this.shop = discount;
        this.hasImage = false;
    }

    public void setShopName(String shopName){
        this.shop = shopName;
    }

    public void setDiscount(String discount){
        this.discount = discount;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

    public Date getStoredDate() {
        return storedDate;
    }

    public void setStoredDate(Timestamp storedDate) {
        this.storedDate = storedDate;
    }

    public void publishedByOwner(boolean isPublishedByAnOwner){
        this.isPublishedByAnOwner = isPublishedByAnOwner;
    }

    public void setImage(byte [] arr){
        this.image = arr;
        hasImage = true;
    }
    public void setImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        image = baos.toByteArray();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImageFromByteArr(byte[] imageByte){
        image = imageByte;
    }

    public byte[] getImageAsByteArr(){
        return image;
    }

    public Bitmap getImageAsBitMap(){

        if(image != null)
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        return null;
    }

    public String getShopName(){
        return shop;
    }

    public String getDiscount(){
        return discount;
    }

    public String getContent(){
        return content;
    }

    public int getDuration(){
        return duration;
    }

    public boolean hasImageinDeal(){return hasImage;}
    public boolean isPublishedByAnOwner(){
        return isPublishedByAnOwner;
    }

    public boolean isComplete(){
        if (shop.length()>0 && discount.length()>0){
            return true;
        }
        return false;
    }
}
