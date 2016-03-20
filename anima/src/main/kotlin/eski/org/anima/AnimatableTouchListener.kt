package eski.org.anima

/**
 * A touch listener that sets itself to a [touchable] [View] through a [TouchAnimatable] it will pass back animation events and transition tweening.
 */
abstract class AnimatableTouchListener(private val animatable: TouchAnimatable, private val cancelIntoClick: Boolean, private val touchSlop: Float) {
  private var touchCancelled: Boolean = false

  protected var canScrollX = false
  protected var canScrollY = false
  private var startTouchTime = 0L
  private var startTouchX = 0f
  private var startTouchY = 0f

  var pointerId: Int? = null

  private var touchableBounds: TouchableBounds? = null

  /** Disables the touch listener so it will no longer receive touch events */
  abstract fun disable()

  /** Enables the touch listener if it had been disabled previously. */
  abstract fun enable()

  protected abstract fun postPressOnDelay(delayMillis: Long)
  protected abstract fun cancelDelayedPress()
  protected abstract fun delayedPressPending(): Boolean

  protected abstract fun calculateTouchableBounds(): TouchableBounds
  protected abstract fun checkIfInScrollingParent()

  protected fun down(pointerId: Int = 0, x: Float, y: Float) {
    touchableBounds = calculateTouchableBounds()
    if (animatable.clicked || this.pointerId != null || !touchableBounds!!.contains(x, y)) return
    checkIfInScrollingParent()

    this.pointerId = pointerId
    startTouchTime = System.currentTimeMillis()

    if(canScrollX || canScrollY) { // Give 64 milliseconds before starting the press animation to see if the user is scrolling.
      postPressOnDelay(64L)
      startTouchX = x
      startTouchY = y
    } else {
      animatable.post(Transition.Type.press)
    }

    touchCancelled = false
  }

  protected fun move(pointerId: Int, x: Float, y: Float) {
    if (this.pointerId != pointerId) return

    if (!touchCancelled && (!touchableBounds!!.contains(x, y) || (delayedPressPending() && isScrolling(x, y)))) {
      this.pointerId = null

      if (!delayedPressPending()) animatable.post(Transition.Type.cancelPress)
      else cancelDelayedPress()
      touchCancelled = true
    }
  }

  protected fun cancel(pointerId: Int) {
    if (this.pointerId != pointerId) return

    if (!touchCancelled) {
      this.pointerId = null

      if (delayedPressPending()) cancelDelayedPress()
      else animatable.post(Transition.Type.cancelPress)
    }

    touchCancelled = true
  }

  protected fun up(pointerId: Int, x: Float, y: Float) {
    if (this.pointerId != pointerId) return

    if (touchableBounds!!.contains(x, y) && !touchCancelled) {
      if (cancelIntoClick) animatable.post(Transition.Type.cancelPress)
      animatable.post(Transition.Type.click)
    } else if (!touchCancelled) {
      animatable.post(Transition.Type.cancelPress)
    }

    this.pointerId = null
    touchCancelled = false
  }

  private fun isScrolling(x: Float, y: Float) = (canScrollX && isScrollingX(x) || canScrollY && isScrollingY(y))
  private fun isScrollingX(x: Float) = Math.abs(x - startTouchX) > touchSlop
  private fun isScrollingY(y: Float) = Math.abs(y - startTouchY) > touchSlop
}
