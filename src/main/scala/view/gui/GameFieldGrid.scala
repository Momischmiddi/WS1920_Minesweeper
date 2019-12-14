package view.gui

import controller.GameController
import javax.swing.ImageIcon
import model.{Field, GameField}
import observer.Observer

import scala.swing.event.MouseClicked
import scala.swing.{Dimension, GridPanel, Label}

class GameFieldGrid(x: Int, y: Int, controller: GameController, val gameField: GameField) extends GridPanel(x, y) with Observer {
  gameField.addGameListener(this)

    contents ++= (for {i <- 0 until x; j <- 0 until y } yield new Label {
      icon = new ImageIcon("src/sprites/field/block.png")
      preferredSize = new Dimension(40, 40)
      name = (i*j).toString
      listenTo(mouse.clicks)
      reactions += {
        case MouseClicked(_, _, c, _, _) => handleFieldClick(j, i, c != 0)
      }
    })


  override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {
      contents ++= {
        for {i <- 0 until x; j <- 0 until y} yield new Label {
          icon = if(fields(i)(j).isOpened) {
            if(fields(i)(j).isBomb) {
              new ImageIcon("src/sprites/field/bomb.png")
            } else {
              if(fields(i)(j).surroundingBombs > 0) {
                new ImageIcon("src/sprites/field/" + fields(i)(j).surroundingBombs + ".png")
              } else {
                new ImageIcon("src/sprites/field/empty.png")
              }
            }
          } else {
            if(fields(i)(j).isFlagged) {
              new ImageIcon("src/sprites/field/flag.png")
            } else {
              new ImageIcon("src/sprites/field/block.png")
            }
          }

          preferredSize = new Dimension(40, 40)

          listenTo(mouse.clicks)
          reactions += {
            case MouseClicked(_, _, c, _, _) => handleFieldClick(j, i, c != 0)
          }
        }
      }

  }

  override def receiveGameEndUpdate(gameWon: Boolean, fields: Array[Array[Field]]): Unit = {
    println("GEwonne: " + gameWon);
    receiveGameFieldUpdate(fields)
  }

  def handleFieldClick(x: Int , y: Int, isRightClick: Boolean) = {
    controller.selectField((x, y), isRightClick)
  }
}
