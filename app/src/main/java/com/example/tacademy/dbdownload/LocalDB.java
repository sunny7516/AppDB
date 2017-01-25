package com.example.tacademy.dbdownload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

// SQLite로 로그 저장하기
public class LocalDB extends SQLiteOpenHelper {
    String APP_CACHE_DIR = "/data/data/" + "com.example.tacademy.dbdownload" + "/db";
    String DB_FILENAME;

    // 데이터 베이스 생성
    public LocalDB(Context context) {
        super(context, "log", null, 1);
        // 디비 파일명 획득
        DB_FILENAME = StorageHelper.getInstance().getString(context, "DBNAME");
        U.getInstance().log("SQLite db 데이터베이스 생성");
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        String path = APP_CACHE_DIR + "/" + DB_FILENAME;
        return SQLiteDatabase.openDatabase(
                path,
                null,
                SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS
        );
       // return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase(){
        String path = APP_CACHE_DIR + "/" + DB_FILENAME;
        return SQLiteDatabase.openDatabase(
                path,
                null,
                SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.NO_LOCALIZED_COLLATORS
        );
       // return super.getWritableDatabase();
    }

    // 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE `tbl_lastCall` ( " +
                " `idx` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " `uid` TEXT, " +
                " `name` TEXT, " +
                " `tel` TEXT, " +
                " `type` INTEGER, " +
                " `regdate` TEXT " +
                ");");
    }

    // 테이블 업데이트, 디비 업데이트
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // 데이터 삽입
    public void insertLog(LastCallModel params) {
        U.getInstance().log("tbl_lastCall 레코드 추가");
        String sql = "insert into tbl_lastCall (uid, name, tel, type, regdate) values " +
                "(?,?,?,?,?);";
        SQLiteDatabase db = getWritableDatabase();/*
        db.execSQL(sql, new String[]{params.getUid(),
                params.getName(), params.getTel(),
                params.getType()+"", params.getRegdate()} );*/
        db.execSQL(sql, params.getParam());
        db.close();
        U.getInstance().log("tbl_lastCall 레코드 추가 완료");
    }

    // 데이터 삽입 : sql을 모를 때 사용
    public void insertLogEx(LastCallModel params) {
        U.getInstance().log("tbl_lastCall 레코드 추가");
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("uid", params.getUid());
        contentValues.put("name", params.getName());
        contentValues.put("tel", params.getTel());
        contentValues.put("type", params.getType());
        contentValues.put("regdate", params.getRegdate());
        db.insert("tbl_lastCall", null, contentValues);
        U.getInstance().log("tbl_lastCall 레코드 추가 완료");
        db.close();
    }

    // 데이터 조회
    public ArrayList<LastCallModel> selectLog() {
        U.getInstance().log("tbl_lastCall 레코드 조회");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tbl_lastCall order by idx desc limit 5;\n", null);
        //cursor.moveToFirst();

        ArrayList<LastCallModel> arrayList = new ArrayList<LastCallModel>();

        while (cursor.moveToNext()) {
            arrayList.add(new LastCallModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5)
            ));
        }
        cursor.close();
        db.close();
        return arrayList;
    }
}
