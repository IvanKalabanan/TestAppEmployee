package test.ivacompany.com.test.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "employee", null, 1);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table employee ("
                + "id integer primary key autoincrement ,"
                + "name text,"
                + "surname text,"
                + "middle_name text,"
                + "birthday text,"
                + "city text,"
                + "position text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
