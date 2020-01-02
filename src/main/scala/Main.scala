import view.tui.MswTUI

import scala.view.gui.MswGUI

object Main {
  def main(args: Array[String]): Unit = {
    val mswGUI = new MswGUI
    mswGUI.visible = true

//    val mswTUI = new MswTUI
//    mswTUI.startGame()
  }
}
