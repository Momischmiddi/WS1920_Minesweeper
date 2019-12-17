package view.gui

import java.awt.Font

import model.Difficulty.Difficulty

import scala.swing.Button
import scala.swing.event.MouseClicked

class DiffiCultyButton(buttonText: String, difficulty: Difficulty, mainContainer: MainContainer) extends Button {

  text = buttonText
  font = new Font("Arial", Font.PLAIN, 18)
  listenTo(mouse.clicks)
  reactions += {
    case MouseClicked(_, _, _, _, _) => handleClick()
  }

  def handleClick(): Unit = {
    mainContainer.restart(difficulty)
  }
}