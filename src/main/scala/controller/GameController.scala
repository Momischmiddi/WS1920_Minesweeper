package controller

import model.GameField

class GameController(gameField: GameField) {

  def selectField(x: Int, y: Int, isFlag: Boolean) : (Boolean, Boolean) = {
    gameField.selectField(x, y, isFlag)
  }
}
