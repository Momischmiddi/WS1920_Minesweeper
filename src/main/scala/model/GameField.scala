package model

import model.Difficulty.Difficulty
import observer.Subject

class GameField(val fields: Array[Array[Field]],  val difficulty: Difficulty = Difficulty.Easy) extends Subject {

  def selectField(x: Int, y: Int, isFlag: Boolean): (Boolean, Boolean) = {
    fireGameChangeEvent(null)
    (false, false)
  }

}
