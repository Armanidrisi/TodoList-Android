package com.armanidrisi.todolost;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<Todo> mData;
    private LayoutInflater mInflater;
    private TodoDBHelper todoDBHelper;

    TodoAdapter(Context context, List<Todo> data, TodoDBHelper dbHelper) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.todoDBHelper = dbHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo todo = mData.get(position);
        holder.todoText.setText(todo.getTask());
        holder.todoText.setPaintFlags(todo.isCompleted() ? Paint.STRIKE_THRU_TEXT_FLAG : 0);
        holder.todoCheckBox.setChecked(todo.isCompleted()); 
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView todoText;
        CheckBox todoCheckBox;
ImageButton deleteButton;
		
        ViewHolder(View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.todo_text);
            todoCheckBox = itemView.findViewById(R.id.todo_checkbox);
			deleteButton = itemView.findViewById(R.id.delete_button);
			
            // Set an OnClickListener for the checkbox
            todoCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Get the corresponding Todo item
                        Todo todo = mData.get(position);

                        // Toggle the completion status
                        todo.setCompleted(!todo.isCompleted());

                        // Update the checkbox state
                        notifyItemChanged(position);

                        // Update the completion status in the database
                        todoDBHelper.updateTodoItemStatus(todo.getId(), todo.isCompleted());
                    }
                }
            });
			
			// Set an OnClickListener for the Delete button
			deleteButton.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View view){
						int position = getAdapterPosition();
						if(position != RecyclerView.NO_POSITION){
							Todo todo = mData.get(position);
							
							todoDBHelper.deleteTodoItem(todo.getId());	
							
							mData.remove(position);
							
							notifyItemRemoved(position);
						}
					}
				});

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Handle item click here
        }
    }
	
	public void addItem(Todo todo) {
		mData.add(todo);
	}
}
