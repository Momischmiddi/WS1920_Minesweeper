package scala.view.gui

import controller.GameController
import javax.swing.ImageIcon
import model.{Field, GameField}
import observer.Observer

import scala.swing.event.{ButtonClicked, MouseClicked}
import scala.swing.{AbstractButton, Button, Dimension, GridPanel, Label, MainFrame}

class MswGUI(controller: GameController, val gameField: GameField) extends MainFrame with Observer {

  gameField.addGameListener(this)

  var x = 9
  var y = 9

  title = "Minesweeper"

  contents = new GridPanel(9, 9) {
    contents ++= (for {i <- 0 until x; j <- 0 until y } yield new Label {
      icon = new ImageIcon("src/sprites/block.png")
      preferredSize = new Dimension(40, 40)

      listenTo(mouse.clicks)
      reactions += {
        case MouseClicked(_, _, c, _, _) => handleFieldClick(j, i, c != 0)
      }
    })
  }

  pack()
  centerOnScreen()
  open()

  def handleFieldClick(x: Int , y: Int, isRightClick: Boolean) = {
    controller.selectField((x, y), isRightClick)
  }

  private def printGameOver(): Unit = {
    println("------------------------------------")
    println("************!GAME OVER!*************")
    println("------------------------------------")
  }

  override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {
    contents = new GridPanel(9, 9) {
      contents ++= {
        for {i <- 0 until x; j <- 0 until y} yield new Label {
          icon = if(fields(i)(j).isOpened) {
            if(fields(i)(j).isBomb) {
              new ImageIcon("src/sprites/bomb.png")
            } else {
              if(fields(i)(j).surroundingBombs > 0) {
                new ImageIcon("src/sprites/" + fields(i)(j).surroundingBombs + ".png")
              } else {
                new ImageIcon("src/sprites/empty.png")
              }
            }
          } else {
            if(fields(i)(j).isFlagged) {
              new ImageIcon("src/sprites/flag.png")
            } else {
              new ImageIcon("src/sprites/block.png")
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
  }

  override def receiveGameEndUpdate(gameWon: Boolean, fields: Array[Array[Field]]): Unit = {
    println("GEwonne: " + gameWon);
    receiveGameFieldUpdate(fields)
  }
}

