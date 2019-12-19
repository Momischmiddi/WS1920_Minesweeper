package view.gui

import controller.Controller
import model.{FieldMatrix, Model}

import scala.swing.GridPanel

class GameFieldGrid(controller: Controller, model: Model, mainContainer: MainContainer, fieldMatrix: FieldMatrix)
  extends GridPanel(model.difficulty._1, model.difficulty._2) {

  contents ++= (for {i <- 0 until model.difficulty._1; j <- 0 until model.difficulty._2}
    yield new FieldLabel(i, j, this, fieldMatrix.get(j, i)))

  def handleFieldClick(x: Int , y: Int, isRightClick: Boolean): Unit = {
    controller.handleClick(x, y, fieldMatrix, isRightClick)
  }
}
