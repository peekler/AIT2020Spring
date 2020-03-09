package hu.ait.layoutinflaterdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_row.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSaveTodo.setOnClickListener {
            createTodo()
        }
    }

    private fun createTodo() {
        var todoRow = layoutInflater.inflate(R.layout.todo_row,
            null, false)
        todoRow.tvTodo.text = etTodo.text.toString()

        todoRow.btnDelete.setOnClickListener {
            layoutContent.removeView(todoRow)
        }

        layoutContent.addView(todoRow, 0)

        etTodo.setText("")
    }
}
