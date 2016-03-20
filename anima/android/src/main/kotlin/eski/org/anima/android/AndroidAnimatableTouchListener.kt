package eski.org.anima.android

import android.support.v4.view.ScrollingView
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import eski.org.anima.AnimatableTouchListener
import eski.org.anima.TouchAnimatable
import eski.org.anima.TouchableBounds
import eski.org.anima.Transition

/**
 * A touch listener that sets itself to a [touchable] [View] through a [TouchAnimatable] it will pass back animation events and transition tweening.
 */
open class AndroidAnimatableTouchListener(touchable: View, private val animatable: TouchAnimatable, private val cancelIntoClick: Boolean)
: AnimatableTouchListener(animatable, cancelIntoClick, ViewConfiguration.get(touchable.context).scaledTouchSlop.toFloat()), View.OnTouchListener {

  override fun postPressOnDelay(delayMillis: Long) {
    pressRunnableIsWaiting = true
    touchable.postDelayed(pressRunnable, delayMillis)
  }

  override fun cancelDelayedPress() {
    touchable.removeCallbacks(pressRunnable)
    pressRunnableIsWaiting = false
  }

  protected val pressRunnable = Runnable {
    pressRunnableIsWaiting = false
    animatable.post(Transition.Type.press)
  }

  override fun delayedPressPending() = pressRunnableIsWaiting
  protected var pressRunnableIsWaiting = false

  override fun calculateTouchableBounds(): TouchableBounds {
    return TouchableBounds(0f, 0f, touchable.width.toFloat(), touchable.height.toFloat())
  }

  var touchable: View = touchable; get() = field
    set(value) {
      field = value
      field.setOnTouchListener(this)
    }

  init {
    enable()
  }

  /** Disables the touch listener so it will no longer receive touch events */
  override fun disable() {
    touchable.setOnTouchListener(null)
  }

  /** Enables the touch listener if it had been disabled previously. */
  override fun enable() {
    touchable.setOnTouchListener(this)
  }

  override fun onTouch(targetView: View, event: MotionEvent): Boolean {
    when (event.action) {
      MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
        down(event.getPointerId(event.actionIndex), event.x, event.y)
      }
      MotionEvent.ACTION_CANCEL -> {
        cancel(event.getPointerId(event.actionIndex))
      }
      MotionEvent.ACTION_MOVE -> {
        move(event.getPointerId(event.actionIndex), event.x, event.y)
      }
      MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
        up(event.getPointerId(event.actionIndex), event.x, event.y)
      }
    }
    return true
  }

  override fun checkIfInScrollingParent() {
    var scrollingViewClassAvailable = false
    try {
      Class.forName("android.support.v4.view.ScrollingView")
      scrollingViewClassAvailable = true
    } catch(e: ClassNotFoundException) {}


    var parent = touchable.parent
    while (parent != null) {
      if (scrollingViewClassAvailable && parent is ScrollingView) {
        canScrollX = true
        canScrollY = true
        break
      } else if (parent is ScrollView) {
        canScrollY = true
        break
      } else if (parent is HorizontalScrollView) {
        canScrollX = true
        break
      } else if (parent is ListView) {
        canScrollY = true
        break
      } else if (scrollingViewClassAvailable && parent is ScrollingView){

      }

      parent = parent.parent
    }
  }
}
