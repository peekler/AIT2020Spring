package hu.ait.todorecyclerviewdemo

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import hu.ait.todorecyclerviewdemo.adapter.TodoAdapter
import hu.ait.todorecyclerviewdemo.data.AppDatabase
import hu.ait.todorecyclerviewdemo.data.Todo
import hu.ait.todorecyclerviewdemo.touch.TodoReyclerTouchCallback
import kotlinx.android.synthetic.main.activity_scrolling.*
import java.util.*

class ScrollingActivity : AppCompatActivity(), TodoDialog.TodoHandler {

    lateinit var todoAdapter: TodoAdapter

    companion object{
        const val KEY_EDIT = "KEY_EDIT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            // create new item
            showAddTodoDialog()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        Thread {
            var todoList = AppDatabase.getInstance(this).todoDao().getAllTodos()

            runOnUiThread {
                todoAdapter = TodoAdapter(this, todoList)
                recyclerTodo.adapter = todoAdapter

                val touchCallbakList = TodoReyclerTouchCallback(todoAdapter)
                val itemTouchHelper = ItemTouchHelper(touchCallbakList)
                itemTouchHelper.attachToRecyclerView(recyclerTodo)
            }
        }.start()
    }


    fun showAddTodoDialog() {
        TodoDialog().show(supportFragmentManager, "Dialog")
    }

    var editIndex: Int = -1

    public fun showEditTodoDialog(todoToEdit: Todo, index: Int) {
        editIndex = index

        val editItemDialog = TodoDialog()

        val bundle = Bundle()
        bundle.putSerializable(KEY_EDIT, todoToEdit)
        editItemDialog.arguments = bundle

        editItemDialog.show(supportFragmentManager, "EDITDIALOG")
    }


    fun saveTodo(todo: Todo) {
        Thread {
            AppDatabase.getInstance(this).todoDao().insertTodo(todo)

            runOnUiThread {
                todoAdapter.addTodo(todo)
            }
        }.start()
    }

    override fun todoCreated(todo: Todo) {
        saveTodo(todo)
    }

    override fun todoUpdated(todo: Todo) {
        Thread{
            AppDatabase.getInstance(this).todoDao().updateTodo(todo)

            runOnUiThread {
                todoAdapter.updateTodo(todo, editIndex)
            }
        }.start()
    }

}
