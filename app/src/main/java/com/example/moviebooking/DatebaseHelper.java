package com.example.moviebooking;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatebaseHelper  extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static String DB_NAME = "kinobilet.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;
    private boolean mDataBaseCreated = false; // Флаг, показывающий, была ли создана база данных

    public DatebaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        this.mContext = context;

        // Пересоздаем базу данных при каждом запуске
        deleteDatabase();  // Удаляем старую базу
        try {
            createDataBase();    // Создаем новую базу из assets
        } catch (IOException e) {
            Log.e("DatabaseHelper", "Error creating database", e);
            throw new RuntimeException(e);
        }
    }


    public void createDataBase() throws IOException {
        if (checkDataBase()) {
            // База данных существует, удаляем её
            deleteDatabase();
        }

        // Копируем базу данных из assets
        copyDataBase();
        Log.i("DatabaseHelper", "Database created successfully.");
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    // public boolean openDataBase() { // Изменено возвращаемое значение
    public void openDataBase() throws Exception {
        String mPath = DB_PATH + DB_NAME;
        try {
            mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READWRITE); //  Разрешаем чтение и запись
            if (mDataBase == null) {
                throw new Exception("Failed to open database: " + mPath);
            }
            Log.i("Database", "Database opened");
        } catch (Exception e) {
            Log.e("Database", "Error opening database: " + mPath, e);
            throw e;
        }
        // return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        super.close(); // Add this to close the database
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "onCreate() called");
        // Код для создания таблиц
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseHelper", "onUpgrade() called with: oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]");
        // Код для обновления таблиц
    }

    private void deleteDatabase() {
        if(mContext.deleteDatabase(DB_NAME)){
            Log.i("DatebaseHelper", "Database deleted");
        } else {
            Log.e("DatabaseHelper", "Failed to delete database.");
        }
    }
}