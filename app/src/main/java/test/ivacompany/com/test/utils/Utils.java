package test.ivacompany.com.test.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import test.ivacompany.com.test.TestApp;
import test.ivacompany.com.test.models.Employee;

/**
 * Created by root on 13.01.17.
 */

public class Utils {

    private static DBHelper dbHelper;
    private static SQLiteDatabase db;

    private static Realm realm;

    private static DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

    public static void initDB(){
        if (dbHelper == null){
            dbHelper = new DBHelper(TestApp.getAppContext());
            db = dbHelper.getWritableDatabase();
        }
        if (realm == null) {
            realm.init(TestApp.getAppContext());
            realm = Realm.getDefaultInstance();
        }
    }

    public static void addEmployee(Employee employee) {
        String insertQuery = "INSERT into employee" +
                "(name,surname,middle_name,birthday,city,position)" +
                " VALUES(" +
                "'"+employee.getName()+"'," +
                "'"+employee.getSurname()+"'," +
                "'"+employee.getMiddleName()+"'," +
                "'"+formatter.format(employee.getBirthday())+"'," +
                "'"+employee.getCity()+"'," +
                "'"+employee.getPosition()+"'" +
                ")";
        db.execSQL(insertQuery);
    }

    public static List<Employee> readFromDB() {
        List<Employee> list = new ArrayList<>();
        String selectQuery = "SELECT " +
                "id,name,surname,middle_name,birthday,city,position" +
                " FROM employee";
        Cursor c = db.rawQuery(selectQuery, new String[] {});

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int id = c.getColumnIndex("id");
            int name = c.getColumnIndex("name");
            int surname = c.getColumnIndex("surname");
            int middleName = c.getColumnIndex("middle_name");
            int birthday = c.getColumnIndex("birthday");
            int city = c.getColumnIndex("city");
            int position = c.getColumnIndex("position");

            do {
                try {
                    list.add(new Employee(
                            c.getLong(id),
                            c.getString(name),
                            c.getString(surname),
                            c.getString(middleName),
                            formatter.parse(c.getString(birthday)),
                            c.getString(city),
                            c.getString(position)
                    ));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (c.moveToNext());
        } else
            Log.d("SQLITE", "0 rows");
        c.close();
        return list;
    }

    public static Employee getEmployee(long id) {
        String selectQuery = "SELECT " +
                "name,surname,middle_name,birthday,city,position" +
                " FROM employee " +
                "WHERE id=" + id;
        Cursor c = db.rawQuery(selectQuery, new String[] {});
        if (c.moveToFirst()) {
            int name = c.getColumnIndex("name");
            int surname = c.getColumnIndex("surname");
            int middleName = c.getColumnIndex("middle_name");
            int birthday = c.getColumnIndex("birthday");
            int city = c.getColumnIndex("city");
            int position = c.getColumnIndex("position");

            do {
                try {
                    return new Employee(
                            id,
                            c.getString(name),
                            c.getString(surname),
                            c.getString(middleName),
                            formatter.parse(c.getString(birthday)),
                            c.getString(city),
                            c.getString(position)
                    );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (c.moveToNext());
        } else
            Log.d("SQLITE", "0 rows");
        c.close();
        return null;
    }

    public static void updateUser(Employee employee) {
        String updateQuery = "UPDATE employee SET " +
                "name='" + employee.getName() + "'," +
                "surname='" + employee.getSurname() + "'," +
                "middle_name='" + employee.getMiddleName() + "'," +
                "birthday='" + formatter.format(employee.getBirthday()) + "'," +
                "city='" + employee.getCity() + "'," +
                "position='"+ employee.getPosition() + "'" +
                " WHERE id=" + employee.getId();
        db.execSQL(updateQuery);
    }

    public static List<Employee> readFromRealm() {
        List<Employee> list = new ArrayList<>();
        RealmResults<Employee> results = realm.where(Employee.class).findAll();
        for(Employee e: results){
            list.add(e);
        }
        return list;
    }

    public static void addToRealm(Employee employee) {
        realm.beginTransaction();
        realm.copyToRealm(employee);
        realm.commitTransaction();
    }

    public static void updateRealmUser(Employee employee) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(employee);
        realm.commitTransaction();
    }

    public static Employee getRealmEmployee(long id) {
        Employee result = realm.where(Employee.class).equalTo(Constants.ID, id).findFirst();
        return result;
    }

    public static void removeFromRealm(long id) {
        realm.beginTransaction();
        RealmResults<Employee> result = realm.where(Employee.class).equalTo(Constants.ID,id).findAll();
        result.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static Realm getRealm(){
        return realm;
    }
}
