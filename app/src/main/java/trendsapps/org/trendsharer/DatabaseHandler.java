package trendsapps.org.trendsharer;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHandler {

    private SQLiteDatabase hotDealsDataBase;
    private String dealsTableName;
    private ContentValues contentValues;

    public DatabaseHandler(SQLiteDatabase database){
      this.hotDealsDataBase = database;
    }

    public DatabaseHandler(String databaseName,String tableName,Activity activity){
        try {
            this.hotDealsDataBase = activity.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE,null);
            Log.i("Database Initialized","Database initialization completed ");
        }catch (Exception e){
            Log.e("Database initializing",e.getMessage());
        }

        dealsTableName = tableName;
        String createTableQuery = "CREATE TABLE IF NOT EXISTS "+dealsTableName+"(ID integer primary key AUTOINCREMENT,Shop VARCHAR,Discount VARCHAR,Content VARCHAR,Duration INTEGER,Photo blob);";
        hotDealsDataBase.execSQL(createTableQuery);
    }

    public void addDeal(HotDeal newDeal){
        contentValues = new ContentValues();
        contentValues.put("Shop",newDeal.getShopName());
        contentValues.put("Discount",newDeal.getDiscount());
        contentValues.put("Content",newDeal.getContent());
        contentValues.put("Duration",newDeal.getDuration());
        contentValues.put("Photo",newDeal.getImageAsByteArr());
        hotDealsDataBase.insert(dealsTableName,null,contentValues);
//        String insertNewDealQuery = "INSERT INTO "+dealsTableName+"(Shop,Discount,Content,Duration,Snap) VALUES('"+newDeal.getShopName()+"','"+newDeal.getDiscount()+"','"+newDeal.getContent()+"',"+newDeal.getDuration()+",'"+newDeal.getImageAsByteArr()+"');";
//        hotDealsDataBase.execSQL(insertNewDealQuery);
    }
    /*
       Retrieve all the hot deals from the sqlite database,
    */
    public HotDeal[] getDeals(){

        String getAlltheDataQuery = "SELECT * FROM "+ dealsTableName+ "WHERE TRUE";
        Cursor dataRows = null;
        dataRows = hotDealsDataBase.rawQuery(getAlltheDataQuery,null);
        HotDeal[] hotDeals = new HotDeal[dataRows.getCount()];
        // if cursor is moved to the first row. then fecth all the data.
        if(dataRows.moveToFirst());{
            for(int i=0;i<hotDeals.length;i++){
                hotDeals[i].setShopName(dataRows.getString(dataRows.getColumnIndex("Content")));
                hotDeals[i].setContent(dataRows.getString(dataRows.getColumnIndex("Content")));
                hotDeals[i].setDiscount(dataRows.getString(dataRows.getColumnIndex("Discount")));
                hotDeals[i].setDuration(dataRows.getInt(dataRows.getColumnIndex("Duration")));
                String date = dataRows.getString(dataRows.getColumnIndex("StoredDate"));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                try {
                    Date dateTime = format.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                hotDeals[i].setImage(dataRows.getBlob(dataRows.getColumnIndex("Photo"))); // image is taken as a byte array.
                // now change this to a BitMap so that we can get it as an Image

            }
        }
        // Hot deals are created. Need to create it.
        return hotDeals;
    }
    public SQLiteDatabase getHotDealsDataBase(){
        return getHotDealsDataBase();
    }
}
