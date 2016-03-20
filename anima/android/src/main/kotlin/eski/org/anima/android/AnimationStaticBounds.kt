package eski.org.anima.android

abstract class AnimationStaticBounds : AndroidAnimationInterface() {
  override fun willChangeBounds(): Boolean {
    return false
  }
}