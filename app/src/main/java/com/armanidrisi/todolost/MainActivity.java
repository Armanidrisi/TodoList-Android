package com.armanidrisi.todolost;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import java.util.ArrayList;
import com.armanidrisi.todolost.TodoAdapter;
import com.armanidrisi.todolost.TodoDBHelper;
import java.util.List;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity { 
	TodoDBHelper todoDBHelper;
	RecyclerView recyclerView;
	ImageButton addButton;
	EditText addEditText;
	TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimary));

		todoDBHelper = new TodoDBHelper(this);
		recyclerView = findViewById(R.id.recycler);
		addButton = findViewById(R.id.add_button);
		addEditText = findViewById(R.id.add_edittext);

		List<Todo> todos = todoDBHelper.getAllTodoItems();

		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		todoAdapter =  new TodoAdapter(this, todos, todoDBHelper);
		recyclerView.setAdapter(todoAdapter);

		addButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					String text = addEditText.getText().toString();

					if (!text.isEmpty()) {
						long itemId = todoDBHelper.insertTodoItem(text, false);

						if (itemId != -1) {
							Todo newTodo = new Todo(text, false);
							todoAdapter.addItem(newTodo);

							int position = todoAdapter.getItemCount() - 1;
							todoAdapter.notifyItemInserted(position);

							Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();
							addEditText.setText("");
						} else {
							Toast.makeText(getApplicationContext(), "Failed to add item", Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "Please enter something....", Toast.LENGTH_SHORT).show();
					}
				}
			});
    }
}
