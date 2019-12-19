package model

import model.Difficulty.Difficulty

case class FieldMatrix(fields: Vector[Vector[Field]], difficulty: Difficulty) {

  def replaceField(x: Int, y: Int, field: Field): FieldMatrix = copy(fields.updated(y, fields(y).updated(x, field)))

  def get(y: Int, x: Int): Field = {
    fields.flatten.find(f => f.xLocation == x && f.yLocation == y).orNull
  }
}
