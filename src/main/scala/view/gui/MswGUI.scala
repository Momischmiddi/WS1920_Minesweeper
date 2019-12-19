package scala.view.gui

import controller.Controller
import javax.swing.ImageIcon
import model.Difficulty.Difficulty
import model.GameStatus.GameStatus
import model.{FieldMatrix, GameStatus, Model}
import observerpattern.Observer
import view.gui.{MainContainer, Setup}

import scala.swing.MainFrame

class MswGUI(var controller: Controller, var model: Model, setup: Setup, fieldMatrix: FieldMatrix) extends MainFrame with Observer {

  def restart(difficulty: Difficulty): Unit = {
    dispose
    setup.start(difficulty)
  }

  contents = new MainContainer(controller, model, this, GameStatus.InProgress, fieldMatrix)
  model.addGameListener(this)
  resizable = false
  iconImage = new ImageIcon("src/sprites/frameicon.png").getImage
  title = "Minesweeper"
  pack()
  centerOnScreen()
  open()

  override def gameFieldUpdated(fieldMatrix: FieldMatrix, gameStatus: GameStatus): Unit = {
    contents = new MainContainer(controller, model, this, gameStatus, fieldMatrix)
  }
}

