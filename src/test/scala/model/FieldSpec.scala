package model

import org.scalatest.Matchers._
import org.scalatest.WordSpec
import traits.TestBase

class FieldSpec extends WordSpec with TestBase {

  val(creator, bombs, gameField) = createTestObjects(Difficulty.Easy, false)

  "A field" should {
    "should contain the correct amount of surrounding bombs" in {

    }

    "should contain the correct amount of surrounding fields" in {

    }

    "should be marked as a bomb, if its a bomb" in {

    }

    "should be marked as no bomb, if its not a bomb" in {

    }

    "should opened and not flagged if no bomb was clicked and the action was no flag" in {

    }

    "should contain a number, be opened and not flagged if no bomb was clicked and there are bombs as neighbours" in {

    }
  }
}
