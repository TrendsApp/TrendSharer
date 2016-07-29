package trendsapps.org.trendsharer;


import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseHandler {

    private SQLiteDatabase hotDealsDataBase;
    private String dealsTableName;

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
        String createTableQuery = "CREATE TABLE IF NOT EXISTS "+dealsTableName+"(ID integer primary key AUTOINCREMENT,Shop VARCHAR,Discount VARCHAR,Content VARCHAR,Duration INTEGER);";
        hotDealsDataBase.execSQL(createTableQuery);
    }

    public void addDeal(HotDeal newDeal){
        String insertNewDealQuery = "INSERT INTO "+dealsTableName+"(Shop,Discount,Content,Duration) VALUES('"+newDeal.getShopName()+"','"+newDeal.getDiscount()+"','"+newDeal.getContent()+"',"+newDeal.getDuration()+");";
        hotDealsDataBase.execSQL(insertNewDealQuery);
    }

    public SQLiteDatabase getHotDealsDataBase(){
        return getHotDealsDataBase();
    }
}
