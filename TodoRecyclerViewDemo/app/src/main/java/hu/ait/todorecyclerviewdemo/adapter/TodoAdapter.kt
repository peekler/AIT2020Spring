package hu.ait.todorecyclerviewdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ait.todorecyclerviewdemo.R
import hu.ait.todorecyclerviewdemo.data.Todo
import hu.ait.todorecyclerviewdemo.touch.TodoTouchHelperCallback
import kotlinx.android.synthetic.main.todo_row.view.*
import java.util.*

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolder>,
    TodoTouchHelperCallback {

    var todoItems = mutableListOf<Todo>(
        Todo("2020. 03. 12.", false, "Todo1"),
        Todo("2020. 03. 11.", false, "Todo2")
    )
    val context: Context

    constructor(context: Context) {
        this.context = context
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
    }

    private fun deleteTodo(position: Int) {
        todoItems.removeAt(position)
        notifyItemRemoved(position)
    }

    public fun addTodo(todo: Todo) {
        todoItems.add(todo)

        //notifyDataSetChanged() // this refreshes the whole list
        notifyItemInserted(todoItems.lastIndex)
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
    }

}
