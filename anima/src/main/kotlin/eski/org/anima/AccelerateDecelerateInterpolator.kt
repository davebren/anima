package eski.org.anima

class AccelerateDecelerateInterpolator: Interpolator {
  companion object constants {
    val instance = AccelerateDecelerateInterpolator()
  }

  override fun apply(tween: Float): Float {
    return (Math.cos((tween + 1) * Math.PI).toFloat() / 2.0f) + 0.5f;
  }
}