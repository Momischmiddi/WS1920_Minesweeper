package view.gui

import controller.Controller
import model.Difficulty.Difficulty
import model.{Creator, FieldMatrix, Model}
import view.tui.MswTUI

import scala.view.gui.MswGUI

class Setup {

  def start(difficulty: Difficulty): FieldMatrix = {
    val creator = new Creator
    val randomBombs = creator.createRandomBombLocations(difficulty)
    val fieldMatrix = creator.create(difficulty, randomBombs)

    val model = new Model(difficulty)
    val controller = new Controller(model)

    new MswTUI(controller, model)
    new MswGUI(controller, model, this, fieldMatrix)

    fieldMatrix
  }

}
