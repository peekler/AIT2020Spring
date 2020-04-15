package hu.ait.aitforum

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import hu.ait.aitforum.adapter.PostsAdapter
import hu.ait.aitforum.data.Post
import kotlinx.android.synthetic.main.activity_forum.*

class ForumActivity : AppCompatActivity() {

    private lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startActivity(Intent(this, CreatePostActivity::class.java))
        }

        postsAdapter = PostsAdapter(this,
            FirebaseAuth.getInstance().currentUser!!.uid)
        recyclerPosts.adapter = postsAdapter

        initPosts()
    }

    fun initPosts() {
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("posts")

        query.addSnapshotListener(
            object : EventListener<QuerySnapshot> {

                override fun onEvent(querySnapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
                    if (e!=null) {
                        Toast.makeText(this@ForumActivity, "Error: ${e.message}",
                            Toast.LENGTH_LONG).show()
                        return
                    }

                    for (docChange in querySnapshot?.getDocumentChanges()!!) {
                        if (docChange.type == DocumentChange.Type.ADDED) {
                            val post = docChange.document.toObject(Post::class.java)
                            postsAdapter.addPost(post, docChange.document.id)
                        } else if (docChange.type == DocumentChange.Type.REMOVED) {
                            postsAdapter.removePostByKey(docChange.document.id)
                        } else if (docChange.type == DocumentChange.Type.MODIFIED) {

                        }
                    }

                }
            }
        )
    }

}
