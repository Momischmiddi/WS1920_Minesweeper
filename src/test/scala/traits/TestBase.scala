package traits

import model.Difficulty.Difficulty
import model.{GameField, GameFieldCreator}

trait TestBase {

  def createTestObjects(difficulty: Difficulty, useTestBombs: Boolean): (GameFieldCreator, List[(Int, Int)], GameField) = {
    val creator = new GameFieldCreator
    val bombs: List[(Int, Int)] = if(useTestBombs) {
      createTestBombs()
    } else {
      creator.createRandomBombLocations(difficulty)
    }

    (creator, bombs, creator.createGameField(difficulty, bombs))
  }

  private def createTestBombs(): List[(Int, Int)] = {
    List(
      // DO NOT MODIFY! Tests will fail if you do.
      (0,4), (0, 5), (0, 7), (1, 7), (2, 3), (4, 4), (5, 5 ), (6, 3), (6, 5), (7, 3)
    )
  }

}
