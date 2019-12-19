package model

object GameStatus extends Enumeration {
  type GameStatus = Value
  val Won, Lost, InProgress = Value
}