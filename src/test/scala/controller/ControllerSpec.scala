package controller

import model.{Difficulty, Field}
import observer.Observer
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterEach, WordSpec}
import traits.TestBase

class ControllerSpec extends WordSpec with TestBase with BeforeAndAfterEach  {

  var(_, _, gameField, gameController) = createTestObjects(Difficulty.Easy, true)

  var correctEventFired: Boolean = false
  var currentTest = ""

  override def afterEach(): Unit = {
    gameField.removeAllGameListeners()
    if(!correctEventFired) {
      System.err.println("Failed test: " + currentTest)
    }
    correctEventFired should be(true)
  }

  override def beforeEach(): Unit = {
    val(_, _, gameField_, gameController_) = createTestObjects(Difficulty.Easy, true)
    gameField = gameField_
    gameController = gameController_

    correctEventFired = false
  }

  "A game" should {
    "Should be over and won, if all non-bomb-fields have been opened" in {
      val observer = new Observer {
        currentTest = "Should be over and won, if all non-bomb-fields have been opened"
        override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {}

        override def receiveGameEndUpdate(gameWon: Boolean): Unit = {
          correctEventFired = true
          gameWon should be(true)
        }
      }

      gameField.addGameListener(observer)

      gameController.selectField((0, 0), false)
      gameController.selectField((8, 8), false)
      gameController.selectField((8, 3), false)
      gameController.selectField((6, 4), false)
      gameController.selectField((0, 8), false)
      gameController.selectField((1, 8), false)
      gameController.selectField((0, 6), false)
      gameController.selectField((5, 4), false)
    }

    "Should be over and lost, if a bomb-field has been opened" in {
      currentTest = "Should be over and lost, if a bomb-field has been opened"
      val observer = new Observer {
        override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {}

        override def receiveGameEndUpdate(gameWon: Boolean): Unit = {
          correctEventFired = true
          gameWon should be(false)
        }
      }

      gameField.addGameListener(observer)
      gameController.selectField((0, 4), false)
    }

    "Should not be over, if there are non-bomb-fields left on the game-field and a non-bomb-field was opened" in {
      currentTest = "Should not be over, if there are non-bomb-fields left on the game-field and a non-bomb-field was opened"
      val observer = new Observer {
        override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {
          correctEventFired = true
          fields(4)(1).surroundingBombs should be(3)
          fields(4)(1).isOpened should be(true)
        }

        override def receiveGameEndUpdate(gameWon: Boolean): Unit = {
          true should be(false) // This method should not be called, since game is not over
        }
      }

      gameField.addGameListener(observer)
      gameController.selectField((1, 4), false)
    }

    "Should not be over, if a bomb was flagged" in {
      currentTest = "Should not be over, if a bomb was flagged"
      val observer = new Observer {
        override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {
          correctEventFired = true
          fields(4)(0).isOpened should be(false)
          fields(4)(0).isFlagged should be(true)
        }

        override def receiveGameEndUpdate(gameWon: Boolean): Unit = {
          true should be(false) // This method should not be called, since game is not over
        }
      }

      gameField.addGameListener(observer)
      gameController.selectField((0, 4), true)
    }

    "Should not be over, if flagged bomb was opened" in {
      currentTest = "Should not be over, if flag was opened"
      gameController.selectField((0, 4), true)

      val observer = new Observer {
        override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {
          correctEventFired = true
        }

        override def receiveGameEndUpdate(gameWon: Boolean): Unit = {
          true should be(false) // This method should not be called, since game is not over
        }
      }

      gameField.addGameListener(observer)
      gameController.selectField((0, 4), true)
      gameController.selectField((0, 4), false)
    }
  }
}
