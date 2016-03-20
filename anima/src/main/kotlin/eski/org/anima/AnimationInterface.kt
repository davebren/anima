package eski.org.anima

interface AnimationInterface {
  val durationMillis: Long
  var callbacks: List<TransitionCallback>
  val dispatcher: TransitionDispatcher
  val interpolator: Interpolator
  val transition: Transition

  fun applyTween(tween: Float) {
    dispatcher.dispatchTransition(transition, interpolator.apply(tween))
  }
}