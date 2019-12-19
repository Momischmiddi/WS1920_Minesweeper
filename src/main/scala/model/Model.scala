package model

import model.Difficulty.Difficulty
import observerpattern.Subject

class Model(val difficulty: Difficulty) extends Subject {

  def update(fieldMatrix: FieldMatrix, flag: Boolean, x: Int, y: Int): Unit = {
    val selectedField = fieldMatrix.get(y, x)

    val newField = if(ignoreClick(selectedField, flag, fieldMatrix.fields, difficulty)) {
      selectedField
    } else {
      if(flag) {
        selectedField.flipFlag().setBombs(getSurroundingBombAmount(fieldMatrix, x, y))
      } else {
        selectedField.open().setBombs(getSurroundingBombAmount(fieldMatrix, x, y))
      }
    }

    val replaced = fieldMatrix.replaceField(x, y, newField)

    val gameDone = isGameDone(replaced.fields)
    val gameWon = isGameWon(replaced.fields, difficulty)

    if(flag) {
      fireFieldUpdated(replaced, GameStatus.InProgress)
    } else if(!gameDone) {
      if(gameWon) {
        fireFieldUpdated(replaced, GameStatus.Won)
      } else {
        val updated = expand(selectedField, replaced)
        fireFieldUpdated(updated, GameStatus.InProgress)
      }
    } else {
      if(gameWon) {
        fireFieldUpdated(replaced, GameStatus.Won)
      } else {
        fireFieldUpdated(replaced, GameStatus.Lost)
      }
    }
  }

  def expand(selectedField: Field, fieldMatrix: FieldMatrix): FieldMatrix = {
    val updatedField = selectedField.open().setBombs(getSurroundingBombAmount(fieldMatrix, selectedField.xLocation, selectedField.yLocation))
    val replacedMatrix = fieldMatrix.replaceField(selectedField.xLocation, selectedField.yLocation, updatedField)

    val neighbours = if(selectedField.surroundingBombs > 0) {
      getNeighbours(selectedField.yLocation, selectedField.xLocation, replacedMatrix)
        .filter(f => calculateManhattanDistance(selectedField, f) > 1)
    } else {
      getNeighbours(selectedField.yLocation, selectedField.xLocation, replacedMatrix)
    }

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

  def openNeighboursRecursively(x: Int, y: Int, hasSurroundingBombs: Boolean, fieldMatrix: FieldMatrix): FieldMatrix = {
    val field = fieldMatrix.get(y, x)

    val neighbours = if(hasSurroundingBombs) {
      // If the field has a number on it, diagonal neighbours should be ignored
      // Its a diagonal field, if the manhattan distance is > 1
      getNeighbours(y, x, fieldMatrix).filter(f => calculateManhattanDistance(field, f) > 1)
    } else {
      getNeighbours(y, x, fieldMatrix)
    }

    for(neighbour <- neighbours) {
      val (nX, nY) = (neighbour.xLocation, neighbour.yLocation)

      // Recursively open all neighbours, which are no bombs and have no numbers on it.
      if(!neighbour.isBomb && !neighbour.isOpened && field.surroundingBombs < 1) {
        val bombAmount = getSurroundingBombAmount(fieldMatrix, nX, nY)
        val toReplace = neighbour.open().setBombs(bombAmount)
        val replaced = fieldMatrix.replaceField(nX, nY, toReplace)

        openNeighboursRecursively(nX, nY, bombAmount > 0, replaced)
      }
    }

    fieldMatrix
  }

  def ignoreClick(field: Field, flag: Boolean, fields: Vector[Vector[Field]], difficulty: Difficulty) : Boolean =
    field.isOpened || (field.isFlagged && !flag) || isGameDone(fields) || isGameWon(fields, difficulty)

  def isGameWon(fields: Vector[Vector[Field]], difficulty: Difficulty) : Boolean =
    fields.flatten.filter(!_.isBomb).count(_.isOpened) == (difficulty._1 * difficulty._2) - difficulty._3

  def isGameDone(fields: Vector[Vector[Field]]) : Boolean =  fields.flatten.filter(_.isBomb).count(_.isOpened) > 0

  def getSurroundingBombAmount(fieldMatrix: FieldMatrix, x: Int, y: Int) : Int =
    getNeighbours(y, x, fieldMatrix).count(_.isBomb)

  def calculateManhattanDistance(original: Field, toCompare: Field): Int =
    Math.abs(original.xLocation - toCompare.xLocation) +
      Math.abs(original.yLocation - toCompare.yLocation)

  def getNeighbours(y: Int, x: Int, fieldMatrix: FieldMatrix) : List[Field] = {
    val res = List(
      fieldMatrix.get(y, x + 1),
      fieldMatrix.get(y + 1, x),
      fieldMatrix.get(y, x - 1),
      fieldMatrix.get(y - 1, x),
      fieldMatrix.get(y + 1, x + 1),
      fieldMatrix.get(y - 1, x + 1),
      fieldMatrix.get(y + 1, x - 1),
      fieldMatrix.get(y - 1, x - 1),
    ).filter(_ != null)
    res
  }
}
