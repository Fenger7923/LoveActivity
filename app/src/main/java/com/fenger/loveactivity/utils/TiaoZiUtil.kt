package com.fenger.loveactivity.utils

import android.content.Context
import android.widget.TextView

class TiaoZiUtil {
    var text1: String = ""
    var text2: String = ""
    var text3: String = ""
    lateinit var textView1: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView
    private var nn = 0
    private var length1 = 0

    fun start(n: Int, context: Context?) {
        length1 = text1.length
        Thread(
            Runnable {
                try {
                    val stv: String = text1.substring(0, n) //截取要填充的字符串
                    textView1.post(Runnable { textView1.text = stv })
                    Thread.sleep(300) //休息片刻
                    nn = n + 1  // n+1；多截取一个
                    if (nn <= length1) { //如果还有汉字，那么继续开启线程，相当于递归的感觉
                        start(nn, context)
                    } else {
                        if ((textView1.text.toString() + "").length == text1.length) {
                            val tiaoZiUtil2 = TiaoZiUtil2()
                            tiaoZiUtil2.context1 = text2
                            tiaoZiUtil2.textView1 = textView2
                            tiaoZiUtil2.context2 = text3
                            tiaoZiUtil2.textView2 = textView3
                            tiaoZiUtil2.start(0, context!!)
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        ).start()
    }
}

