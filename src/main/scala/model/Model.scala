package model

import model.Difficulty.Difficulty
import observerpattern.Subject

class Model(val difficulty: Difficulty) extends Subject {

  def update(fieldMatrix: FieldMatrix, flag: Boolean, x: Int, y: Int): Unit = {
    val selectedField = fieldMatrix.get(y, x)

    if(!ignoreClick(selectedField, flag, fieldMatrix.fields, difficulty)) {
      val newField = if(flag) {
        selectedField.flipFlag().setBombs(getSurroundingBombAmount(fieldMatrix, x, y))
      } else {
        selectedField.open().setBombs(getSurroundingBombAmount(fieldMatrix, x, y))
      }

      val replaced = fieldMatrix.replaceField(x, y, newField)

      val gameDone = isGameDone(replaced.fields)

      if (flag) {
        fireFieldUpdated(replaced, GameStatus.InProgress)
      } else if (!gameDone) {
        val updated = expand(selectedField, replaced)
        if (isGameWon(updated.fields, difficulty)) {
          fireFieldUpdated(updated, GameStatus.Won)
        } else {
          fireFieldUpdated(updated, GameStatus.InProgress)
        }
      } else {
        fireFieldUpdated(createGameLostField(x, y, replaced), GameStatus.Lost)
      }
    }
  }

  def expand(selectedField: Field, fieldMatrix: FieldMatrix): FieldMatrix = {
    val updatedField = selectedField.open().setBombs(getSurroundingBombAmount(fieldMatrix, selectedField.xLocation, selectedField.yLocation))
    val replacedMatrix = fieldMatrix.replaceField(selectedField.xLocation, selectedField.yLocation, updatedField)

    val neighbours = getNeighbours(selectedField.yLocation, selectedField.xLocation, replacedMatrix)

    val toOpen = neighbours
      .filter(f => !f.isOpened && !f.isBomb)
      .filter(f => getSurroundingBombAmount(fieldMatrix, f.xLocation, f.yLocation) > 0)

    val toExpand = neighbours
      .filter(f => !f.isOpened && !f.isBomb)
      .filter(f => !(getSurroundingBombAmount(fieldMatrix, f.xLocation, f.yLocation) > 0))

    toExpand.foldLeft(
      toOpen.foldLeft(
        replacedMatrix)((r, f) => r.replaceField(
        f.xLocation, f.yLocation, f.open().setBombs(getSurroundingBombAmount(r, f.xLocation, f.yLocation)))
      ))((r, n) => expand(n, r))
  }

  def createGameLostField(x: Int, y: Int, fieldMatrix: FieldMatrix): FieldMatrix =
    fieldMatrix.fields.flatten.filter(f => f.isBomb)
      .foldLeft(fieldMatrix)((fieldMatrix, bomb) => fieldMatrix.replaceField(
      bomb.xLocation, bomb.yLocation, bomb.open()
    )).replaceField(
      x, y, Field(x, y, true, false, true, 0, true)
    )

  def ignoreClick(field: Field, flag: Boolean, fields: Vector[Vector[Field]], difficulty: Difficulty) : Boolean = field.isOpened || (field.isFlagged && !flag) || isGameDone(fields) || isGameWon(fields, difficulty)

  def isGameWon(fields: Vector[Vector[Field]], difficulty: Difficulty) : Boolean = fields.flatten.filter(!_.isBomb).count(_.isOpened) == (difficulty._1 * difficulty._2) - difficulty._3

  def isGameDone(fields: Vector[Vector[Field]]) : Boolean =  fields.flatten.filter(_.isBomb).count(_.isOpened) > 0

  def getSurroundingBombAmount(fieldMatrix: FieldMatrix, x: Int, y: Int) : Int = getNeighbours(y, x, fieldMatrix).count(_.isBomb)

  def getNeighbours(y: Int, x: Int, fieldMatrix: FieldMatrix) : List[Field] = {
    List(
      fieldMatrix.get(y, x + 1),
      fieldMatrix.get(y + 1, x),
      fieldMatrix.get(y, x - 1),
      fieldMatrix.get(y - 1, x),
      fieldMatrix.get(y + 1, x + 1),
      fieldMatrix.get(y - 1, x + 1),
      fieldMatrix.get(y + 1, x - 1),
      fieldMatrix.get(y - 1, x - 1),
    ).filter(_ != null)
  }
}
