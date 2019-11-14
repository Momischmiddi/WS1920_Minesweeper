package model

import model.Difficulty.Difficulty
import org.scalatest.Matchers._
import org.scalatest.WordSpec

class GameFieldSpec extends WordSpec  {

  def createTestField(difficulty: Difficulty): GameField = {
    val creator = new GameFieldCreator
    val bombLocations = creator.createRandomBombLocations(difficulty)
    creator.createGameField(difficulty, bombLocations)
  }

  "A game-field" should {
    "should contain the correct amount of fields" in {
      createTestField(Difficulty.Easy).fields.flatten.length should be(Difficulty.Easy._1 * Difficulty.Easy._2)
      createTestField(Difficulty.Medium).fields.flatten.length should be(Difficulty.Medium._1 * Difficulty.Medium._2)
      createTestField(Difficulty.Hard).fields.flatten.length should be(Difficulty.Hard._1 * Difficulty.Hard._2)
    }

    "should contain the correct amount of bombs" in {
      createTestField(Difficulty.Easy).fields.flatten.count(_.isBomb) should be(Difficulty.Easy._3)
      createTestField(Difficulty.Medium).fields.flatten.count(_.isBomb) should be(Difficulty.Medium._3)
      createTestField(Difficulty.Hard).fields.flatten.count(_.isBomb) should be(Difficulty.Hard._3)
    }
  }
}
