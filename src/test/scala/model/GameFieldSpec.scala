package model

import org.scalatest.Matchers._
import org.scalatest.WordSpec
import traits.TestBase

class GameFieldSpec extends WordSpec  with TestBase {

  val(_, _, easyField, _) = createTestObjects(Difficulty.Easy, false)
  val(_, _, mediumField, _) = createTestObjects(Difficulty.Medium, false)
  val(_, _, hardField, _) = createTestObjects(Difficulty.Hard, false)

  "A game-field" should {
    "should contain the correct amount of fields" in {
      easyField.fields.flatten.length should be(Difficulty.Easy._1 * Difficulty.Easy._2)
      mediumField.fields.flatten.length should be(Difficulty.Medium._1 * Difficulty.Medium._2)
      hardField.fields.flatten.length should be(Difficulty.Hard._1 * Difficulty.Hard._2)
    }

    "should contain the correct amount of bombs" in {
      easyField.fields.flatten.count(_.isBomb) should be(Difficulty.Easy._3)
      mediumField.fields.flatten.count(_.isBomb) should be(Difficulty.Medium._3)
      hardField.fields.flatten.count(_.isBomb) should be(Difficulty.Hard._3)
    }
  }
}
