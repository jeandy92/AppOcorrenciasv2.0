package appocorrencias.com.appocorrencias.ClassesSA;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jeanderson on 21/04/2017.
 */

public class Banco extends SQLiteOpenHelper {


    public Banco(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase bancoDados) {

    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}





