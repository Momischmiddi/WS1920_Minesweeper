package scala.view.gui

import controller.GameController
import model.{Difficulty, Field, GameFieldCreator}
import observer.Observer

import scala.swing.{Button, Dimension, FlowPanel, Frame, Label, MainFrame}

class MswGUI extends MainFrame with Observer{

    // Uni-codes for fancy TUI-prints
    val bombUnicode: String = "\uD83D\uDCA3"
    val squareUnicode: String = "\u2B1B"
    val flagUnicode: String = "\u2690"
    val numberPrefixUnicode: Int = 9312
    val noSurroundingBombsUnicode: String = "\u25A1"

  title = "Minesweeper"
  preferredSize = new Dimension(400, 400)
  contents = new FlowPanel {
    contents += new Label("test123")
    contents += new Label("test234")
    contents += new Button("testButton")
  }

  def startGame(): Unit =
  {
    val creator = new GameFieldCreator
    val randomBombs = creator.createRandomBombLocations(Difficulty.Easy)
    val gameField = creator.createGameField(Difficulty.Easy, randomBombs)

    gameField.addGameListener(this)

    val controller = new GameController(gameField)

    while(true)
      controller.selectField((7, 5), false)
  }


  def redrawField(fields: Array[Array[Field]]): Unit =
  {
      for (v <- fields) {
        print("\n")
        contents.appended(new Label("Test"))
        for (h <- v) {
          if (h.isFlagged) print(flagUnicode)
          else if (h.isOpened && h.surroundingBombs == 0) print(noSurroundingBombsUnicode)
          else if (h.isOpened && h.isBomb) printGameOver()
          else if (h.isOpened && h.surroundingBombs > 0) print((numberPrefixUnicode+h.surroundingBombs-1).toChar.toString)
          else print(squareUnicode)
        }
      }
  }

  private def printGameOver(): Unit = {
    println("------------------------------------")
    println("************!GAME OVER!*************")
    println("------------------------------------")
  }

  override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = redrawField(fields)

  override def receiveGameEndUpdate(gameWon: Boolean): Unit = printGameOver()
}

