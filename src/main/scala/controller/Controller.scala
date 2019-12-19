package controller

import model.{FieldMatrix, Model}

class Controller(model: Model) {

  def handleClick(x: Int, y: Int, matrix: FieldMatrix, flag: Boolean): Unit = {
    model.update(matrix, flag, x, y)
  }

}
