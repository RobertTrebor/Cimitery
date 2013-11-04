package de.cimitery.android.cimitery;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBCreator extends SQLiteOpenHelper {
	
	Context context;

	public DBCreator(Context context, String name, CursorFactory factory,
			int version) {
		super(context,
				context.getResources().getString(R.string.database),
				null,
				Integer.parseInt(context.getResources().getString(R.string.version)));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (String sql  : context.getResources().getStringArray(R.array.create)) {
			db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
