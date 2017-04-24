package com.dallaway

object fsm {

  class Transition[I](val input: I, nextState: => State[I]) {
    lazy val next = nextState
  }

  // TODO: `transitions` should not contain the same symbol more than once
  case class State[I](transitions: Seq[Transition[I]])

  object State {
    def terminal[I] = State[I](Nil)
    def is[I](ts: Transition[I]*): State[I] = State(ts.toList)
  }

  object Transition {
    def apply[I](input: I, nextState: => State[I]) = new Transition(input, nextState)
  }

  object Implicits {
    implicit class arrow[I](input: I) {
      def -->(newState: => State[I]) = Transition(input, newState)
    }
  }

  case class FSM[I](state: State[I]) {

    // TODO: `pick` should be NonEmpty => Transition
    def generate(pick: Seq[Transition[I]] => Transition[I]): Stream[I] =
      if (state.transitions.isEmpty) Stream.empty
      else {
        val transition = pick(state.transitions)
        Stream.cons(transition.input, FSM(transition.next).generate(pick))
      }
  }
 
}

object Reber {

  import fsm._
  import Implicits._

  lazy val state0: State[Char] = State.is('T' --> state1, 'P' --> state2)
  lazy val state1: State[Char] = State(Seq(Transition('S', state1), Transition('X', state3)))
  lazy val state2: State[Char] = State(Seq(Transition('T', state2), Transition('V', state4)))
  lazy val state3: State[Char] = State(Seq(Transition('X', state2), Transition('S', state5)))
  lazy val state4: State[Char] = State(Seq(Transition('V', state5), Transition('P', state3)))
  lazy val state5 = State.terminal[Char]

  def main(args: Array[String]): Unit = {

    def rnd[I](ts: Seq[Transition[I]]): Transition[I] =
      scala.util.Random.shuffle(ts).head

    val fsm = FSM(Reber.state0)
    println(
      fsm.generate(rnd).take(100).mkString
    )

  }
}

