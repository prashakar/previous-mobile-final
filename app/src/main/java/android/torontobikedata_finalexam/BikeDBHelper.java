package android.torontobikedata_finalexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BikeDBHelper extends SQLiteOpenHelper {

    private Context context;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILENAME = "Bikes.db";

    private static String CREATE_STATEMENT = "" +
            "CREATE TABLE Bikes(" +
            "_id integer primary key autoincrement," +
            "numOfUnits int not null," +
            "latitude double not null," +
            "longitude double not null," +
            "municipality text not null," +
            "address text null)";

    private static String DROP_STATEMENT = "" +
            "DROP TABLE Bikes";

    public BikeDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STATEMENT);
        db.execSQL(CREATE_STATEMENT);
    }

    public ArrayList<Bike> getAllBikes(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Bike> results = new ArrayList<>();

        String[] columns = new String[] {"numOfUnits", "latitude", "longitude",
        "municipality", "address"};
        String where = "";
        String[] whereArgs = new String[]{};
        String groupBy = "";
        String groupArgs = "";
        String orderBy = "numOfUnits";

        Cursor cursor = db.query("Bikes", columns, where, whereArgs, groupBy, groupArgs, orderBy);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            int numOfUnits = cursor.getInt(0);
            double latitude = cursor.getDouble(1);
            double longitude = cursor.getDouble(2);
            String municipality = cursor.getString(3);
            String address = cursor.getString(4);

            Bike bike = new Bike(numOfUnits, latitude, longitude, municipality, address);
            results.add(bike);

            cursor.moveToNext();
        }
        return results;
    }

    public Bike createBike(int numOfUnits, double latitude, double longitude,
                           String municipality, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("numOfUnits", numOfUnits);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("municipality", municipality);
        values.put("address", address);
        db.insertOrThrow("Bikes", null, values);

        Bike bike = new Bike(numOfUnits, latitude, longitude, municipality, address);

        return bike;
    }

    public void updateBike(Bike bike){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long id = bike.getId();
        values.put("numOfUnits", bike.getNumOfUnits());
        values.put("latitude", bike.getLatitude());
        values.put("longitude", bike.getLongitude());
        values.put("municipality", bike.getMunicipality());
        values.put("address", bike.getAddress());
        db.update("Bikes", values, "_id="+id, null);
    }

    public void deleteAllBikes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Bikes", null, null);
    }
}