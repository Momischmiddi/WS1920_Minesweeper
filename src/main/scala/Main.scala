import model.Difficulty
import view.gui.Setup

object Main {
  def main(args: Array[String]): Unit = {
    new Setup().start(Difficulty.Easy)
  }
}
