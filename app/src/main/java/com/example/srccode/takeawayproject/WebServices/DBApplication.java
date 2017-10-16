package com.example.srccode.takeawayproject.WebServices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.srccode.takeawayproject.Classes.ClassCookingDB;

/**
 * Created by ayman on 2017-05-24.
 */

public class DBApplication  extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        ActiveAndroid.initialize(this);
    }
}


//    DBInfo dbInfo;// object from the inner class
//    public DBApplication(Context context) {
//        dbInfo=new DBInfo(context); // to get the data from the DBInfo to the DB class ,to able to trans data
//
//
//    }
//    public  long InsertData(String Id,String Name, String Price,String Image){   /// play as a tunnel to the database (for security )
//        SQLiteDatabase sqLiteDatabase=dbInfo.getWritableDatabase();// to go to db class and create the data base, and write on it
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(DBInfo.user_Id,Id);
//        contentValues.put(DBInfo.names,Name);
//        contentValues.put(DBInfo.price,Price);
//        contentValues.put(DBInfo.image,Image);
//        // insert the data
//        long id =sqLiteDatabase.insert(DBInfo.table_name,null,contentValues);
//        return id;
//
//    }
//    public  String View(){
//        SQLiteDatabase sqLiteDatabase=dbInfo.getWritableDatabase();// to go to db class and create the data base, and read from it
//        String [] columns={DBInfo.user_Id,DBInfo.names,DBInfo.price,DBInfo.image};
//        Cursor cursor=sqLiteDatabase.query(DBInfo.table_name,columns,null,null,null,null,null);
//
//        StringBuffer stringBuffer=new StringBuffer();
//        while (cursor.moveToNext()){// tgere are another data
//            int u_id =cursor.getInt(0);
//            String ordernamee=cursor.getString(1);
//            String orderprice=cursor.getString(2);
//            String orderimage=cursor.getString(2);
//
//            stringBuffer.append(u_id+" "+ordernamee+" "+orderprice+" "+orderimage+"\n");
//
//        }
//
//
//        return stringBuffer.toString();
//    }
//
//    public  String Search(String uname){
//        SQLiteDatabase sqLiteDatabase=dbInfo.getWritableDatabase();// to go to db class and create the data base, and read from it
//        String [] columns={DBInfo.user_Id,DBInfo.names,DBInfo.price,DBInfo.image};
//        Cursor cursor=sqLiteDatabase.query(DBInfo.table_name,columns,DBInfo.names+ " = '"+ uname+"'",null,null,null,null);
//
//        StringBuffer stringBuffer=new StringBuffer();
//        while (cursor.moveToNext()){// tgere are another data
//            // another way to get the data
//            int index1=cursor.getColumnIndex(DBInfo.names);
//            int index2=cursor.getColumnIndex(DBInfo.price);
//            int index3=cursor.getColumnIndex(DBInfo.image);
//            String ordersnameee=cursor.getString(index1);// to get the data
//            String ordersprice=cursor.getString(index2);
//            String ordersimage=cursor.getString(index3);
//
//
//            //int u_id =cursor.getInt(0);
//            //  String usernamee=cursor.getString(1);
//            // String emaile=cursor.getString(2);
//
//            stringBuffer.append(ordersnameee+" "+ordersprice+""+ordersimage+"\n");
//
//        }
//
//
//        return stringBuffer.toString();
//    }
//
//    public  int Update(String oldName,String newName){
//        SQLiteDatabase sqLiteDatabase=dbInfo.getWritableDatabase();// to go to db class and create the data base, and read from it
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(DBInfo.names,newName);
//        String [] whereArg={oldName};
//
//        //
//        int count=sqLiteDatabase.update(DBInfo.table_name,contentValues,DBInfo.names+" LIKE ? ",whereArg);// can use "=?" insteade of "LIKE ?'
//
//        return count;
//    }
//
//
//    public  int DeleteName (String deletedname){
//        SQLiteDatabase sqLiteDatabase=dbInfo.getWritableDatabase();// to go to db class and create the data base, and read from it
//
//        String [] whereArg={deletedname};
//
//        //
//        int count=sqLiteDatabase.delete(DBInfo.table_name,DBInfo.names+" LIKE ? ",whereArg);// can use "=?" insteade of "LIKE ?'
//
//        return count;
//    }
///// i make a class inside a class to make the app secured , user can't access it
//// i make it static to make the outer class able to use it
//// but it can't use the InsertData method as it isn't a static
//static class DBInfo extends SQLiteOpenHelper {
//    // variables must define it
//    private static final String database_name="AymanDB";
//    private static final String table_name="table_one";
//    private static final int dbversion_name=5;// must change the version when upgrade
//    private static final String user_Id="Id";
//    private static final String price="Price";
//    private static final String image="Image";
//    private static final String names="Name";
//
//    private static final String drop_table="Drop TABLE IF EXISTS "+table_name;// drop table if exist
//    private static final String Create_table="CREATE TABLE "+table_name+" ("+user_Id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
//            +names+" NVARCHAR(255) ," +price+" NVARCHAR(255) ,"+image+" NVARCHAR(255));";// crate  table in sqlite  +email+" NVARCHAR(255)
//
//    private Context context;
//    public DBInfo(Context context) {
//        // super(context, name, factory, version);
//
//        super( context, database_name, null, dbversion_name);
//        this.context=context;
//        Toast.makeText(context,"this is constructor",Toast.LENGTH_LONG).show();// to notify us that the constructo is called
//
//
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        try {
//            db.execSQL(Create_table);
//            Toast.makeText(context,"on create is called",Toast.LENGTH_LONG).show();// to notify us that the constructo is called
//
//        }catch (SQLException e){
//            Toast.makeText(context,"create due to :"+e,Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        try {
//            db.execSQL(drop_table);// to drop it if exists before notto make problems
//            onCreate(db);// call the function on create
//            Toast.makeText(context,"onupgrade is called",Toast.LENGTH_LONG).show();// to notify us that the constructo is called
//
//        }catch (SQLException e){
//            Toast.makeText(context,"upgrade due to :"+e,Toast.LENGTH_LONG).show();
//        }
//    }
//}
//
//}
