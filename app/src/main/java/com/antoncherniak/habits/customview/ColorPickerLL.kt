package com.antoncherniak.habits.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateMargins
import com.antoncherniak.habits.R
import kotlin.math.roundToInt


class ColorPickerLL @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        private const val ITEM_COLOR_COUNT = 16
    }

    var listener: ((color: ColorStateList?)->Unit)? = null

    init {
        this.orientation = HORIZONTAL
        background = BitmapDrawable(resources, createBackgroundBitmap(context))
        createColorPickerItems(context){
            listener?.invoke(it.backgroundTintList)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    private fun createBackgroundBitmap(context: Context): Bitmap? {
        val bitmap = Bitmap.createBitmap(getWidth(context).roundToInt(), 1, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawRect(RectF(0f, 0f, getWidth(context), 1f), getBackgroundDrawable(context))
        return bitmap
    }

    private fun buildHueColorArray(): IntArray {
        val hue = IntArray(360)
        var i = 0
        while (i < hue.size) {
            hue[i] = Color.HSVToColor(floatArrayOf(i.toFloat(), 1f, 1f))
            i++
        }
        return hue
    }

    private fun getWidth(context: Context): Float {
        return ITEM_COLOR_COUNT * context.resources.getDimension(R.dimen.item_color_picker_size) +
                (ITEM_COLOR_COUNT * 2) * context.resources.getDimension(R.dimen.color_picker_item_space_size)
    }

    private fun getBackgroundDrawable(context: Context): Paint {
        val gradient = LinearGradient(
            0f,
            0f,
            getWidth(context),
            0f,
            buildHueColorArray(),
            null,
            Shader.TileMode.CLAMP
        )
        return Paint().apply {
            isDither = true
            shader = gradient
        }
    }

    private fun createColorPickerItems(context: Context, colorItemClick: (View) -> Unit) {
        val bitmap = createBackgroundBitmap(context)
        val marginSpace =
            context.resources.getDimension(R.dimen.color_picker_item_space_size).roundToInt()
        val itemColorSize =
            context.resources.getDimension(R.dimen.item_color_picker_size).roundToInt()

        val startPosition = marginSpace + 0.5 * itemColorSize
        val step = itemColorSize + 2 * marginSpace

        var acc = startPosition
        for (item in 0 until ITEM_COLOR_COUNT) {
            val pixel = bitmap?.getPixel(acc.roundToInt(), 0)
            acc += step

            val colorItemView = View(context).apply {
                layoutParams = MarginLayoutParams(
                    itemColorSize,
                    itemColorSize
                ).apply {
                    updateMargins(
                        right = marginSpace,
                        left = marginSpace
                    )
                }
                isClickable = true
                background =
                    ContextCompat.getDrawable(this.context, R.drawable.selected_color_background)
                foreground = ContextCompat.getDrawable(
                    this.context,
                    R.drawable.selected_color_foreground
                )
                backgroundTintList = pixel?.let { ColorStateList.valueOf(it) }
                this.setOnClickListener(colorItemClick)
            }

            this.addView(colorItemView)
        }
    }
}