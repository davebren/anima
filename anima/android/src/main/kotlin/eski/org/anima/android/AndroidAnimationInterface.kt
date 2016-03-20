package eski.org.anima.android

import android.view.animation.Animation
import android.view.animation.Transformation
import eski.org.anima.*

abstract class AndroidAnimationInterface(): Animation(), AnimationInterface {
  companion object constants {
    val builder: AnimationBuilder = object: AnimationBuilder {
      override fun build(transition: Transition, duration: Long, interpolator: Interpolator, callbacks: List<TransitionCallback>): AnimationInterface {
        val ret = object: AndroidAnimationInterface() {
          override val durationMillis = duration
          override var callbacks = callbacks
          override val dispatcher = TransitionDispatcher(callbacks)
          override val interpolator = interpolator
          override val transition = transition
        }
        ret.duration = duration
        return ret
      }
    }
  }

  override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
    applyTween(interpolatedTime)
  }
}