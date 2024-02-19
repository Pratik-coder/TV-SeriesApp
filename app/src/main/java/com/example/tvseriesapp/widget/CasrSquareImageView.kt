package com.example.tvseriesapp.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.example.tvseriesapp.extensions.dpToPx


class CasrSquareImageView:AppCompatImageView {
    constructor(context:Context):super(context)
    constructor(context:Context,attrs:AttributeSet):super(context,attrs)
    constructor(context: Context,attrs: AttributeSet,defStyle:Int):super(context, attrs,defStyle)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width=MeasureSpec.getSize(widthMeasureSpec)
        val height=MeasureSpec.getSize(heightMeasureSpec)

        val desiredWidth=200.dpToPx(context)
        val finalWidthMeasureSpec=MeasureSpec.makeMeasureSpec(desiredWidth,MeasureSpec.EXACTLY)


        val desiredHeight=150.dpToPx(context)
        val finalHeightMeasureSpec=MeasureSpec.makeMeasureSpec(desiredHeight,MeasureSpec.EXACTLY)

        setMeasuredDimension(finalWidthMeasureSpec,finalHeightMeasureSpec)
    }
}