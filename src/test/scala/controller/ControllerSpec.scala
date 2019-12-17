package controller

import model.{Difficulty, Field}
import observer.Observer
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterEach, WordSpec}
import traits.TestBase

class ControllerSpec extends WordSpec with TestBase with BeforeAndAfterEach  {

  var(_, _, gameField, gameController) = createTestObjects(Difficulty.Easy, true)

  override def afterEach(): Unit = {
    gameField.removeAllGameListeners()
  }

  override def beforeEach(): Unit = {
    val(_, _, gameField_, gameController_) = createTestObjects(Difficulty.Easy, true)
    gameField = gameField_
    gameController = gameController_
  }

  /*
  "A game" should {
    "Should be over and won, if all non-bomb-fields have been opened" in {
      var evtCounter = 0
      val observer = new Observer {
        override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {}

        override def receiveGameEndUpdate(gameWon: Boolean, fields: Array[Array[Field]]): Unit = {
          if(evtCounter == 0) {
            gameWon should be(true)
          } else {
            gameWon should be(false)
          }
          evtCounter = evtCounter + 1
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

      observer.receiveGameEndUpdate(false, gameField.fields)
    }

    "Should be over and lost, if a bomb-field has been opened" in {
      var evtCounter = 0
      val observer = new Observer {
        override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {}
        override def receiveGameEndUpdate(gameWon: Boolean, fields: Array[Array[Field]]): Unit = {
          if(evtCounter == 0) {
            gameWon should be(false)
          } else {
            gameWon should be(true)
          }
          evtCounter = evtCounter + 1
        }
      }

      gameField.addGameListener(observer)
      gameController.selectField((0, 4), false)
      observer.receiveGameEndUpdate(true, gameField.fields)
    }

    "Should not be over, if there are non-bomb-fields left on the game-field and a non-bomb-field was opened" in {
      var evtCounter = 0
      val observer = new Observer {
        override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {
          if(evtCounter == 0) {
            fields(4)(1).surroundingBombs should be(3)
            fields(4)(1).isOpened should be(true)
          } else {
            fields should equal(null)
          }

          evtCounter = evtCounter + 1
        }

        override def receiveGameEndUpdate(gameWon: Boolean, fields: Array[Array[Field]]): Unit = {
          true should be(false) // This method should not be called, since game is not over
        }
      }

      gameField.addGameListener(observer)
      gameController.selectField((1, 4), false)
      observer.receiveGameFieldUpdate(null)
    }

    "Should not be over, if a bomb was flagged" in {
      var evtCounter = 0
      val observer = new Observer {
        override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {
          if(evtCounter == 0) {
            fields(4)(0).isOpened should be(false)
            fields(4)(0).isFlagged should be(true)
          } else {
            fields should equal(null)
          }

          evtCounter = evtCounter + 1
        }

        override def receiveGameEndUpdate(gameWon: Boolean, fields: Array[Array[Field]]): Unit = {
          true should be(false) // This method should not be called, since game is not over
        }
      }

      gameField.addGameListener(observer)
      gameController.selectField((0, 4), true)
      observer.receiveGameFieldUpdate(null)
    }

    "Should not be over, if flagged bomb was opened" in {
      gameController.selectField((0, 4), true)
      val observer = new Observer {
        override def receiveGameFieldUpdate(fields: Array[Array[Field]]): Unit = {
        }

        override def receiveGameEndUpdate(gameWon: Boolean, fields: Array[Array[Field]]): Unit = {
          true should be(false) // This method should not be called, since the game is not over
        }
      }

      gameField.addGameListener(observer)
      gameController.selectField((0, 4), true)
      gameController.selectField((0, 4), false)
    }
  }
  */
}
