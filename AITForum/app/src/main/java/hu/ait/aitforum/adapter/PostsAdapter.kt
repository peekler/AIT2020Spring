package hu.ait.aitforum.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ait.aitforum.R
import hu.ait.aitforum.data.Post
import kotlinx.android.synthetic.main.post_row.view.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    lateinit var context: Context
    var  postsList = mutableListOf<Post>()
    var  postKeys = mutableListOf<String>()

    lateinit var currentUid: String

    constructor(context: Context, uid: String) : super() {
        this.context = context
        this.currentUid = uid
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.post_row, parent, false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var post = postsList.get(holder.adapterPosition)

        holder.tvAuthor.text = post.author
        holder.tvTitle.text = post.title
        holder.tvBody.text = post.body

        if (currentUid == post.uid) {
            holder.btnDelete.visibility = View.VISIBLE
        } else {
            holder.btnDelete.visibility = View.GONE
        }
    }

    fun addPost(post: Post, key: String) {
        postsList.add(post)
        postKeys.add(key)
        notifyDataSetChanged()
    }

    private fun removePost(index: Int) {

    }


    fun removePostByKey(key: String) {

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvAuthor = itemView.tvAuthor
        var tvTitle = itemView.tvTitle
        var tvBody = itemView.tvBody
        var btnDelete = itemView.btnDelete
    }
}