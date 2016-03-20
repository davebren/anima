package eski.org.anima

/**
 * This class holds the animation configurations for each transition type.
 *
 * @param callbacks  List of [TransitionCallback]s that will be receiving the animations. The container object is used so that multiple listeners can be synchronized.
 * @param animationBuilder Builder interface for creating [AnimationInterface]s that can be platform dependent.
 *
 */
open class TouchAnimations(val callbacks: List<TransitionCallback>, val animationBuilder: AnimationBuilder) : AnimationGroup() {
  /** Default [Transition] values for easy use of [TouchAnimations] when there is no need to differentiate between where the transition is coming from in the same callback. */
  companion object constants {
     /** The default owner string to use with the default transitions. **/
    val defaultOwner: String by lazy { TouchAnimations::class.java.simpleName }

    /** Singleton instance representing that there is no active transition. */
    val defaultNoneTransition: Transition by lazy { Transition(defaultOwner, Transition.Type.none) }

    /** Singleton instance representing the down transition (when the view is in the pressed state). */
    val defaultPressTransition: Transition by lazy { Transition(defaultOwner, Transition.Type.press) }

    /** Singleton instance representing the cancel transition (when the view is leaving the pressed state because the user has slid their finger off of the view). */
    val defaultPressCancelTransition: Transition by lazy { Transition(defaultOwner, Transition.Type.cancelPress) }

    /** Singleton instance representing the click transition. This is used for the click transition of both checked states [CheckState.unchecked], and [CheckState.checked]. */
    val defaultClickTransition: Transition by lazy { Transition(defaultOwner, Transition.Type.click) }

    /** Singleton instance representing the reverse transition. This is a special transition type for use with a backstack implementation. */
    val defaultBackTransition: Transition by lazy { Transition(defaultOwner, Transition.Type.back) }
  }

  /** overridable [Transition] instance representing that there is no active transition. */
  open val noneTransition: Transition by lazy { defaultNoneTransition }

  open val pressTransition: Transition by lazy { defaultPressTransition }

  open val pressCancelTransition: Transition by lazy { defaultPressCancelTransition }

  open val clickTransition: Transition by lazy { defaultClickTransition }

  open val backTransition: Transition by lazy { defaultBackTransition }

  override val press = animationBuilder.build(pressTransition, 125, AccelerateDecelerateInterpolator.instance, callbacks)
  override val pressCancel = animationBuilder.build(pressCancelTransition, 125, AccelerateDecelerateInterpolator.instance, callbacks)
  override val click = animationBuilder.build(clickTransition, 250, AccelerateDecelerateInterpolator.instance, callbacks)
  override val reverseClick = animationBuilder.build(backTransition, 250, AccelerateDecelerateInterpolator.instance, callbacks)
}