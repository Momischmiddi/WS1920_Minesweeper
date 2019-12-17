package view.gui

import javax.swing.Box
import model.Difficulty

import scala.swing.{BoxPanel, Orientation}

class GameDifficultyPanel(val mainContainer: MainContainer) extends BoxPanel(Orientation.Horizontal) {
  peer.add(Box.createVerticalStrut(1))
  contents += new DiffiCultyButton("Einfach", Difficulty.Easy, mainContainer)
  peer.add(Box.createVerticalStrut(1))
  contents += new DiffiCultyButton("Mittel", Difficulty.Medium, mainContainer)
  peer.add(Box.createVerticalStrut(1))
  contents += new DiffiCultyButton("Schwer", Difficulty.Hard, mainContainer)
  peer.add(Box.createVerticalStrut(1))
}
