package com.dallaway.fsm

object Reber {

  case class State[I](links: Seq[(I, State[I])])

  lazy val state0: State[Char] = State(List( 'T' -> state1, 'P' -> state2 ))
  lazy val state1: State[Char] = State(List( 'S' -> state1, 'X' -> state3 ))
  lazy val state2: State[Char] = State(List( 'T' -> state2, 'V' -> state4 ))
  lazy val state3: State[Char] = State(List( 'X' -> state2, 'S' -> state5 ))
  lazy val state4: State[Char] = State(List( 'V' -> state5, 'P' -> state3 ))
  lazy val state5 = State[Char](Nil)
 
}

