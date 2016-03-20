package eski.org.anima

/**
 * Basic animation group containing down, cancel, click, and reverseClick [AndroidAnimationInterface].
 */
abstract class AnimationGroup() {
  abstract val press: AnimationInterface
  abstract val pressCancel: AnimationInterface
  abstract val click: AnimationInterface
  abstract val reverseClick: AnimationInterface
}