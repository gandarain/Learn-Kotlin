package com.example.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

// class drawing view inherit from class View
class DrawingView(context: Context, attr: AttributeSet): View(context, attr) {
    private var mDrawPath: CustomPath? = null
    private var mCanvasBitmap: Bitmap? = null
    private var mDrawPaint: Paint? = null
    private var mCanvasPaint: Paint? = null
    private var mBrushSize: Float = 0.toFloat()
    private var color: Int = Color.BLACK
    private var canvas: Canvas? = null

    // this variable will be used to store all the path
    // so the line will be persist on the canvas
    private val mPaths: ArrayList<CustomPath> = ArrayList<CustomPath>()

    // undo path
    private val mUndoPath: ArrayList<CustomPath> = ArrayList<CustomPath>()

    init {
        setupDrawing()
    }

    // undo handler
    fun onUndoHandler() {
        // add last index on mPath to mUndoPath
        // remove last index on mPath
        if (mPaths.size > 0) {
            mUndoPath.add(mPaths.removeAt(mPaths.size - 1))
            invalidate()
        }
    }

    // redo handler
    fun onRedoHandler() {
        // add last index on mUndoPath to mPath
        // remove last index on mUndoPath
        if (mUndoPath.size > 0) {
            mPaths.add(mUndoPath.removeAt(mUndoPath.size - 1))
            invalidate()
        }
    }

    private fun setupDrawing() {
        mDrawPath = CustomPath(color, mBrushSize)
        mDrawPaint = Paint()
        mDrawPaint!!.color = color
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap = Paint.Cap.ROUND
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        // no need to set mBrushSize because it will set on the main activity
        // mBrushSize = 20.toFloat()
    }

    // this function will call when view (DrawingView) is displayed
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)
    }

    // Change Canvas into Canvas? if fails
    // When drawing something then update the mDrawPaint
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)

        // show all the available path
        for (path in mPaths) {
            mDrawPaint!!.strokeWidth = path!!.brushThickness
            mDrawPaint!!.color = path!!.color
            canvas.drawPath(path, mDrawPaint!!)
        }

        if (!mDrawPath!!.isEmpty) {
            mDrawPaint!!.strokeWidth = mDrawPath!!.brushThickness
            mDrawPaint!!.color = mDrawPath!!.color
            canvas.drawPath(mDrawPath!!, mDrawPaint!!)
        }
    }

    // When touch the screen
    // When touch on the screen then update the mDrawPath
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when(event?.action) {
            // when press screen
            MotionEvent.ACTION_DOWN -> {
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mBrushSize
                mDrawPath!!.reset()
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.moveTo(touchX, touchY)
                    }
                }
            }
            // when drag over the screen
            MotionEvent.ACTION_MOVE -> {
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.lineTo(touchX, touchY)
                    }
                }
            }
            // when release the touch
            MotionEvent.ACTION_UP -> {
                // store the path into mPaths
                mPaths.add(mDrawPath!!)
                mDrawPath = CustomPath(color, mBrushSize)
            }
            // don't do anything for other motion event
            else -> return false
        }

        // this function coming from View (DrawingView) class
        invalidate()

        return true
    }

    // this function will call on the main activity
    fun setSizeForBrush(newSize: Float) {
        // adjust to size of the screen
        mBrushSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            newSize,
            resources.displayMetrics
        )

        mDrawPaint!!.strokeWidth = mBrushSize
    }

    fun setColor(newColor: String) {
        color = Color.parseColor(newColor)
        mDrawPaint!!.color = color
    }

    // custom path is nested class from drawing view
    // only used on drawing view
    // custom path inherit from class path
    internal inner class CustomPath(var color: Int, var brushThickness: Float): Path() {

    }
}