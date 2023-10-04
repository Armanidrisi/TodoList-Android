package com.armanidrisi.todolost;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;

public class TodoDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TodoList.db";
    private static final int DATABASE_VERSION = 1;

    public TodoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE Todos (_id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, completed INTEGER);");
	}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Todos;");
        onCreate(db);
    }
	
	public List<Todo> getAllTodoItems() {
		List<Todo> todoItems = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM Todos", null);

		if (cursor.moveToFirst()) {
			do {
				long id = cursor.getLong(cursor.getColumnIndex("_id"));
				String task = cursor.getString(cursor.getColumnIndex("task"));
				int completedInt = cursor.getInt(cursor.getColumnIndex("completed"));
				boolean completed = completedInt == 1;

				Todo todoItem = new Todo(task, completed);
				todoItem.setId(id);
				todoItem.setCompleted(completed);

				todoItems.add(todoItem);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return todoItems;
	}
	
	public long insertTodoItem(String task, boolean completed) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("task", task);
		values.put("completed", completed ? 1 : 0); 
		long itemId = db.insert("Todos", null, values);
		db.close();
		return itemId;
	}
	
	public int updateTodoItemStatus(long itemId, boolean completed) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("completed", completed ? 1 : 0);
		int rowsAffected = db.update("Todos", values, "_id=?", new String[]{String.valueOf(itemId)});
		db.close();
		return rowsAffected;
	}
	
	public void deleteTodoItem(long todoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Todos", "_id" +" = ?", new String[]{String.valueOf(todoId)});
        db.close();
    }
}
