package com.collagesimageview.collapseImageView

import android.content.Context
import android.util.AttributeSet
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.collagesimageview.R
import com.collagesimageview.databinding.ActivitySmartImageViewBinding


/**
 * Created by parthi on 04/04/21.
 */

class SmartImageViewLayout : FrameLayout {

    private lateinit var binding: ActivitySmartImageViewBinding

    private var mContext: Context? = null
    private var attrs: AttributeSet? = null
    private var styleAttr: Int? = null
    private var view = View.inflate(context, R.layout.activity_smart_image_view, null)
    private var radius = 0f

    constructor(context: Context) : super(context) {
        init(context, null, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, null)
        binding = ActivitySmartImageViewBinding.inflate(
            LayoutInflater.from(context), this, true
        )
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int?) {
        this.mContext = context
        this.attrs = attrs
        this.styleAttr = defStyleAttr
        addView(view)

        readAttributes()
    }

    private fun readAttributes() {
        val arr =
            mContext?.theme?.obtainStyledAttributes(attrs, R.styleable.SmartImageViewLayout, 0, 0)
        arr?.let {
            if (it.getInt(R.styleable.SmartImageViewLayout_shape, -1) != -1) {
                setShape(Shape.values()[it.getInt(R.styleable.SmartImageViewLayout_shape, -1)])
            }
        }
    }

    fun putImages(vararg urls: String) {
        when (val count = urls.size) {
            1 -> {
                binding.smartImageView1.putImage(urls[0])
                binding.textView.visibility = View.GONE
                binding.linearLayout1.visibility = View.GONE
                binding.frameLayout1.visibility = View.GONE
            }
            2 -> {
                binding.smartImageView1.putImage(urls[0])
                binding.smartImageView2.putImage(urls[1])
                binding.textView.visibility = View.GONE
                binding.linearLayout1.visibility = View.VISIBLE
                binding.frameLayout1.visibility = View.GONE
            }
            3 -> {
                binding.smartImageView1.putImage(urls[0])
                binding.smartImageView2.putImage(urls[1])
                binding.smartImageView3.putImage(urls[2])
                binding.textView.visibility = View.GONE
                binding.linearLayout1.visibility = View.VISIBLE
                binding.frameLayout1.visibility = View.VISIBLE
            }
            else -> {
                binding.smartImageView1.putImage(urls[0])
                binding.smartImageView2.putImage(urls[1])
                binding.smartImageView3.putImage(urls[2])
                binding.textView.text = "+${count - 3}"
                binding.textView.visibility = View.VISIBLE
                binding.linearLayout1.visibility = View.VISIBLE
                binding.frameLayout1.visibility = View.VISIBLE
            }
        }
    }

    private fun setShape(shape: Shape) {
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                binding.cardView.layoutParams.height = view.height
                binding.cardView.layoutParams.width = view.width
                val min = minOf(view.height, view.width)
                radius = min / 2f
                when (shape) {
                    Shape.SQUARE -> binding.cardView.radius = 0f
                    Shape.CIRCLE -> {
                        binding.cardView.radius = radius
                        binding.textView.setPadding(0, 0, min / 20, min / 20)
                    }
                }
                //
                binding.textView.textSize = maxOf(12f, radius / 7f)
            }
        })
        d("shape::: ", "$shape")

    }
}

enum class Shape {
    SQUARE,
    CIRCLE
}