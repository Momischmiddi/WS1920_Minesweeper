package controller

import model.{Difficulty, Field}
import org.scalatest.Matchers._
import org.scalatest.WordSpec
import traits.TestBase

class ControllerSpec extends WordSpec with TestBase {

  val(_, _, gameField, gameController) = createTestObjects(Difficulty.Easy, false)

  "A game" should {
    "Should be over and won, if all non-bomb-fields have been opened" in {
      true should be(false)
      // TODO: Add full game-cycle here.
    }

    "Should be over and lost, if a bomb-field has been opened" in {
      gameField.addGameChangeListener((fields: Array[Array[Field]]) =>
        fields(4)(0).surroundingBombs should be(-1)
      )

      val clickResult = gameController.openField((0, 4), false)

      clickResult._1 should be(true)
      clickResult._2 should be(false)
    }

    "Should not be over, if there are non-bomb-fields left on the game-field and a non-bomb-field was opened" in {
      gameField.addGameChangeListener((fields: Array[Array[Field]]) =>
        fields(4)(1).surroundingBombs should be(-1)
      )

      val clickResult = gameController.openField((1, 4), false)

      clickResult._1 should be(false)
      clickResult._2 should be(false)
    }

    "Should not be over, if a bomb was flagged" in {
      gameField.addGameChangeListener((fields: Array[Array[Field]]) =>
        fields(4)(0).surroundingBombs should be(-1)
      )

      val clickResult = gameController.openField((0, 4), true)

      clickResult._1 should be(false)
      clickResult._2 should be(false)
    }

    "Should not be over, if flag was opened" in {
      gameController.openField((0, 4), true)

      gameField.addGameChangeListener((fields: Array[Array[Field]]) =>
        fields(4)(0).surroundingBombs should be(-1)
      )

      val clickResult = gameController.openField((0, 4), false)

      clickResult._1 should be(false)
      clickResult._2 should be(false)
    }
  }
}
