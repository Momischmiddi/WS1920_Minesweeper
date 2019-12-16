package view.gui

import javax.swing.ImageIcon

import scala.swing.{BoxPanel, Dimension, Label, Orientation}

class NumberPanel extends BoxPanel(Orientation.Horizontal) {

  var iconPath = "src/sprites/status/"
  val labelSize = new Dimension(40, 55)

  contents += new Label {
    preferredSize = labelSize
    icon = new ImageIcon(iconPath + "0.png")
  }

  contents += new Label {
    preferredSize = labelSize
    icon = new ImageIcon(iconPath + "0.png")
  }

  contents += new Label {
    preferredSize = labelSize
    icon = new ImageIcon(iconPath + "0.png")
  }

}
