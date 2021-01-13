package com.fenger.loveactivity.utils

import android.content.Context
import android.widget.TextView
import com.fenger.loveactivity.LoveActivity

class TiaoZiUtil3() {
    lateinit var textView1: TextView
    lateinit var context1: String
    private var length1 = 0
    private var nn = 0

    fun start(n: Int, context: Context) {
        length1 = context1.length
        Thread(
            Runnable {
                try {
                    val stv = context1.substring(0, n) //截取要填充的字符串
                    textView1.post { textView1.text = stv }
                    Thread.sleep(200) //休息片刻
                    nn = n + 1 //n+1；多截取一个
                    if (nn <= length1) { //如果还有汉字，那么继续开启线程，相当于递归的感觉
                        start(nn, context)
                    } else {
                        if ((textView1.text.toString() + "").length == context1.length) {
//                            (context as LoveActivity).getResponse(1)
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        ).start()
    }
}