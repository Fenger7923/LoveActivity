package com.fenger.loveactivity

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PointF
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Html
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.fenger.loveactivity.utils.BezierTypeEvaluator
import com.fenger.loveactivity.utils.LoveDialog
import com.fenger.loveactivity.utils.TiaoZiUtil
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_love.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author fengerzhang
 * @date 1/12/21 11:24 AM
 */
class LoveActivity : AppCompatActivity() {

    private var width: Int = 0
    private var height: Int = 0
    var screenWidth = 0
    var screenHeight = 0
    private var isFast = true
    private var mIsDestroyed = false
    var day: Long = 0
    var hour: Long = 0
    var minute: Long = 0
    var second: Long = 0
    private var count = 0
    private var mediaPlayer: MediaPlayer? = null

    private lateinit var loveDialog: LoveDialog


    private lateinit var animationDrawable: AnimationDrawable
    var handlerTime: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 10) {
                ++second
                if (second == 60L) {
                    ++minute
                    second = 0
                }
                if (minute == 60L) {
                    ++hour
                    minute = 0
                }
                if (hour == 24L) {
                    day++
                    hour = 0
                }
                tv_time.text = Html.fromHtml(
                    "<font><small><small><small>" + " 和你的缘分从那一刻开始已经过去了<br/> " + "</small></small></small></font> " + day +
                            " <font><small><small><small>" + "天" + "</small></small></small></font> " + hour +
                            " <font><small><small><small>" + "小时" + "</small></small></small></font> " + minute +
                            " <font><small><small><small>" + "分" + "</small></small></small></font> " + second +
                            " <font><small><small><small>" + "秒" + "</small></small></small></font>"
                )
                val message = this.obtainMessage(0)
                sendMessageDelayed(message, 1000)
            } else if (msg.arg1 == 0) {
                timeDifference("2020-03-27 17:07:00")
                tv_time.text = Html.fromHtml(
                    "<font><small><small>" + "<center>和你的缘分从那一刻开始已经过去了<center/><br/>" + " <center></small></small></font> &nbsp;" + day +
                            " <font><small><small>" + "天" + "</small></small></font> " + hour +
                            " <font><small><small>" + "小时" + "</small></small></font> " + minute +
                            " <font><small><small>" + "分" + "</small></small></font> " + second +
                            " <font><small><small>" + "秒" + "</small></small></font></center>"
                )
                val message = this.obtainMessage(0)
                sendMessageDelayed(message, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_love)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) //应用运行时，保持屏幕高亮，不锁屏
        val wm =
            this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        width = dm.widthPixels // 屏幕宽度（像素）
        height = dm.heightPixels // 屏幕高度（像素）
        val density = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
        val densityDpi = dm.densityDpi // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        screenWidth = (width / density).toInt() // 屏幕宽度(dp)
        screenHeight = (height / density).toInt() // 屏幕高度(dp)

        setClickListener()
    }

    private fun setClickListener() {
        iv_chick_heart.setOnClickListener(View.OnClickListener {
            if (!isFast) return@OnClickListener
            playAnimation(iv_chick_heart)
            playAnimation0(tvPiontMe)
            isFast = false
        })
    }

    //点击后消失动画
    private fun playAnimation(view: View?) {
        val animators: MutableList<Animator> =
            ArrayList() //设置一个装动画的集合
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f)
        scaleX.duration = 2000 //设置持续时间
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f)
        scaleY.duration = 2000 //设置持续时间
        val alpha = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f)
        alpha.duration = 2000 //设置持续时间
        alpha.addUpdateListener {
            //底部列表的
        }
        alpha.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                relativeLayout.removeView(iv_chick_heart)
                iv_heart.visibility = View.VISIBLE
                playAnimation2(iv_heart)
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

    //第一个消失动画
    private fun playAnimation0(view: View?) {
        val animators: MutableList<Animator> =
            ArrayList() //设置一个装动画的集合
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f)
        scaleX.duration = 500 //设置持续时间
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f)
        scaleY.duration = 500 //设置持续时间
        val alpha = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f)
        alpha.duration = 500 //设置持续时间
        alpha.addUpdateListener {
            //底部列表的
        }
        alpha.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {}
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

    //小爱心出现动画
    fun playAnimation2(view: View?) {
        val animators: MutableList<Animator> =
            ArrayList() //设置一个装动画的集合
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0f, 0.3f)
        scaleX.duration = 1000 //设置持续时间
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0f, 0.3f)
        scaleY.duration = 1000 //设置持续时间
        val alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        alpha.duration = 1000 //设置持续时间
        alpha.addUpdateListener {
            //底部列表的translationY
        }
        alpha.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                playAnimation3(iv_heart)
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

    //小小爱心下落动画
    fun playAnimation3(view: View?) {
        val animators: MutableList<Animator> =
            ArrayList() //设置一个装动画的集合
        val translationY =
            ObjectAnimator.ofFloat(view, "translationY", 0f, screenHeight * 1.7f)
        translationY.duration = 4000 //设置持续时间
        translationY.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                playAnimation4(v_left, -screenWidth * 2f)
                playAnimation4(v_right, screenWidth * 2f)
            }

            override fun onAnimationEnd(animation: Animator) {
                relativeLayout.removeView(iv_heart)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animators.add(translationY)
        val btnSexAnimatorSet = AnimatorSet() //动画集
        btnSexAnimatorSet.playTogether(animators) //设置一起播放
        btnSexAnimatorSet.start() //开始播放
    }

    //线条出现动画
    fun playAnimation4(view: View?, value: Float) {
        val animators: MutableList<Animator> =
            ArrayList() //设置一个装动画的集合
        val translationX = ObjectAnimator.ofFloat(view, "translationX", 0f, value)
        translationX.duration = 4000 //设置持续时间
        translationX.addUpdateListener {
            //底部列表的translationY
        }
        translationX.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                playAnimation5(iv_tree)
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animators.add(translationX)
        val btnSexAnimatorSet = AnimatorSet() //动画集
        btnSexAnimatorSet.playTogether(animators) //设置一起播放
        btnSexAnimatorSet.start() //开始播放
    }

    //爱心树生长动画
    fun playAnimation5(view: ImageView) {
        view.setImageDrawable(getDrawable(R.drawable.frame_anim))
        view.visibility = View.VISIBLE

        //通过设置android:src时，得到AnimationDrawable 用如下方法
        animationDrawable = view.drawable as AnimationDrawable
        animationDrawable.start()
        var duration = 0
        for (i in 0 until animationDrawable.getNumberOfFrames()) {
            duration += animationDrawable.getDuration(i)
        }
        val handler = Handler()
        handler.postDelayed({ playAnimation6(rl_tree) }, duration.toLong())
    }

    //爱心树移动动画
    private fun playAnimation6(view: View?) {
        val animators: MutableList<Animator> =
            ArrayList() //设置一个装动画的集合
        val translationX =
            ObjectAnimator.ofFloat(view, "translationX", 0f, screenWidth - 300.toFloat())
        translationX.duration = 2000 //设置持续时间
        translationX.addUpdateListener {
            //底部列表的translationY
        }
        translationX.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                val text1 = "To 小云："
                val text2 =
                    "        小云，节日快乐啊！我在soul的人海中遇到了你，我没有那么多的期盼，没有那么多的想法，只是想爱一个爱我的人。我爱的人，是你啊。" +
                            "自从认识你，每天都会有你的问候，你的声音，你的想法。虽然，每天我们都在聊的可能只是吃了吗，睡了吗。但，听你分享你的故事，感受你的音容笑貌，却是我每天最期待的事。前段时间你忙起来了，我发现自己总在忙碌中打开微信，" +
                            "只是想看看有没有你的消息。就这样被你征服。虽然你已经是我的女朋友很多天啦，可是我却还没有向你正式表白过呀。所以。。。"
                val text3 = "— —Fenger"
                val tiaoZiUtil = TiaoZiUtil()
                tiaoZiUtil.text1 = text1
                tiaoZiUtil.textView1 = tv_to
                tiaoZiUtil.text2 = text2
                tiaoZiUtil.textView2 = tv_context
                tiaoZiUtil.text3 = text3
                tiaoZiUtil.textView3 = tv_bottom
                tiaoZiUtil.start(0, this@LoveActivity)
                playAnimation7(tv_time)
                playAnimation8()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animators.add(translationX)
        val btnSexAnimatorSet = AnimatorSet() //动画集
        btnSexAnimatorSet.playTogether(animators) //设置一起播放
        btnSexAnimatorSet.start() //开始播放
    }

    //时间标语出现动画
    fun playAnimation7(view: View?) {
        tv_time.visibility = View.VISIBLE
        val animators: MutableList<Animator> =
            ArrayList() //设置一个装动画的集合
        val translationX =
            ObjectAnimator.ofFloat(view, "translationX", -screenWidth * 2f, 1f)
        translationX.duration = 4000 //设置持续时间
        translationX.addUpdateListener {
            //底部列表的translationY
        }
        translationX.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                val message = Message()
                message.arg1 = 0
                handlerTime.sendMessage(message)
            }

            override fun onAnimationEnd(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animators.add(translationX)
        val btnSexAnimatorSet = AnimatorSet() //动画集
        btnSexAnimatorSet.playTogether(animators) //设置一起播放
        btnSexAnimatorSet.start() //开始播放
    }

    //树叶飘落动画
    fun playAnimation8() {
        object : Thread() {
            override fun run() {
                if (mIsDestroyed) // 页面销毁直接返回
                    return
                while (true) {
                    if (mIsDestroyed) // 页面销毁直接返回
                        return
                    runOnUiThread { addLeft() }
                    try {
                        sleep(3000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }.start()
    }

    private fun addLeft() {
        try {
            // 四张不同形状的叶子
            val mLeafs =
                arrayOf(
                    getDrawable(R.drawable.leaf1),
                    getDrawable(R.drawable.leaf2),
                    getDrawable(R.drawable.leaf3),
                    getDrawable(R.drawable.leaf4),
                    getDrawable(R.drawable.leaf5),
                    getDrawable(R.drawable.leaf6)
                )

            // 四个不同的补间器
            val mInterpolator =
                arrayOf<Interpolator>(
                    AccelerateDecelerateInterpolator(),
                    AccelerateInterpolator(),
                    DecelerateInterpolator(),
                    LinearInterpolator()
                )
            val mLeaf = ImageView(this@LoveActivity)
            val random = Random()
            mLeaf.setImageDrawable(mLeafs[random.nextInt(6)])
            val leafX = random.nextInt(screenWidth / 3) + screenWidth * 1.8f
            val leafY: Float
            leafY = if (leafX > screenWidth / 2) {
                screenHeight * 1.0f / screenWidth * leafX - screenHeight * 1.8f
            } else {
                -screenHeight * 1.0f / screenWidth * leafX + screenHeight * 1.8f
            }

            // 设置落叶起点，添加到布局
            mLeaf.x = random.nextInt(screenWidth) + screenWidth * 1.7f
            mLeaf.y = random.nextInt(screenHeight) + screenHeight / 8.toFloat()
            rlLefts.addView(mLeaf)

            // 设置树叶刚开始出现的动画
            val alpha = ObjectAnimator.ofFloat(mLeaf, "alpha", 0f, 1f)
            val scaleX = ObjectAnimator.ofFloat(mLeaf, "scaleX", 0f, 0.3f)
            val scaleY = ObjectAnimator.ofFloat(mLeaf, "scaleY", 0f, 0.3f)
            val rotation = ObjectAnimator.ofFloat(
                mLeaf,
                "rotation",
                random.nextInt(90).toFloat(),
                random.nextInt(360).toFloat()
            )
            val set = AnimatorSet()
            set.playTogether(alpha, scaleX, scaleY, rotation)
            set.duration = 1000

            // 树叶落下的起点
            val pointF0 = PointF(mLeaf.x, mLeaf.y)

            // 树叶落下经过的第二个点
            val pointF1 = PointF(
                (screenWidth + random.nextInt((screenWidth * 1.0).toInt() + 1)).toFloat(),
                leafY * 2 + random.nextInt((leafY * 1.5).toInt() + 1)
            )

            // 树叶落下经过的第三个点
            val pointF2 = PointF(
                (screenWidth + random.nextInt((screenWidth * 1.0).toInt() + 1)).toFloat(),
                leafY * 3 + random.nextInt((leafY * 2.5).toInt() + 1)
            )

            // 树叶落下的终点
            val pointF3 = PointF(
                random.nextInt(screenWidth).toFloat(),
                (screenHeight * 3).toFloat()
            )

            // 通过自定义的贝塞尔估值器算出途经的点的想x，y坐标
            val bazierTypeEvaluator =
                BezierTypeEvaluator(
                    pointF1,
                    pointF2
                )

            // 设置值动画
            val bazierAnimator =
                ValueAnimator.ofObject(bazierTypeEvaluator, pointF0, pointF3)
            bazierAnimator.setTarget(mLeaf)
            bazierAnimator.addUpdateListener { animation ->
                val pointF = animation.animatedValue as PointF
                ViewCompat.setX(mLeaf, pointF.x)
                ViewCompat.setY(mLeaf, pointF.y)
                ViewCompat.setAlpha(mLeaf, 1 - animation.animatedFraction / 2)
            }
            bazierAnimator.duration = 18000

            // 将以上动画添加到动画集合
            val allSet = AnimatorSet()
            allSet.play(set).before(bazierAnimator)

            // 随机设置一个补间器
            allSet.interpolator = mInterpolator[random.nextInt(4)]
            allSet.addListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    rlLefts.removeView(mLeaf)
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
            allSet.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //计算时间差
    fun timeDifference(startDate: String?) {
        try {
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val startTime = df.parse(startDate)
            val endTime = df.parse(df.format(Date()))
            val diff = endTime.time - startTime.time
            day = diff / (1000 * 60 * 60 * 24)
            hour = (diff - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
            minute =
                (diff - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60)
            second =
                (diff - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000
        } catch (e: java.lang.Exception) {
            Log.e("fenger", "error")
        }
    }

    override fun onResume() {
        super.onResume()
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music)
            mediaPlayer!!.isLooping = false
            mediaPlayer!!.seekTo(30000)
            mediaPlayer!!.start()
        } else {
            if (mediaPlayer!!.duration != mediaPlayer!!.currentPosition) {
                mediaPlayer!!.seekTo(mediaPlayer!!.currentPosition)
                mediaPlayer!!.start()
            }
        }
        mediaPlayer!!.setOnCompletionListener {
            if (count > 550) finish() else {
                if (!mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.seekTo(30000)
                    mediaPlayer!!.start()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mIsDestroyed = true
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer = null
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            val toast: Toast = TastyToast.makeText(
                this@LoveActivity,
                "一定要听完这首歌好不好？\n          ╥﹏╥",
                TastyToast.LENGTH_LONG,
                TastyToast.ERROR
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        return false
    }

    //弹出弹窗
    private fun showDialog() {
        loveDialog = LoveDialog(this@LoveActivity)
        loveDialog.setCustomDialog()
        loveDialog.setCanceledOnTouchOutside(false)
        loveDialog.setOnKeyListener { _, keyCode, event ->
            keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0
        }
        val params: WindowManager.LayoutParams? = loveDialog.window?.attributes
        loveDialog.window?.setGravity(Gravity.CENTER)
        params?.width = (screenWidth * 2)
        params?.height = (screenHeight * 2.5).toInt()
        loveDialog.window?.attributes = params
        loveDialog.show()
    }

    fun getResponse(value: Int) {
        if (value == 1) {
            showDialog()
        } else if (value == 2) {
            count++
            when {
                count == 550 -> loveDialog.setTime("笔芯")
                count == 580 -> loveDialog.setTime("白白")
                count <= 520 -> loveDialog.setTime(count.toString() + "")
            }
            if (count > 620) {
                if (loveDialog.isShowing) {
                    loveDialog.dismiss()
                    vBackground.visibility = View.GONE
                    val toast = TastyToast.makeText(
                        this@LoveActivity,
                        "陪我听完这首歌好不好？",
                        TastyToast.LENGTH_LONG,
                        TastyToast.WARNING
                    )
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            }
        }
    }
}