package view.gui

import javax.swing.ImageIcon

import scala.swing.{BoxPanel, Dimension, Label, Orientation}

class NumberPanel extends BoxPanel(Orientation.Horizontal) {

  contents += new Label {
    preferredSize = new Dimension(40, 55)
    icon = new ImageIcon("src/sprites/status/0.png")
  }

  contents += new Label {
    preferredSize = new Dimension(40, 55)
    icon = new ImageIcon("src/sprites/status/0.png")
  }

  contents += new Label {
    preferredSize = new Dimension(40, 55)
    icon = new ImageIcon("src/sprites/status/0.png")
  }

}
