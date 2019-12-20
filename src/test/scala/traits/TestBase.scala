package traits

import controller.Controller
import model.{Creator, Difficulty, FieldMatrix, Model}

trait TestBase {

  def createTestObjects(): (FieldMatrix, List[(Int, Int)], Model, Controller) = {
    val bombs = createTestBombs()
    val model = new Model(Difficulty.Easy)
    val controller = new Controller(model)

    (new Creator().create(Difficulty.Easy, bombs), bombs, model, controller)
  }

  private def createTestBombs(): List[(Int, Int)] = {
    List(
      // DO NOT MODIFY! Tests will fail if you do.
      (0, 4), (0, 5), (0, 7), (1, 7), (2, 3), (4, 4), (5, 5), (6, 3), (6, 5), (7, 3)
    )
  }
}
