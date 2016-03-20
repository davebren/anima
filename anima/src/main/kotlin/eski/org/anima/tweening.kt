package eski.org.anima

/*
 * These are extension functions for calculating 'tweened' values.
 *
 * TODO: Make these work for tween values outside of the interval [0, 1]
 */

fun Int.tween(end: Int, t: Float): Int {
  return Math.round(this.toFloat() + (end - this).toFloat()*t)
}

fun Float.tween(end: Float, t: Float): Float {
  return this + (end - this)* t;
}

fun Float.stepTween(endTweenStep: Float): Float {
  return if (this >= endTweenStep) 1f else this / endTweenStep
}

fun Float.breakpointTween(startTween: Float, endTween: Float): Float {
  return Math.min(Math.max((this - startTween) / (endTween - startTween), 0f), 1f)
}