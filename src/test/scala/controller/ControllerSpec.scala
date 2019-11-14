package controller

import model.Difficulty
import org.scalatest.Matchers._
import org.scalatest.WordSpec
import traits.TestBase

class ControllerSpec extends WordSpec with TestBase {

  val(creator, bombs, gameField) = createTestObjects(Difficulty.Easy, false)

  "A game" should {
    "Should be over and won, if all non-bomb-fields have been selected" in {

    }

    "Should be over and lost, if bomb-fields has been selected" in {

    }

    "Should not be over, if there are non-bomb-fields left on the game-field and a non-bomb-field was opened" in {

    }

    "Should not be over, if a bomb was flagged" in {

    }

    "Should not be over, if flag was selected" in {

    }
  }
}
