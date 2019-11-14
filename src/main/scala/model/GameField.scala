package model

import model.Difficulty.Difficulty
import observer.Subject

class GameField(val fields: Array[Array[Field]],  val difficulty: Difficulty = Difficulty.Easy) extends Subject {

  def openField(x: Int, y: Int): (Boolean, Boolean) = {
    fireGameChangeEvent(null)
    (false, false)
  }

}
