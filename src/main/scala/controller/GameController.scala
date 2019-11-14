package controller

import model.GameField

class GameController(gameField: GameField) {

  def openField(coordinates: (Int, Int), isFlag: Boolean) : (Boolean, Boolean) = {
    gameField.selectField(coordinates._1, coordinates._2, isFlag)
  }
}
