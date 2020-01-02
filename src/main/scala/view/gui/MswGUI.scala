package scala.view.gui

import java.awt.Color
import java.awt.event.MouseEvent

import controller.GameController
import javafx.util.Pair
import javax.swing.AbstractButton
import javax.swing.border.LineBorder
import javax.swing.table.TableColumn
import model.{Difficulty, Field, GameField, GameFieldCreator}
import observer.Observer

import scala.swing.event.ButtonClicked
import scala.swing.{Action, BorderPanel, BoxPanel, Button, Dimension, FlowPanel, Frame, GridPanel, Label, MainFrame, Orientation, Swing, Table}

class MswGUI extends MainFrame with Observer{

  // Uni-codes for fancy TUI-prints
  val bombUnicode: String = "\uD83D\uDCA3"
  val squareUnicode: String = "\u2B1B"
  val flagUnicode: String = "\u2690"
  val numberPrefixUnicode: Int = 9312
  val noSurroundingBombsUnicode: String = "\u25A1"

  val label1 = new Label("Choose difficulty: ")
  val easy = new Button("EASY")
  val medium = new Button("MEDIUM")
  val hard = new Button("HARD")

  var controller:GameController = null
  var panels:List[GridPanel] = null
  var buttons: List[Button] = List.empty

  title = "Minesweeper"
  preferredSize = new Dimension(600, 600)
  resizable = false
  contents = new BorderPanel {
    add(label1, BorderPanel.Position.North)
    add(new FlowPanel() {  contents += (easy, medium, hard)  },BorderPanel.Position.Center )

    listenTo(easy, medium, hard)

    reactions += {
      case ButtonClicked(b) if b == easy =>
        startGame(1)
      case ButtonClicked(b) if b == medium =>
        startGame(2)
      case ButtonClicked(b) if b == hard =>
        startGame(3)
    }
  }

  def startGame(difficulty:Int): Unit = {

    val diff = difficulty match {
    case 1 => Difficulty.Easy
    case 2 => Difficulty.Medium
    case 3 =>  Difficulty.Medium
  }

    val creator = new GameFieldCreator
    val randomBombs = creator.createRandomBombLocations(diff)
    val gameField = creator.createGameField(diff, randomBombs)
    gameField.addGameListener(this)
    controller = new GameController(gameField)
    controller.startGame()
  }

  def redrawField(fields: Array[Array[Field]]): Unit =
  {
    label1.visible = false
    easy.visible = false
    medium.visible = false
    hard.visible = false

    val gridPanel = new GridPanel(fields(0).length, fields.length)

    if (buttons.length > 0) {
      for (b <- buttons) {
        b.action.enabled = false
        b.visible = false
      }
    }
    println(buttons.length)
    contents = new BorderPanel {
      add(gridPanel, BorderPanel.Position.Center)
    }

    for (v <- fields) {
      for (h <- v) {
        if (h.isFlagged) {
          val button = new Button(flagUnicode);
          gridPanel.contents += button;
        }
        else if (h.isOpened && h.surroundingBombs == 0) gridPanel.contents += new Button("nothingAround")
        else if (h.isOpened && h.isBomb) gridPanel.contents += new Button("bomb")
        else if (h.isOpened && h.surroundingBombs > 0)gridPanel.contents += new Button(h.surroundingBombs.toString)
        else {
          val button = new Button(Action(""){buttonClicked(h.xLocation,h.yLocation)});
          gridPanel.contents += button;
          buttons ::= button
        }
      }
    }
  }

  def buttonClicked(xLocation: Int, yLocation: Int) = controller.selectField((xLocation,yLocation), false)

  override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {
    //println("redraw")
    redrawField(fields)
  }

  override def receiveGameEndUpdate(gameWon: Boolean): Unit = null//printGameOver()
}

