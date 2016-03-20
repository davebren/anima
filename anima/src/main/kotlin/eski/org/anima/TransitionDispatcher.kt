package eski.org.anima

class TransitionDispatcher(val callbacks: List<TransitionCallback>) {
  private var started = false
  private var ended = false
  private var startTween = 0f

  @Synchronized fun dispatchTransition(transition: Transition, tween: Float) {
    if (ended) {
      if ((startTween < 1f && tween == 1f) || (startTween > 0f && tween == 0f)) return
    }
    ended = false

    if (!started) {
      startTween = tween
      started = true

      callbacks.forEach {
        it.initializeTransition(transition)
      }
    }

    callbacks.filter(){!it.block(transition)}.forEach {
      it.applyTransformation(transition, tween)
    }

    if ((startTween < 1f && tween == 1f) || (startTween > 0f && tween == 0f)) {
      started = false
      ended = true
      callbacks.forEach {
        it.transitionFinished(transition)
      }
    }
  }
}