package trendsapps.org.trendsharer;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHandler {

    private SQLiteDatabase hotDealsDataBase;
    private String dealsTableName;
    private ContentValues contentValues;
    public static boolean newDealAdded = false;
    public static final String DATABSENAME = "trends-db";
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
        String createTableQuery = "CREATE TABLE IF NOT EXISTS "+dealsTableName+" (ID integer primary key AUTOINCREMENT,Shop VARCHAR,Discount VARCHAR,Content VARCHAR,Duration INTEGER,Photo BLOB,Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);";
        hotDealsDataBase.execSQL(createTableQuery);
    }

    public void addDeal(HotDeal newDeal){
        contentValues = new ContentValues();
        contentValues.put("Shop",newDeal.getShopName());
        contentValues.put("Discount",newDeal.getDiscount());
        contentValues.put("Content",newDeal.getContent());
        contentValues.put("Duration",newDeal.getDuration());
        contentValues.put("Photo",newDeal.getImageAsByteArr());
        contentValues.put("Timestamp", String.valueOf(newDeal.getStoredDate()));
        hotDealsDataBase.insert(dealsTableName,null,contentValues);
        newDealAdded = true;

    }
    /*
       Retrieve all the hot deals from the sqlite database,
    */
    public ArrayList<HotDeal> getDeals(){

        String getAlltheDataQuery = "SELECT * FROM "+ dealsTableName;// think about divide querying in to parts.
        Cursor dataRows = null;
        dataRows = hotDealsDataBase.rawQuery(getAlltheDataQuery,null);
        ArrayList<HotDeal> hotDeals = new ArrayList<>();
        // if cursor is moved to the first row. then fecth all the data.
        try {

            if (dataRows.moveToFirst()) {
                while (dataRows.isAfterLast() == false) {
                    HotDeal temp = new HotDeal();
                    temp.setShopName(dataRows.getString(dataRows.getColumnIndex("Shop")));
                    temp.setContent(dataRows.getString(dataRows.getColumnIndex("Content")));
                    temp.setDiscount(dataRows.getString(dataRows.getColumnIndex("Discount")));
                    temp.setDuration(dataRows.getInt(dataRows.getColumnIndex("Duration")));
                    String date = dataRows.getString(dataRows.getColumnIndex("Timestamp"));
                    Timestamp dateTime = Timestamp.valueOf(date);
                    temp.setStoredDate(dateTime);

                    temp.setImage(dataRows.getBlob(dataRows.getColumnIndex("Photo"))); // image is taken as a byte array.
                    // now change this to a BitMap so that we can get it as an Image
                    hotDeals.add(temp);
                    System.out.println("Check");
                    dataRows.moveToNext();
                }
            }
        }
        finally {
            dataRows.close();
        }




        // Hot deals are created. Need to create it.
        return hotDeals; // terminating condition.
    }

    public int getHotDealsCount(){
        String getCountQuery = "SELECT count(*) as alldeals FROM "+ dealsTableName;
        Cursor dealsCount = null;
        dealsCount = hotDealsDataBase.rawQuery(getCountQuery,null);
        int dealstotal = dealsCount.getCount();
        return dealstotal;
    }
    public SQLiteDatabase getHotDealsDataBase(){
        return getHotDealsDataBase();
    }
}
