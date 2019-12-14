package view.gui

import controller.GameController
import javax.swing.Box
import model.GameField

import scala.swing.{BoxPanel, Button, Orientation}

class MainContainer(x: Int, y: Int, controller: GameController, val gameField: GameField) extends BoxPanel(Orientation.Vertical) {

  contents += new GameStatusPanel
  contents += new GameFieldGrid(x, y, controller, gameField)

}
