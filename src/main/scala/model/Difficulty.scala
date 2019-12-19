package model

object Difficulty extends Enumeration {

  type Difficulty = (Int, Int, Int)
  // X-size, Y-size, Bombamount
  val Easy = (9, 9, 10)
  val Medium = (14, 14, 26)
  val Hard = (20, 20, 45)
}
