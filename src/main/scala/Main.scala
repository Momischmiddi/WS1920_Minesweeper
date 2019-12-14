import controller.GameController
import model.{Difficulty, GameFieldCreator}
import view.tui.MswTUI

import scala.view.gui.MswGUI

object Main {
  def main(args: Array[String]): Unit = {
    val creator = new GameFieldCreator
    val randomBombs = creator.createRandomBombLocations(Difficulty.Easy)
    val gameField = creator.createGameField(Difficulty.Easy, randomBombs)

    val controller = new GameController(gameField)

    val mswGUI = new MswGUI(controller, gameField)

    //val mswTUI = new MswTUI
    //mswTUI.startGame()
  }
}
