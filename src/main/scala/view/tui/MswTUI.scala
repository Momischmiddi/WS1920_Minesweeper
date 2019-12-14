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

  override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = render(createTUI(fields))

  def render(string: String): Unit = {
    print(string);
  }

  private def createTUI(fields: Array[Array[Field]]): String =
  {
    val field: StringBuilder = new StringBuilder
    for (v <- fields) {
      field.append("\n")
      for (h <- v) {
        if (h.isFlagged) field.append(flagUnicode)
        else if (h.isOpened && h.surroundingBombs == 0) field.append(noSurroundingBombsUnicode)
        else if (h.isOpened && h.isBomb) {field.clear(); field.append(createGameOver())}
        else if (h.isOpened && h.surroundingBombs > 0) field.append((numberPrefixUnicode+h.surroundingBombs-1).toChar.toString)
        else field.append(squareUnicode)
      }
    }

    field.toString()
  }

  override def receiveGameEndUpdate(gameWon: Boolean): Unit = {
    if(gameWon) render(createGameWon()) else render(createGameOver())
    System.exit(0)
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
