package eski.org.anima

/**
 * Representation of a transition.
 *
 * @param owner Represents the owner of the transition. "Where" the transition is coming from.
 * @pram type Represents the type of transition, see documentation of possible [Type] values for details.
 */
 class Transition(val owner: String, val type: Type) {
   enum class Type {
    /** Represents a transition marking the entrance of an entity or view. */
    click,

    /** Represents a transition marking the exit of an entity or view. */
    back,

    /** Represents a transition marking a touch event such as pressing (but not yet releasing or cancelling) a view. */
    press,

    /** Represents a transition marking the cancel of a touch event, likely the user moved their finger / cursor off the view while pressed. */
    cancelPress,

    /** Represents that no transition is taking place. */
    none;

    /** Method for converting [click] into [back], [back] into [click], [press] into [cancelPress], and [cancelPress] into [press]. */
    fun opposite(): Type {
      when(this) {
        click -> return back
        back -> return click
        press -> return cancelPress
        cancelPress -> return press
        none -> return none
      }
    }

    /** Returns true if the transition type is [press] or [cancelPress]. */
    fun isPress(): Boolean {
      return this == press || this == cancelPress
    }

    /** Returns true if the transition type is [click] or [back]. */
    fun isTransition(): Boolean {
      return this == click || this == back
    }
  }

  fun equals(other: Transition): Boolean {
    return other.owner == owner && other.type == type
  }
}