package com.example.robert.zakupy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.robert.zakupy.model.Category;
import com.example.robert.zakupy.model.CurrentProducts;
import com.example.robert.zakupy.model.Product;

/**
 * Created by Robert on 2016-06-09.
 */
public class DbAdapter {
    private static final String DEBUG_TAG = "SqLiteTodoManager";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";



    private static final String DB_CREATE_PRODUCT_TABLE =
            "CREATE TABLE Product ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "id_category INTEGER NOT NULL, "  +
                    "unit TEXT NULL " +
                    ");";
    private static final String DROP_PRODUCT_TABLE =
            "DROP TABLE IF EXISTS Product";

    private static final String DB_CREATE_CATEGORY_TABLE =
            "CREATE TABLE Category ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL " +
                    ");";
    private static final String DROP_CATEGORY_TABLE =
            "DROP TABLE IF EXISTS Category";

    private static final String DB_CREATE_CURRENTPRODUCTS_TABLE =
            "CREATE TABLE CurrentProducts ( " +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_product INTEGER NOT NULL, "  +
                    "amount TEXT NULL, " +
                    "is_completed INTEGER DEFAULT 0 " +
                    ");";
    private static final String DROP_CURRENTPRODUCTS_TABLE =
            "DROP TABLE IF EXISTS CurrentProducts";


    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;


    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_PRODUCT_TABLE);
            db.execSQL(DB_CREATE_CATEGORY_TABLE);
            db.execSQL(DB_CREATE_CURRENTPRODUCTS_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Tables ver." + DB_VERSION + "created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_PRODUCT_TABLE);
            db.execSQL(DROP_CATEGORY_TABLE);
            db.execSQL(DROP_CURRENTPRODUCTS_TABLE);

            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Tables updated from ver." + oldVersion
                    + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");

            onCreate(db);
        }
    }

    public DbAdapter(Context context) {
        this.context = context;
    }

    public DbAdapter open(){
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    public long insertProduct(String name, int id_category, String unit) {
        ContentValues newProductValues = new ContentValues();
        newProductValues.put("name", name);
        newProductValues.put("id_category", id_category);
        newProductValues.put("unit", unit);
        return db.insert("Product", null, newProductValues);
    }

    //    public boolean updateProduct(TodoTask task) {
//        long id = task.getId();
//        String description = task.getDescription();
//        boolean completed = task.isCompleted();
//        return updateTodo(id, description, completed);
//    }
//
//    public boolean updateTodo(long id, String description, boolean completed) {
//        String where = KEY_ID + "=" + id;
//        int completedTask = completed ? 1 : 0;
//        ContentValues updateTodoValues = new ContentValues();
//        updateTodoValues.put(KEY_DESCRIPTION, description);
//        updateTodoValues.put(KEY_COMPLETED, completedTask);
//        return db.update(DB_TODO_TABLE, updateTodoValues, where, null) > 0;
//    }
//
    public boolean deleteProduct(int id){
        String where = "id" + "=" + id;
        return db.delete("Product", where, null) > 0;
    }

    public boolean deleteProductTmp(int id){
        String where = "id" + "!=" + id;
        return db.delete("Product", where, null) > 0;
    }

    public Cursor getAllProducts() {
        String[] columns = {"id", "name", "id_category", "unit"};
        return db.query("Product", columns, null, null, null, null, null);
    }

    public Product getProductById(int id) {
        String[] columns = {"id", "name", "id_category", "unit"};
        String where = "id" + "=" + id;
        Cursor cursor = db.query("Product", columns, where, null, null, null, null);
        Product product = null;
        if(cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(1);
            int id_category = cursor.getInt(2);
            String unit = cursor.getString(3);
            product = new Product(id, name, id_category, unit);
        }
        return product;
    }

    public Category getCategoryById(int id) {
        String[] columns = {"id", "name"};
        String where = "id" + "=" + id;
        Cursor cursor = db.query("Category", columns, where, null, null, null, null);
        Category category = null;
        if(cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(1);

            category = new Category(id, name);
        }
        return category;
    }

    public long insertCategory(String name) {
        ContentValues newCategoryValues = new ContentValues();
        newCategoryValues.put("name", name);
        return db.insert("Category", null, newCategoryValues);
    }

    public long insertCategory(int id, String name) {
        ContentValues newCategoryValues = new ContentValues();
        newCategoryValues.put("id", id);
        newCategoryValues.put("name", name);
        return db.insert("Category", null, newCategoryValues);
    }

    public Cursor getAllProductsOrderBy(String orderBy) {
        String[] columns = {"id", "name", "id_category", "unit"};
        return db.query("Product", columns, null, null, null, null, orderBy);
    }

    public long insertCurrentProduct(int productId, String amount) {
        ContentValues newCategoryValues = new ContentValues();
        newCategoryValues.put("id_product", productId);
        newCategoryValues.put("amount", amount);
        return db.insert("CurrentProducts", null, newCategoryValues);
    }

    public CurrentProducts getCurrentProductById(int productId) {
        String[] columns = {"id", "id_product", "amount", "is_completed"};
        String where = "id_product" + "=" + productId;
        Cursor cursor = db.query("CurrentProducts", columns, where, null, null, null, null);
        CurrentProducts currentProduct = null;
        if(cursor != null && cursor.moveToFirst()) {
           int id = cursor.getInt(0);
            int id_product = cursor.getInt(1);
            String amount = cursor.getString(2);
            int is_completedInt = cursor.getInt(3);
            boolean is_completed = (is_completedInt != 0);
            currentProduct = new CurrentProducts(id, id_product, amount, is_completed);
        }
        return currentProduct;
    }

    public boolean deleteCurrentProductTmp(int id){
        String where = "id" + "!=" + id;
        return db.delete("CurrentProducts", where, null) > 0;
    }

    public boolean deleteCurrentProduct(int productId){
        String where = "id_product" + "=" + productId;
        return db.delete("CurrentProducts", where, null) > 0;
    }

    public boolean deleteCategoryTmp(int id){
        String where = "id" + "!=" + id;
        return db.delete("Category", where, null) > 0;
    }

}




