package eski.org.anima

class TouchableBounds(val left: Float, val top: Float, val right: Float, val bottom: Float) {
  fun contains(x: Float, y: Float) = left <= x && right >= x && top <= y && bottom >= y
}

