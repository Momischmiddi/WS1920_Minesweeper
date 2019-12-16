package view.gui

import controller.GameController
import model.GameField

import scala.swing.GridPanel

class GameFieldGrid(controller: GameController, gameField: GameField, mainContainer: MainContainer) extends GridPanel(gameField.difficulty._1, gameField
.difficulty._2) {
  contents ++= (for {i <- 0 until gameField.difficulty._1; j <- 0 until gameField.difficulty._2} yield new FieldLabel(i, j, this, gameField.getFieldFromGameField(i, j)))

  def handleFieldClick(x: Int , y: Int, isRightClick: Boolean): Unit = {
    controller.selectField((x, y), isRightClick)
  }
}
