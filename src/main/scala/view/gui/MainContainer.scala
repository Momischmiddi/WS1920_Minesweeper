package view.gui

import controller.Controller
import model.Difficulty.Difficulty
import model.GameStatus.GameStatus
import model.{FieldMatrix, Model}

import scala.swing.{BoxPanel, Orientation}
import scala.view.gui.MswGUI

class MainContainer(controller: Controller, val model: Model, mswFrame: MswGUI, gameStatus: GameStatus, fieldMatrix: FieldMatrix) extends BoxPanel(Orientation.Vertical) {

  def restart(difficulty: Difficulty): Unit = {
    mswFrame.restart(difficulty)
  }

  contents += new GameStatusPanel(gameStatus, this, model.difficulty)
  contents += new GameFieldGrid(controller, model, this, fieldMatrix)
  contents += new GameDifficultyPanel(this)
}
