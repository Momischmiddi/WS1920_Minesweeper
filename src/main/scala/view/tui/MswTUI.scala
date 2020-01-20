package view.tui

import controller.Controller
import model.GameStatus.GameStatus
import model.{FieldMatrix, GameStatus, Model}
import observerpattern.Observer

class MswTUI(controller: Controller, model: Model) extends Observer {

    // Uni-codes for fancy TUI-prints
    val bombUnicode: String = "\uD83D\uDCA3"
    val squareUnicode: String = "\u2B1B"
    val flagUnicode: String = "\u2690"
    val numberPrefixUnicode: Int = 9312
    val noSurroundingBombsUnicode: String = "\u25A1"

    model.addGameListener(this)

  override def gameFieldUpdated(fieldMatrix: FieldMatrix, gameStatus: GameStatus): Unit = {
    render(createHeader(gameStatus))
    render(createField(fieldMatrix))
  }

  def render(string: String): Unit = {
    System.out.print(string)
  }

  def createHeader(gameStatus: GameStatus): String = {
    val headerText:StringBuilder = new StringBuilder

    headerText.append("\n-------------------------------------------------------\n")

    headerText.append(
      gameStatus match {
        case GameStatus.Won => "***********************!You won!***********************\n"
        case GameStatus.Lost => "***********************!You lost!**********************\n"
        case _=> "*********************** New move **********************\n"
      }
    )

    headerText.append("-------------------------------------------------------\n")
    headerText.toString
  }

  def createField(fieldMatrix: FieldMatrix): String = {
    val fieldText:StringBuilder = new StringBuilder

    for (i <- 0 until model.difficulty._1; j <- 0 until model.difficulty._2) {
      val field = fieldMatrix.get(j, i)

      if (field.isFlagged) fieldText.append(flagUnicode)
      else if (field.isOpened && field.surroundingBombs == 0) fieldText.append(noSurroundingBombsUnicode)
      else if (field.isOpened && field.surroundingBombs > 0) fieldText.append((numberPrefixUnicode + field.surroundingBombs - 1).toChar.toString)
      else fieldText.append(squareUnicode)

      if(j > 0 && j % (model.difficulty._1-1) == 0) {
        fieldText.append("\n")
      }
    }

    fieldText.toString
  }
}
