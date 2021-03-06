package functionality

import model.GameStatus.GameStatus
import model.{FieldMatrix, GameStatus}
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterEach, WordSpec}
import traits.TestBase

class ModelSpec extends WordSpec with TestBase with BeforeAndAfterEach  {

  var(fieldMatrix, bombs, model, controller) = createTestObjects()

  override def beforeEach(): Unit = {
    val (_fieldMatrix, _bombs, _model, _controller) = createTestObjects()

    fieldMatrix = _fieldMatrix
    bombs = _bombs
    model = _model
    controller = _controller
  }

  "A model" should {
    "fire an event, if it was modified" in {
      model.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
        true should be(true)
      })

      controller.handleClick(0, 0, fieldMatrix, false)
    }

    "open a field, if it was selected" in {
      model.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
        fieldMatrix.get(0, 0).isOpened should be(true)
      })

      controller.handleClick(0, 0, fieldMatrix, false)
    }

    "not open a field, if it was flagged" in {
      model.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
        fieldMatrix.get(0, 0).isOpened should be(false)
      })

      controller.handleClick(0, 0, fieldMatrix, true)
    }

    "flag a field, if it was flagged" in {
      model.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
        fieldMatrix.get(0, 0).isFlagged should be(true)
      })

      controller.handleClick(0, 0, fieldMatrix, true)
    }

    "finish the game, if a bombs was selected" in {
      model.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
        gameStatus should be(GameStatus.Lost)
      })

      controller.handleClick(0, 4, fieldMatrix, false)
    }

    "not finish the game, if a bombs was flagged" in {
      model.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
        gameStatus should be(GameStatus.InProgress)
      })

      controller.handleClick(0, 4, fieldMatrix, true)
    }
  }
}