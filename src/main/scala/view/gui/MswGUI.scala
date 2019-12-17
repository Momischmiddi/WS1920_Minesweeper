package scala.view.gui

import controller.GameController
import javax.swing.ImageIcon
import model.Difficulty.Difficulty
import model.{Difficulty, Field, GameField}
import observer.Observer
import view.GameStatus
import view.GameStatus.GameStatus
import view.gui.{MainContainer, Setup}

import scala.swing.MainFrame

class MswGUI(var controller: GameController, var gameField: GameField, setup: Setup) extends MainFrame with Observer {
  def restart(difficulty: Difficulty): Unit = {
    dispose
    setup.start(difficulty)
  }

  contents = new MainContainer(controller, gameField, this, GameStatus.InProgress)
  gameField.addGameListener(this)
  resizable = false
  iconImage = new ImageIcon("src/sprites/frameicon.png").getImage
  title = "Minesweeper"
  pack()
  centerOnScreen()
  open()

  var gameStatus = GameStatus.InProgress

  override def receiveGameFieldUpdate(fields: Array[Array[Field]], gameStatus: GameStatus): Unit = {
    contents = new MainContainer(controller, gameField, this, gameStatus)
  }
}

