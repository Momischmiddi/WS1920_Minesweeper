import view.tui.MswTUI

object Main {
  def main(args: Array[String]): Unit = {
//    val mswGUI = new MswGUI
//    mswGUI.visible = true

    val mswTUI = new MswTUI
    mswTUI.startGame()
  }
}
