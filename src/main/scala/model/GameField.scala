package model

import model.Difficulty.Difficulty
import observer.Subject

//vector
class GameField(val fields: Array[Array[Field]],  val difficulty: Difficulty = Difficulty.Easy) extends Subject {

  def fireStartField(): Unit = {fireFieldChangeEvent(fields)}

  def selectField(x: Int, y: Int, flagField: Boolean): Boolean = {
    val selectedField = getFieldFromGameField(x, y)

    if(ignoreClick(selectedField, flagField)) {
      false
    } else {
      val newField = new Field(x, y, selectedField.isBomb, flagField, !flagField, getSurroundingBombAmount(selectedField))
      fields(y)(x) = newField

      if(isGameWon) {
        fireGameEndEvent(true)
      } else {
        val isGameOver : Boolean = fields(y)(x).isBomb && !flagField

        if(!isGameOver && newField.surroundingBombs < 1 && !flagField)
          openAllNeighbours(x, y, newField.surroundingBombs > 0)

        if(!isGameOver) {
          fireFieldChangeEvent(fields)
        } else {
          fireGameEndEvent(false)
        }
      }

      true
    }
  }

  private def openAllNeighbours(x: Int, y: Int, isNumber: Boolean): Unit = {
    val field = getFieldFromGameField(x, y)
    // If the field has a number on it, diagonal neighbours should be ignored
    val neighbours = if(isNumber) {
      // Its a diagonal field, if the manhattan distance is > 1
      getSurroundingFields(x, y).filter(f => calculateManhattanDistance(field, f) > 1)
    } else {
      getSurroundingFields(x, y)
    }

    for(neighbour <- neighbours) {
      val (x, y) = (neighbour.xLocation, neighbour.yLocation)

      // Recursively open all neighbours, which are no bombs and have no numbers on it.
      if(!neighbour.isBomb && !neighbour.isOpened && field.surroundingBombs < 1) {
        fields(y)(x) = new Field(x, y, false ,false, true, getSurroundingBombAmount(neighbour))
        openAllNeighbours(x, y, fields(y)(x).surroundingBombs > 0)
      }
    }
  }

  private def ignoreClick(field: Field, isFlag: Boolean) : Boolean = {
    field.isOpened || (field.isFlagged && !isFlag)
  }

  private def getFieldFromGameField(x: Int, y: Int) : Field = {
    fields.flatten.find(f => f.xLocation == x && f.yLocation == y) match {
      case Some(value) => value
      case _ => null
    }
  }

  private def getSurroundingFields(x: Int, y: Int) : List[(Field)] = {
    List(
      getFieldFromGameField(x+1, y),
      getFieldFromGameField(x, y+1),
      getFieldFromGameField(x-1, y),
      getFieldFromGameField(x, y-1),
      getFieldFromGameField(x+1, y+1),
      getFieldFromGameField(x+1, y-1),
      getFieldFromGameField(x-1, y+1),
      getFieldFromGameField(x-1, y-1),
    ).filter(_ != null)
  }

  private def getSurroundingBombAmount(field: Field) : Int =
    getSurroundingFields(field.xLocation, field.yLocation).
      count(_.isBomb)

  private def calculateManhattanDistance(original: Field, toCompare: Field): Int =
    Math.abs(original.xLocation - toCompare.xLocation) +
      Math.abs(original.yLocation - toCompare.yLocation)

  private def isGameWon : Boolean = fields.flatten.filter(!_.isBomb).forall(_.isOpened)
}
