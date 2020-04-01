package hu.ait.animationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*        btnAnimation.setOnClickListener {
            var pushAnim = AnimationUtils.loadAnimation(this, R.anim.push_anim)

            btnAnimation.startAnimation(pushAnim)
        }*/
        var pushAnim = AnimationUtils.loadAnimation(this, R.anim.push_anim)
        var sendAnim = AnimationUtils.loadAnimation(this, R.anim.send_anim)

        sendAnim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
                Toast.makeText(this@MainActivity, "ANIMATION FINISHED", Toast.LENGTH_LONG).show()
            }
            override fun onAnimationStart(animation: Animation?) {
            }
        })

        btnAnimation.setOnClickListener {
            //layoutMain.startAnimation(pushAnim)
            tvData.startAnimation(sendAnim)
        }
    }
}
