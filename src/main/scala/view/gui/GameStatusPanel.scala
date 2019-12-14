package view.gui

import java.awt.Color

import javax.swing.{Box, ImageIcon}

import scala.swing.{BoxPanel, Dimension, Label, Orientation}

class GameStatusPanel extends BoxPanel(Orientation.Horizontal) {

  background = new Color(192, 192, 192)
  peer.add(Box.createVerticalStrut(1))
  contents += new NumberPanel
  peer.add(Box.createVerticalStrut(1))
  contents += new Label {
    icon = new ImageIcon("src/sprites/status/happy.png")
    preferredSize = new Dimension(55, 55)
  }

  peer.add(Box.createVerticalStrut(1))
  contents += new NumberPanel
  peer.add(Box.createVerticalStrut(1))
}
