package eski.org.anima

/**
 * An interface to implement transition animations.
 */
open class TransitionCallback {
  /**
   * This is invoked before a transition first starts.
   * @param transition The transition to initialize. Check [Transition.owner] and [Transition.type] to determine how to handle the initialization.
   */
  open fun initializeTransition(transition: Transition) {}

  /**
   * Apply any transformations to the view and view state here.
   *
   * @param
   */
  open fun applyTransformation(transition: Transition, tween: Float) {}

  open fun transitionFinished(transition: Transition) {}

  /**
   * To avoid receiving callbacks from a specific transition you can return false here before the animation starts.
   *
   * @param transition The transition to be blocked or not.
   */
  open fun block(transition: Transition): Boolean { return false }
}