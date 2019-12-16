package scala.view.gui

import controller.GameController
import javax.swing.ImageIcon
import model.{Difficulty, Field, GameField, GameFieldCreator}
import observer.Observer
import view.GameStatus
import view.gui.MainContainer

import scala.swing.MainFrame

class MswGUI(controller: GameController, var gameField: GameField) extends MainFrame with Observer {
  def restart(): Unit = {
    val gameFieldCreator = new GameFieldCreator()
    val bombLocations = gameFieldCreator.createRandomBombLocations(Difficulty.Hard)
    //val listeners = gameField.getGameListeners()

    gameField = gameFieldCreator.createGameField(Difficulty.Hard, bombLocations)
    gameField.addGameListener(this)
    //listeners.foreach(li => gameField.addGameListener(li))
    contents = new MainContainer(controller, gameField, this, GameStatus.InProgress)
    pack()
    centerOnScreen()
  }

  gameField.addGameListener(this)
  title = "Minesweeper"
  resizable = false
  iconImage = new ImageIcon("src/sprites/frameicon.png").getImage

  contents = new MainContainer(controller, gameField, this, GameStatus.InProgress)

  pack()
  centerOnScreen()
  open()

  var gameStatus = GameStatus.InProgress

  override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {
    contents = new MainContainer(controller, gameField, this, gameStatus)
  }

  override def receiveGameEndUpdate(gameWon: Boolean, fields: Array[Array[Field]]): Unit = {
    if(gameWon) {
      gameStatus = GameStatus.Won
    } else {
      gameStatus = GameStatus.Lost
    }

    receiveGameFieldUpdate(fields)
  }
}

