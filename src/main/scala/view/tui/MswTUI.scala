package view.tui

import controller.Controller
import model.GameStatus.GameStatus
import model.{Field, FieldMatrix, GameStatus, Model}
import observerpattern.Observer

class MswTUI(controller: Controller, var model: Model) extends Observer {

    // Uni-codes for fancy TUI-prints
    val bombUnicode: String = "\uD83D\uDCA3"
    val squareUnicode: String = "\u2B1B"
    val flagUnicode: String = "\u2690"
    val numberPrefixUnicode: Int = 9312
    val noSurroundingBombsUnicode: String = "\u25A1"

    model.addGameListener(this)

    println("*********************************************")
    println("--------------- GAME START ------------------")
    println("*********************************************")

  override def gameFieldUpdated(fieldMatrix: FieldMatrix, gameStatus: GameStatus): Unit = {
    createOutput(fieldMatrix, gameStatus)
  }

  def render(string: String): Unit = {
    print(string)
  }

  private def createOutput(fieldMatrix: FieldMatrix, gameStatus: GameStatus): String =
  {
    if(gameStatus == GameStatus.Won) {
      createGameWon()
    } else if(gameStatus == GameStatus.Lost) {
      createGameOver()
    } else {
      val consoleOut: StringBuilder = new StringBuilder

      var ctr = 0
      for (i <- 0 until model.difficulty._1; j <- 0 until model.difficulty._2) {
        val field = fieldMatrix.get(i, j)

        if (ctr % model.difficulty._1 == 0 && ctr != 0) {
          consoleOut.append("\n")
        }

        ctr = ctr + 1

        if (field.isFlagged) consoleOut.append(flagUnicode)
        else if (field.isOpened && field.surroundingBombs == 0) consoleOut.append(noSurroundingBombsUnicode)
        else if (field.isOpened && field.isBomb) {
          consoleOut.clear(); consoleOut.append(createGameOver())
        }
        else if (field.isOpened && field.surroundingBombs > 0) consoleOut.append((numberPrefixUnicode + field.surroundingBombs - 1).toChar.toString)
        else consoleOut.append(squareUnicode)
      }

      consoleOut.append("\n\n")
      consoleOut.toString()
    }
  }

  private def createGameWon(): String =
  {
    val gameWon:StringBuilder = new StringBuilder
    gameWon.append("----------------------------------------------------\n")
    gameWon.append("**************!Congratulations!*********************\n")
    gameWon.append("*****************!You won!**************************\n")
    gameWon.append("----------------------------------------------------\n")
    gameWon.toString()
  }

  private def createGameOver(): String = {
    val gameOver:StringBuilder = new StringBuilder
    gameOver.append("------------------------------------\n")
    gameOver.append("************!GAME OVER!*************\n")
    gameOver.append("------------------------------------\n")
    gameOver.toString()
  }
}
