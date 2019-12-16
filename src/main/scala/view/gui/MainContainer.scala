package view.gui

import controller.GameController
import model.GameField
import observer.Observer
import view.GameStatus.GameStatus

import scala.swing.{BoxPanel, Orientation}
import scala.view.gui.MswGUI

class MainContainer(controller: GameController, val gameField: GameField, mswFrame: MswGUI, gameStatus: GameStatus) extends BoxPanel(Orientation.Vertical) {
  def restart(): Unit = {
    mswFrame.restart()
  }

  contents += new GameStatusPanel(gameStatus, this)
  contents += new GameFieldGrid(controller, gameField, this)
}
