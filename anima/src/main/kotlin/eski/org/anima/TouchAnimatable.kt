package eski.org.anima

import java.util.*

class TouchAnimatable(
    protected val targetView: AnimatableView,
    protected val animations: AnimationGroup,
    protected val backStackable: Boolean = false,
    protected val multipleClicksAllowed: Boolean = false,
    protected val transitionCallbacks: MutableList<TransitionCallback>) {

  private val tweenQueue: Queue<Transition.Type> = LinkedList()
  var clicked: Boolean = false
  private var currentTween: Transition.Type? = null

  protected val backPressedCallback: Function0<Void?> = object: Function0<Void?> {
    override fun invoke(): Void? {
      //if (this@TouchAnimatable is BackButton.BackTouchAnimatable) log("BackButton.super.backPressed.invoke")
      post(Transition.Type.back)
      return null
    }
  }

  init {
    transitionCallbacks.add(object: TransitionCallback() {
      override fun transitionFinished(transition: Transition) {
        synchronized(this@TouchAnimatable) {
          if (currentTween == Transition.Type.back) clicked = false
          if (currentTween == Transition.Type.click && multipleClicksAllowed) clicked = false
          currentTween = null
          executeFromQueue()
        }
      }
    })

    //Backstack.replace(callbackGroup.backstackId, backPressedCallback)
  }

  @Synchronized fun post(transitionType: Transition.Type) {
    //if (this is BackButton.BackTouchAnimatable) log("BackButton.super.post: " + tween)
    if (transitionType != Transition.Type.back && clicked) return;
    if (transitionType == Transition.Type.click) {
      clicked = true
      //if (backStackable) Backstack.push(callbackGroup.backstackId, backPressedCallback)
    }
    if (currentTween != null) tweenQueue.add(transitionType) else currentTween = executeTween(transitionType)
  }

  private @Synchronized fun executeFromQueue() {
    if (!tweenQueue.isEmpty()) {
      currentTween = tweenQueue.poll()
      executeTween(currentTween)
    }
  }

  fun executeTween(tween: Transition.Type?): Transition.Type? {
    targetView.clearAnimation()
    targetView.startAnimation(when(tween) {
      Transition.Type.click -> animations.click
      Transition.Type.back -> animations.reverseClick
      Transition.Type.press -> animations.press
      Transition.Type.cancelPress -> animations.pressCancel
      else -> animations.press
    })
    return tween
  }
}

