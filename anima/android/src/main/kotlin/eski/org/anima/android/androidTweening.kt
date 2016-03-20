package eski.org.anima.android

import android.graphics.Color
import eski.org.anima.tween

/*
 * These are extension functions for calculating 'tweened' values.
 *
 * TODO: Make these work for tween values outside of the interval [0, 1]
 */

fun Int.tweenRgb(end: Int, t: Float): Int {
  val red = Color.red(this).tween(Color.red(end), t)
  val green = Color.green(this).tween(Color.green(end), t)
  val blue = Color.blue(this).tween(Color.blue(end), t)
  val alpha = Color.alpha(this).tween(Color.alpha(end), t)
  return Color.argb(alpha, red, green, blue)
}

fun Int.tweenFade(t: Float) = Color.argb(
    0.tween(255, t),
    Color.red(this),
    Color.green(this),
    Color.blue(this)
)