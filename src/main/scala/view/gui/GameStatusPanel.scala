package view.gui

import java.awt.Color

import javax.swing.{Box, ImageIcon}
import model.Difficulty.Difficulty
import view.GameStatus
import view.GameStatus.GameStatus

import scala.swing.event.MouseClicked
import scala.swing.{BoxPanel, Dimension, Label, Orientation}

class GameStatusPanel(gameStatus: GameStatus, mainContainer: MainContainer, difficulty: Difficulty) extends BoxPanel(Orientation.Horizontal) {

  background = new Color(192, 192, 192)

  peer.add(Box.createVerticalStrut(1))
  contents += new Label {
    val spriteName = gameStatus match {
      case GameStatus.InProgress => "happy"
      case GameStatus.Won => "cool"
      case GameStatus.Lost => "sad"
    }

    icon = new ImageIcon("src/sprites/status/" + spriteName + ".png")
    preferredSize = new Dimension(55, 55)

    listenTo(mouse.clicks)
    reactions += {
      case MouseClicked(_, _, _, _, _) => handleClick()
    }
  }

  peer.add(Box.createVerticalStrut(1))

  def handleClick(): Unit = {
    mainContainer.restart(difficulty)
  }
}
