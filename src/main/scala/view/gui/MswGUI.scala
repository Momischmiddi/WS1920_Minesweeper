package scala.view.gui

import controller.GameController
import javax.swing.ImageIcon
import model.{Field, GameField}
import observer.Observer
import view.gui.{GameFieldGrid, MainContainer}

import scala.swing.event.{ButtonClicked, MouseClicked}
import scala.swing.{AbstractButton, Button, Dimension, GridPanel, Label, MainFrame}

class MswGUI(controller: GameController, val gameField: GameField) extends MainFrame {

  var x = 9
  var y = 9

  title = "Minesweeper"
  iconImage = new ImageIcon("src/sprites/frameicon.png").getImage

  contents = new MainContainer(x, y, controller, gameField)

  pack()
  centerOnScreen()
  open()
}

