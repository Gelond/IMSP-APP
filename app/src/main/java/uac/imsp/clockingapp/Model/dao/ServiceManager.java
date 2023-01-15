package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Objects;

import dbAdapter.ServiceSQLite;
import entity.Service;

public class ServiceManager {


    public static SQLiteDatabase Database;


    private final ServiceSQLite serviceSQLite;

    public ServiceManager(Context context) {
        serviceSQLite = new ServiceSQLite(context);
    }
    public SQLiteDatabase  open(){

        if (Database==null)
            Database=serviceSQLite.getWritableDatabase();
        else if (!Database.isOpen())
        {
            Database=null;
            Database = serviceSQLite.getWritableDatabase();
        }
        return Database;

    }
    public void close(){
        if (Database!=null && Database.isOpen())
            Database.close();
    }
    public boolean exists(@NonNull Service service){
        String query="SELECT id_service FROM service WHERE nom=?";
        String[] selectArgs= new String[]{service.getName()};
        Cursor cursor=Database.rawQuery(query,selectArgs);

        if(cursor.moveToNext())
        {

         service.setId(cursor.getInt(0));
            cursor.close();
         return true;
        }
        else {
            cursor.close();
            return false;
        }





    }


    public boolean check(@NonNull Service service) {
        String query = "SELECT nom FROM service WHERE id=?";
        String[] selectArgs = new String[]{String.valueOf(service.getId())};
        Cursor cursor = Database.rawQuery(query, selectArgs);
boolean b;
        cursor.moveToNext();
        b= Objects.equals(cursor.getString(0), service.getName());
            cursor.close();
            return b;

    }
        public boolean hasEmployee(@NonNull Service service){

        String query="SELECT * FROM EMPLOYE WHERE ID_SERVICE_REF=? ";
        String[] selectArgs= new String[]{String.valueOf(service.getId())};
        Cursor cursor=Database.rawQuery(query,selectArgs);

        if (cursor.moveToNext())

        {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }



    public void create (Service service){

        SQLiteStatement statement ;

        String query = "INSERT INTO service (nom) VALUES(?) ";





        if(!searchService(service)) {// if not exists
            statement=Database.compileStatement(query);
            statement.bindString(1,service.getName());

            statement.executeInsert();
        }
        searchService(service); //to set Id

    }

        public  boolean delete(@NonNull Service service){
        String query = "DELETE FROM service WHERE nom=?";
        SQLiteStatement statement ;

        statement = Database.compileStatement(query);
        statement.bindString(1,service.getName());
       return statement.executeUpdateDelete()==1;

    }
    public void update(@NonNull Service service, String newName){
        String query = "UPDATE service SET nom=? WHERE id_service=?";
        SQLiteStatement statement;

        statement = Database.compileStatement(query);
        statement.bindString(1, newName);
        statement.bindLong(2,service.getId());
        statement.executeUpdateDelete();

    }
    public String[] getAllServices(){
        ArrayList <String> service= new ArrayList<>();
        String query="SELECT nom FROM service ORDER BY id_service";
        Cursor cursor=Database.rawQuery(query,null);

        while (cursor.moveToNext())

            service.add(cursor.getString(0));
        cursor.close();
        return service.toArray(new String[service.size()]);


    }




    public Service[] getAllService(){
        ArrayList <Service> serviceSet= new ArrayList<>();
        Service service;

        String query="SELECT * FROM service";
        Cursor cursor=Database.rawQuery(query,null);
        while (cursor.moveToNext())

        {
            service = new Service(cursor.getInt(0));
            service.setName(cursor.getString(1));
            serviceSet.add(service);
        }
        cursor.close();
        return serviceSet.toArray(new Service[serviceSet.size()]);

    }
    public boolean searchService(@NonNull Service service){
        boolean test;
        String query="SELECT id_service FROM service WHERE nom=? ";

        String [] selectArg={
                service.getName()
        };
        Cursor cursor=Database.rawQuery(query,selectArg);
        cursor.moveToFirst();
        test=cursor.getCount()==1;


        if(test) {
            service.setId(Integer.parseInt(cursor.getString(0)));
            cursor.close();
            return true;

        }
        cursor.close();
        return false;

    }






}
