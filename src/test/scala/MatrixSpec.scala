import model.GameStatus.GameStatus
import model.{Creator, Difficulty, FieldMatrix}
import org.scalatest.Matchers._
import org.scalatest.WordSpec

class MatrixSpec extends WordSpec with TestBase {

  val creator = new Creator
  val easyBombs = creator.createRandomBombLocations(Difficulty.Easy)
  val mediumBombs = creator.createRandomBombLocations(Difficulty.Medium)
  val hardBombs = creator.createRandomBombLocations(Difficulty.Hard)

  "A created matrix" should {
    "Contain the correct amount of elements for an easy field" in {
      val matrix = creator.create(Difficulty.Easy, easyBombs)
      matrix.fields.flatten.length should be(Difficulty.Easy._1 * Difficulty.Easy._2)
    }

    "Contain the correct amount of fields for a medium field" in {
      val matrix = creator.create(Difficulty.Medium, easyBombs)
      matrix.fields.flatten.length should be(Difficulty.Medium._1 * Difficulty.Medium._2)
    }

    "Contain the correct amount of fields for a hard field" in {
      val matrix = creator.create(Difficulty.Hard, easyBombs)
      matrix.fields.flatten.length should be(Difficulty.Hard._1 * Difficulty.Hard._2)
    }

    "Contain bombs at the right spots" in {
      val testObjects = createTestObjects()

      testObjects._1.get(4, 0).isBomb should be(true)
      testObjects._1.get(5, 0).isBomb should be(true)
      testObjects._1.get(7, 0).isBomb should be(true)
      testObjects._1.get(7, 1).isBomb should be(true)
      testObjects._1.get(3, 2).isBomb should be(true)
      testObjects._1.get(4, 4).isBomb should be(true)
      testObjects._1.get(5, 5).isBomb should be(true)
      testObjects._1.get(3, 6).isBomb should be(true)
      testObjects._1.get(5, 6).isBomb should be(true)
      testObjects._1.get(3, 7).isBomb should be(true)
    }
  }

  "Only contain non-opened fields after creation" in {
    createTestObjects()._1.fields.foreach(xF => xF.foreach(yF => yF.isOpened should be(false)))
  }

  "Only contain non-flagged fields after creation" in {
    createTestObjects()._1.fields.foreach(xF => xF.foreach(yF => yF.isFlagged should be(false)))
  }

  "Should not have any field with neighbours after creations" in {
    createTestObjects()._1.fields.foreach(xF => xF.foreach(yF => yF.surroundingBombs should be(-1)))
  }

  "Should contain no red bomb after creation" in {
    createTestObjects()._1.fields.foreach(xF => xF.foreach(yF => yF.isRedBomb should be(false)))
  }

  "Replace a field at the right position" in {
    val testObjects = createTestObjects

    var cnt = 0

    testObjects._3.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
      if(cnt == 0) {
        cnt += 1
        fieldMatrix.get(0, 0).isOpened should be(true)
        testObjects._4.handleClick(0, 1, fieldMatrix, false)
      } else if(cnt == 1) {
        cnt += 1
        fieldMatrix.get(1, 0).isOpened should be(true)
        fieldMatrix.get(0, 0).isOpened should be(true)
        testObjects._4.handleClick(1, 0, fieldMatrix, false)
      } else if(cnt == 2) {
        cnt += 1
        fieldMatrix.get(1, 0).isOpened should be(true)
        fieldMatrix.get(1, 0).isOpened should be(true)
        fieldMatrix.get(0, 0).isOpened should be(true)
        testObjects._4.handleClick(1, 1, fieldMatrix, false)
      } else if(cnt == 3) {
        cnt += 1
        fieldMatrix.get(1, 1).isOpened should be(true)
        fieldMatrix.get(1, 0).isOpened should be(true)
        fieldMatrix.get(1, 0).isOpened should be(true)
        fieldMatrix.get(0, 0).isOpened should be(true)
        testObjects._4.handleClick(7, 1, fieldMatrix, false)
      } else if(cnt == 4) {
        fieldMatrix.get(1, 1).isOpened should be(true)
        fieldMatrix.get(1, 0).isOpened should be(true)
        fieldMatrix.get(1, 0).isOpened should be(true)
        fieldMatrix.get(0, 0).isOpened should be(true)
        fieldMatrix.get(1, 7).isOpened should be(true)
      }
    })

    testObjects._1.get(0, 0).isOpened should be(false)
    testObjects._1.get(0, 1).isOpened should be(false)
    testObjects._1.get(1, 0).isOpened should be(false)
    testObjects._1.get(1, 1).isOpened should be(false)
    testObjects._1.get(7, 1).isOpened should be(false)

    testObjects._4.handleClick(0, 0, testObjects._1, false)
  }
}
