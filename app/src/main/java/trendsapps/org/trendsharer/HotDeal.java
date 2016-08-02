package trendsapps.org.trendsharer;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class HotDeal {
    private int ID;
    private String shop;
    private String discount;
    private String content = null;
    private int duration = 20;
    private boolean isPublishedByAnOwner = false;
    private byte[] image = null;

    public HotDeal(){

    }
    public HotDeal(int ID,String shopName, String discount){
        this.ID = ID;
        this.shop = shopName;
        this.shop = discount;
    }

    public HotDeal(String shopName, String discount){
        this.shop = shopName;
        this.shop = discount;
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

    public void publishedByOwner(boolean isPublishedByAnOwner){
        this.isPublishedByAnOwner = isPublishedByAnOwner;
    }

    public void setImage(byte [] arr){
        this.image = arr;
    }
    public void setImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        image = baos.toByteArray();
    }

    public void setImageFromByteArr(byte[] imageByte){
        image = imageByte;
    }

    public byte[] getImageAsByteArr(){
        return image;
    }

    public Bitmap getImageAsBitMap(){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
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
