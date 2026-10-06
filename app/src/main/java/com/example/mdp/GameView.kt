package com.example.mdp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val bugs = mutableListOf<Bug>()

    private val bitmaps = mutableMapOf<Int, Bitmap>()

    private var lastFrameTime = System.nanoTime()

    private var running = false

    var onBugHit: ((Bug) -> Unit)? = null

    var onMiss: (() -> Unit)? = null


    fun startGame(
        bugCount: Int = 5
    ) {
        bugs.clear()

        repeat(bugCount) {
            bugs.add(createRandomBug())
        }

        lastFrameTime = System.nanoTime()

        running = true

        invalidate()
    }


    fun stopGame() {
        running = false
    }


    private fun createRandomBug(): Bug {

        val type = when (Random.nextInt(10)) {

            in 0..5 -> BugType.NORMAL

            in 6..8 -> BugType.FAST

            else -> BugType.RARE
        }

        return BugFactory.create(type)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!running) {
            return
        }

        updateBugs()

        drawBugs(canvas)

        postInvalidateOnAnimation()
    }


    private fun updateBugs() {

        val currentTime = System.nanoTime()

        val deltaTime =
            (currentTime - lastFrameTime) /
                    1_000_000_000f

        lastFrameTime = currentTime

        bugs.forEach { bug ->

            bug.x +=
                bug.directionX *
                        bug.speed *
                        deltaTime

            bug.y +=
                bug.directionY *
                        bug.speed *
                        deltaTime


            if (bug.x < 0f) {
                bug.x = 0f
                bug.directionX *= -1f
            }

            if (bug.x > 1f) {
                bug.x = 1f
                bug.directionX *= -1f
            }

            if (bug.y < 0f) {
                bug.y = 0f
                bug.directionY *= -1f
            }

            if (bug.y > 1f) {
                bug.y = 1f
                bug.directionY *= -1f
            }
        }
    }


    private fun drawBugs(canvas: Canvas) {

        bugs.forEach { bug ->

            val bitmap =
                getBitmap(bug.imageResource)

            val bugSize =
                bug.size *
                        minOf(width, height)

            val pixelX =
                bug.x * width

            val pixelY =
                bug.y * height

            val left =
                pixelX - bugSize / 2f

            val top =
                pixelY - bugSize / 2f

            val right =
                pixelX + bugSize / 2f

            val bottom =
                pixelY + bugSize / 2f

            val destination =
                RectF(
                    left,
                    top,
                    right,
                    bottom
                )

            canvas.drawBitmap(
                bitmap,
                null,
                destination,
                null
            )
        }
    }


    private fun getBitmap(
        resourceId: Int
    ): Bitmap {

        return bitmaps.getOrPut(resourceId) {

            BitmapFactory.decodeResource(
                resources,
                resourceId
            )
        }
    }


    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        if (
            event.action !=
            MotionEvent.ACTION_DOWN
        ) {
            return true
        }

        if (!running) {
            return true
        }


        val touchX =
            event.x / width.toFloat()

        val touchY =
            event.y / height.toFloat()


        val hitBug =
            bugs.lastOrNull { bug ->

                val halfSize =
                    bug.size / 2f

                touchX >=
                        bug.x - halfSize &&

                        touchX <=
                        bug.x + halfSize &&

                        touchY >=
                        bug.y - halfSize &&

                        touchY <=
                        bug.y + halfSize
            }


        if (hitBug != null) {

            bugs.remove(hitBug)

            onBugHit?.invoke(hitBug)

            bugs.add(
                createRandomBug()
            )

            invalidate()

        } else {

            onMiss?.invoke()
        }


        return true
    }


    override fun onDetachedFromWindow() {

        running = false

        super.onDetachedFromWindow()
    }
}