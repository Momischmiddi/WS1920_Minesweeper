package controller

import model.GameField

class GameController(gameField: GameField) {

  gameField.fireStartField()
  def selectField(coordinates: (Int, Int), flagField: Boolean) : Unit = {
    gameField.selectField(coordinates._1, coordinates._2, flagField)
  }
}
