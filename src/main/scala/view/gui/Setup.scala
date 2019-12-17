package view.gui

import controller.GameController
import model.Difficulty.Difficulty
import model.{Difficulty, GameFieldCreator}
import view.tui.MswTUI

import scala.view.gui.MswGUI

class Setup {

  def start(difficulty: Difficulty) = {
    val creator = new GameFieldCreator
    val randomBombs = creator.createRandomBombLocations(difficulty)
    val gameField = creator.createGameField(difficulty, randomBombs)
    val controller = new GameController(gameField)

    val mswTUI = new MswTUI(controller, gameField)
    val mswGUI = new MswGUI(controller, gameField, this)
  }

}
