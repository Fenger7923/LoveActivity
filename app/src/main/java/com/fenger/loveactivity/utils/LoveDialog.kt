package com.fenger.loveactivity.utils

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.view.View
import com.fenger.loveactivity.LoveActivity
import com.fenger.loveactivity.R
import kotlinx.android.synthetic.main.love_dialog.*
import java.util.*

/**
 * @author Tom.Cai
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 */
class LoveDialog(context: Context) : Dialog(context, R.style.dialog_style2) {
    private var timer: Timer? = null
    private var task: TimerTask? = null
    fun setCustomDialog() {
        val mView = View.inflate(context, R.layout.love_dialog, null)
        playAnimation3(relative_layout1)
        timer = Timer()
        task = object : TimerTask() {
            override fun run() {
                (context as LoveActivity).getResponse(2)
            }
        }
        super.setContentView(mView)
    }

    fun setTime(time: String) {
        tv_time.text = time + ""
    }

    fun playAnimation(view: View?, type: Int) {
        val animators: MutableList<Animator> =
            ArrayList() // 设置一个装动画的集合
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)
        scaleX.duration = 2000 //设置持续时间
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)
        scaleY.duration = 2000 //设置持续时间
        val alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        alpha.duration = 2000 //设置持续时间
        alpha.addUpdateListener {
            //底部列表的translationY
        }
        alpha.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                if (type == 1) playAnimation4(tv_text)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animators.add(scaleX)
        animators.add(scaleY)
        animators.add(alpha)
        val btnSexAnimatorSet = AnimatorSet() //动画集
        btnSexAnimatorSet.playTogether(animators) //设置一起播放
        btnSexAnimatorSet.start() //开始播放
    }

    private fun playAnimation2(view: View?) {
        val animators: MutableList<Animator> =
            ArrayList() //设置一个装动画的集合
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)
        scaleX.duration = 2000 //设置持续时间
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)
        scaleY.duration = 2000 //设置持续时间
        val alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        alpha.duration = 2000 //设置持续时间
        alpha.addUpdateListener {
            //底部列表的translationY
        }
        alpha.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                iv_yes.visibility = View.VISIBLE
                playAnimation(iv_yes, 0)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animators.add(scaleX)
        animators.add(scaleY)
        animators.add(alpha)
        val btnSexAnimatorSet = AnimatorSet() //动画集
        btnSexAnimatorSet.playTogether(animators) //设置一起播放
        btnSexAnimatorSet.start() //开始播放
    }

    private fun playAnimation3(view: View?) {
        val animators: MutableList<Animator> = ArrayList() //设置一个装动画的集合
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f)
        scaleX.duration = 2000 //设置持续时间
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f)
        scaleY.duration = 2000 //设置持续时间
        val alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        alpha.duration = 2000 //设置持续时间

//        alpha.addListener(object : AnimatorListener {
//            override fun onAnimationStart(animation: Animator) {}
//            override fun onAnimationEnd(animation: Animator) {
//                view?.visibility = View.GONE
//                relative_layout2.visibility = View.VISIBLE
//                playAnimation(relative_layout2, 1)
//            }
//
//            override fun onAnimationCancel(animation: Animator) {}
//            override fun onAnimationRepeat(animation: Animator) {}
//        })
        animators.add(scaleX)
        animators.add(scaleY)
        animators.add(alpha)
        val btnSexAnimatorSet = AnimatorSet() //动画集
        btnSexAnimatorSet.playTogether(animators) //设置一起播放
        btnSexAnimatorSet.start() //开始播放
    }

    fun playAnimation4(view: View?) {
        tv_text.visibility = View.VISIBLE
        val animators: MutableList<Animator> =
            ArrayList() //设置一个装动画的集合
        val alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        alpha.duration = 2000 //设置持续时间
        alpha.addUpdateListener {
            //底部列表的translationY
        }
        alpha.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                tv_time.visibility = View.VISIBLE
                timer?.schedule(task, 0, 80)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animators.add(alpha)
        val btnSexAnimatorSet = AnimatorSet() //动画集
        btnSexAnimatorSet.playTogether(animators) //设置一起播放
        btnSexAnimatorSet.start() //开始播放
    }

    override fun show() {
        super.show()
        playAnimation2(rl_dialog)
    }

    override fun dismiss() {
        super.dismiss()
        task?.cancel()
    }
}