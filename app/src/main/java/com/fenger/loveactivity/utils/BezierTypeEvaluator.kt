package com.fenger.loveactivity.utils

import android.animation.TypeEvaluator
import android.graphics.PointF

class BezierTypeEvaluator(
    /**
     * 三次方贝塞尔曲线
     * B(t)=P0*(1-t)^3+3*P1*t*(1-t)^2+3*P2*t^2*(1-t)+P3*t^3,t∈[0,1]
     * P0,是我们的起点,
     * P3是终点,
     * P1,P2是途径的两个点
     * 而t则是我们的一个因子,取值范围是0-1
     */
    private val pointF1: PointF, private val pointF2: PointF
) :
    TypeEvaluator<PointF> {
    override fun evaluate(t: Float, startValue: PointF, endValue: PointF): PointF {
        val pointF = PointF()
        pointF.x = (startValue.x * Math.pow(
            1 - t.toDouble(),
            3.0
        ) + 3 * pointF1.x * t * Math.pow(
            1 - t.toDouble(),
            2.0
        ) + 3 * pointF2.x * Math.pow(
            t.toDouble(),
            2.0
        ) * (1 - t) + endValue.x * Math.pow(t.toDouble(), 3.0)).toFloat()
        pointF.y = (startValue.y * Math.pow(
            1 - t.toDouble(),
            3.0
        ) + 3 * pointF1.y * t * Math.pow(
            1 - t.toDouble(),
            2.0
        ) + 3 * pointF2.y * Math.pow(
            t.toDouble(),
            2.0
        ) * (1 - t) + endValue.y * Math.pow(t.toDouble(), 3.0)).toFloat()
        return pointF
    }

}