package view.gui

import javax.swing.ImageIcon
import model.Field

import scala.swing.{Dimension, Label}
import scala.swing.event.MouseClicked

class FieldLabel(x: Int, y: Int, gameFieldGrid: GameFieldGrid, field: Field) extends Label {
  updateIcon(field)
  preferredSize = new Dimension(40, 40)
  name = (x*y).toString
  listenTo(mouse.clicks)
  reactions += {
    case MouseClicked(_, _, c, _, _) => handleFieldClick(x, y, c != 0)
  }

  private def getIconNameFromField(field: Field): String = {
    if(field.isRedBomb) {
      "bomb_red"
    } else if(!field.isOpened && !field.isFlagged) {
      "block"
    } else if(field.isFlagged) {
      "flag"
    } else if(field.isOpened && field.isBomb) {
      "bomb"
    } else if(field.isOpened && field.surroundingBombs > 0) {
      field.surroundingBombs.toString
    } else {
      "empty"
    }
  }

  def updateIcon(field: Field): Unit = {
    icon = new ImageIcon("src/sprites/field/" + getIconNameFromField(field) + ".png")
  }

  def handleFieldClick(x: Int , y: Int, isRightClick: Boolean): Unit = {
    gameFieldGrid.handleFieldClick(x, y, isRightClick)
  }
}
