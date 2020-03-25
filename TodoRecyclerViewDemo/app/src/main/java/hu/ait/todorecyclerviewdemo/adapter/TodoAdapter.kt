package hu.ait.todorecyclerviewdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ait.todorecyclerviewdemo.R
import hu.ait.todorecyclerviewdemo.ScrollingActivity
import hu.ait.todorecyclerviewdemo.data.AppDatabase
import hu.ait.todorecyclerviewdemo.data.Todo
import hu.ait.todorecyclerviewdemo.touch.TodoTouchHelperCallback
import kotlinx.android.synthetic.main.todo_row.view.*
import java.lang.Appendable
import java.util.*

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolder>,
    TodoTouchHelperCallback {

    var todoItems = mutableListOf<Todo>()
    val context: Context

    constructor(context: Context, listTodos: List<Todo>) {
        this.context = context
        todoItems.addAll(listTodos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.todo_row, parent, false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTodo = todoItems[position]

        holder.tvDate.text = currentTodo.createDate
        holder.cbDone.text = currentTodo.todoText
        holder.cbDone.isChecked = currentTodo.done

        holder.btnDelete.setOnClickListener {
            deleteTodo(holder.adapterPosition)
        }

        holder.btnEdit.setOnClickListener {
            (context as ScrollingActivity).showEditTodoDialog(
                todoItems[holder.adapterPosition], holder.adapterPosition
            )
        }

        holder.cbDone.setOnClickListener {
            todoItems[holder.adapterPosition].done = holder.cbDone.isChecked
            Thread{
                AppDatabase.getInstance(context).todoDao().updateTodo(todoItems[holder.adapterPosition])
            }.start()
        }
    }

    private fun deleteTodo(position: Int) {
        Thread {
            AppDatabase.getInstance(context).todoDao().deleteTodo(
                todoItems.get(position))

            (context as ScrollingActivity).runOnUiThread {
                todoItems.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()
    }

    public fun addTodo(todo: Todo) {
        todoItems.add(todo)

        //notifyDataSetChanged() // this refreshes the whole list
        notifyItemInserted(todoItems.lastIndex)
    }

    public fun updateTodo(todo: Todo, editIndex: Int) {
        todoItems.set(editIndex, todo)
        notifyItemChanged(editIndex)
    }

    override fun onDismissed(position: Int) {
        deleteTodo(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(todoItems, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate = itemView.tvDate
        val cbDone = itemView.cbDone
        val btnDelete = itemView.btnDelete
        val btnEdit = itemView.btnEdit
    }

}
