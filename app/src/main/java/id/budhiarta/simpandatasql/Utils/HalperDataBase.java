package id.budhiarta.simpandatasql.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import id.budhiarta.simpandatasql.Model.ModelToDo;

public class HalperDataBase extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COl_1 = "ID";
    private static final String COl_2 = "TASK";
    private static final String COl_3 = "STATUS";

    public HalperDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTask(ModelToDo model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COl_2, model.getTask());
        values.put(COl_3, 0);
        db.insert(TABLE_NAME , null, values);

    }

    public  void updateTask(int id, String task){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COl_2, task);
        db.update(TABLE_NAME, values, "ID=?", new String[]{
                String.valueOf(id)
        });
    }

    public void updateStatus(int id, int status){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COl_3, status);
        db.update(TABLE_NAME, values, "ID=?", new String[]{
                String.valueOf(id)
        });
    }

    public void deleteTaks(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{
                String.valueOf(id)
        });
    }
    public List<ModelToDo> getAllTaks(){
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<ModelToDo> modelList = new ArrayList<>();

        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do{
                        ModelToDo task = new ModelToDo();
                        task.setId(cursor.getInt(cursor.getColumnIndex(COl_1)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(COl_2)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(COl_3)));
                        modelList.add(task);

                    }while(cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
}

