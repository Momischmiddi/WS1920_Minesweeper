package view.tui

import controller.GameController
import model.{Field, GameField}
import observer.Observer
import view.GameStatus
import view.GameStatus.GameStatus

class MswTUI(controller: GameController, var gameField: GameField) extends Observer {

    // Uni-codes for fancy TUI-prints
    val bombUnicode: String = "\uD83D\uDCA3"
    val squareUnicode: String = "\u2B1B"
    val flagUnicode: String = "\u2690"
    val numberPrefixUnicode: Int = 9312
    val noSurroundingBombsUnicode: String = "\u25A1"

    gameField.addGameListener(this)

    println("*********************************************")
    println("--------------- GAME START ------------------")
    println("*********************************************")

  override def receiveGameFieldUpdate(fields: Array[Array[Field]], gameStatus: GameStatus): Unit = render(createOutput(fields, gameStatus))

  def render(string: String): Unit = {
    print(string)
  }

  private def createOutput(fields: Array[Array[Field]], gameStatus: GameStatus): String =
  {
    if(gameStatus == GameStatus.Won) {
      createGameWon()
    } else if(gameStatus == GameStatus.Lost) {
      createGameOver()
    } else {
      val consoleOut: StringBuilder = new StringBuilder

      var ctr = 0
      for (i <- 0 until gameField.difficulty._1; j <- 0 until gameField.difficulty._2) {
        val field = gameField.getFieldFromGameField(i, j)

        if (ctr % gameField.difficulty._1 == 0 && ctr != 0) {
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
