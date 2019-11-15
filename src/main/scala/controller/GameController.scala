package controller

import model.GameField

class GameController(gameField: GameField) {

  def selectField(coordinates: (Int, Int), flagField: Boolean) : Boolean = {
    gameField.selectField(coordinates._1, coordinates._2, flagField)
  }
}
