package view.tui

import controller.GameController
import model.Difficulty.Difficulty
import model.{Difficulty, Field, GameField, GameFieldCreator}
import observer.Observer

import scala.io.StdIn

class MswTUI extends Observer {

    // Uni-codes for fancy TUI-prints
    val bombUnicode: String = "\uD83D\uDCA3"
    val squareUnicode: String = "\u2B1B"
    val flagUnicode: String = "\u2690"
    val numberPrefixUnicode: Int = 9312
    val noSurroundingBombsUnicode: String = "\u25A1"

  def startGame(): Unit = {
    println("*******************************************")
    println("--------- WELCOME TO MINESWEEPER ----------")
    println("*******************************************")
    println("\n")
    println("Choose Difficulty:  (Default: Easy)")
    println("Easy -> 1")
    println("Medium -> 2")
    println("Hard -> 3")
    val difficulty = StdIn.readInt()

    val bombs, game = difficulty match {
      case 1 => Difficulty.Easy
      case 2 => Difficulty.Medium
      case 3 => Difficulty.Hard
      case _ => Difficulty.Easy
    }

    val creator = new GameFieldCreator
    val randomBombs = creator.createRandomBombLocations(bombs)
    val gameField = creator.createGameField(game, randomBombs)
    gameField.addGameListener(this)

    println("*********************************************")
    println("--------------- GAME START ------------------")
    println("*********************************************")

    val controller = new GameController(gameField)

    while(true){
      println("\nChoose fields:  x , y")
      val x = StdIn.readLine("X coordinate: ")
      val y = StdIn.readLine("y coordinate: ")
      controller.selectField((x.toInt,y.toInt),false)
    }
  }

  override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = redrawTUI(fields)

  private def redrawTUI(fields: Array[Array[Field]]): Unit =
  {
    for (v <- fields) {
      print("\n")
      for (h <- v) {
        if (h.isFlagged) print(flagUnicode)
        else if (h.isOpened && h.surroundingBombs == 0) print(noSurroundingBombsUnicode)
        else if (h.isOpened && h.isBomb) printGameOver()
        else if (h.isOpened && h.surroundingBombs > 0) print((numberPrefixUnicode+h.surroundingBombs-1).toChar.toString)
        else print(squareUnicode)
      }
    }
  }

  override def receiveGameEndUpdate(gameWon: Boolean): Unit = if(gameWon) printGameWon() else printGameOver()

  private def printGameWon(): Unit =
  {
    println("----------------------------------------------------")
    println("**************!Congratulations!*********************")
    println("*****************!You won!**************************")
    println("----------------------------------------------------")
    System.exit(0)
  }

  private def printGameOver(): Unit = {
    println("------------------------------------")
    println("************!GAME OVER!*************")
    println("------------------------------------")
    System.exit(0)
  }
}
