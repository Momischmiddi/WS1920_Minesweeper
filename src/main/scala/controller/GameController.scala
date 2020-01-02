package controller

import model.GameField

class GameController(gameField: GameField) {
  //Constructor for initial Gamefield
  //gameField.fireStartField()

  def startGame(): Unit ={
    gameField.fireStartField()
  }

  def selectField(coordinates: (Int, Int), flagField: Boolean) : Boolean = {
    gameField.selectField(coordinates._1, coordinates._2, flagField)
  }
}
