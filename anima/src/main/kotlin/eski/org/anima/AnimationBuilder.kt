package eski.org.anima

interface AnimationBuilder {
  fun build(transition: Transition,
            duration: Long,
            interpolator: Interpolator,
            callbacks: List<TransitionCallback>
  ): AnimationInterface
}