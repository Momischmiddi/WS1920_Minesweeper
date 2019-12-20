import model.GameStatus.GameStatus
import model.{Field, FieldMatrix, GameStatus}
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterEach, WordSpec}

class GameSpec extends WordSpec with TestBase with BeforeAndAfterEach  {

  var(fieldMatrix, bombs, model, controller) = createTestObjects()

  override def beforeEach(): Unit = {
    val (_fieldMatrix, _bombs, _model, _controller) = createTestObjects()

    fieldMatrix = _fieldMatrix
    bombs = _bombs
    model = _model
    controller = _controller
  }

  "A game" should {
    "be over and contain open bombs, if a bomb was selected" in {
      model.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
        gameStatus should be(GameStatus.Lost)
        validateOpenBomb(4, 0, fieldMatrix)
        validateOpenBomb(5, 0, fieldMatrix)
        validateOpenBomb(7, 0, fieldMatrix)
        validateOpenBomb(7, 1, fieldMatrix)
        validateOpenBomb(3, 2, fieldMatrix)
        validateOpenBomb(4, 4, fieldMatrix)
        validateOpenBomb(5, 5, fieldMatrix)
        validateOpenBomb(3, 6, fieldMatrix)
        validateOpenBomb(5, 6, fieldMatrix)
        validateOpenBomb(3, 7, fieldMatrix)

        fieldMatrix.get(4, 0).isRedBomb should be(true)
      })

      controller.handleClick(0, 4, fieldMatrix, false)
    }
  }

  "A game" should {
    var moveCtr = 0
    "be won, if a non bomb field was selected last" in {
      model.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
        if(moveCtr == 0) {
          moveCtr += 1
          controller.handleClick(0, 8, fieldMatrix, false)
        } else if(moveCtr == 1) {
          moveCtr += 1
          controller.handleClick(6, 4, fieldMatrix, false)
        } else if(moveCtr == 2) {
          moveCtr += 1
          controller.handleClick(8, 3, fieldMatrix, false)
        } else if(moveCtr == 3) {
          moveCtr += 1
          controller.handleClick(4, 6, fieldMatrix, false)
        } else if(moveCtr == 4) {
          moveCtr += 1
          controller.handleClick(8, 3, fieldMatrix, false)
        } else if(moveCtr == 5) {
          moveCtr += 1
          controller.handleClick(6, 0, fieldMatrix, false)
        } else {
          gameStatus should be(GameStatus.Won)
        }
      })

      controller.handleClick(0, 0, fieldMatrix, false)
    }
  }

  // Tests a full gamecycle
  "A game" should {
    var moveCtr = 0
    "be won, if all non-bomb-fields have been selected" in {
      model.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
        if(moveCtr == 0) {
          moveCtr += 1
          gameStatus should be(GameStatus.InProgress)
          for(i <- 0 until 9){
            validateEmpty(fieldMatrix.get(0, i))
            validateEmpty(fieldMatrix.get(1, i))
          }

          validateEmpty(fieldMatrix.get(2, 0))
          validateNumber(fieldMatrix.get(2, 1), 1)
          validateNumber(fieldMatrix.get(2, 2), 1)
          validateNumber(fieldMatrix.get(2, 3), 1)
          validateEmpty(fieldMatrix.get(2, 4))
          validateNumber(fieldMatrix.get(2, 5), 1)
          validateNumber(fieldMatrix.get(2, 6), 2)
          validateNumber(fieldMatrix.get(2, 7), 2)
          validateNumber(fieldMatrix.get(2, 8), 1)

          validateNumber(fieldMatrix.get(3, 0), 1)
          validateNumber(fieldMatrix.get(3, 1), 2)
          validateNumber(fieldMatrix.get(3, 3), 2)
          validateNumber(fieldMatrix.get(3, 4), 1)
          validateNumber(fieldMatrix.get(3, 5), 2)
          controller.handleClick(0, 8, fieldMatrix, false)
        } else if(moveCtr == 1) {
          moveCtr += 1
          gameStatus should be(GameStatus.InProgress)
          validateNumber(fieldMatrix.get(8, 0), 2)
          validateNumber(fieldMatrix.get(8, 1), 2)
          controller.handleClick(6, 4, fieldMatrix, false)
        } else if(moveCtr == 2) {
          moveCtr += 1
          gameStatus should be(GameStatus.InProgress)
          validateNumber(fieldMatrix.get(4, 5), 4)
          validateNumber(fieldMatrix.get(4, 6), 4)
          validateNumber(fieldMatrix.get(4, 7), 3)
          validateNumber(fieldMatrix.get(5, 7), 1)
          controller.handleClick(8, 3, fieldMatrix, false)
        } else if(moveCtr == 3) {
          moveCtr += 1
          gameStatus should be(GameStatus.InProgress)
          validateNumber(fieldMatrix.get(3, 8), 1)
          validateNumber(fieldMatrix.get(4, 8), 1)
          controller.handleClick(0, 6, fieldMatrix, false)
        } else if(moveCtr == 4) {
          moveCtr +=1
          gameStatus should be(GameStatus.InProgress)
          validateNumber(fieldMatrix.get(6, 0), 3)
          validateNumber(fieldMatrix.get(6, 1), 3)
          validateNumber(fieldMatrix.get(5, 1), 2)
          controller.handleClick(6, 6, fieldMatrix, false)
        } else if(moveCtr == 5) {
          gameStatus should be(GameStatus.Won)
        }
      })

      controller.handleClick(0, 0, fieldMatrix, false)
    }
  }

  def validateEmpty(field: Field): Unit = {
    field.isOpened should be(true)
    field.isBomb should be(false)
    field.surroundingBombs should be(0)
    field.isFlagged should be(false)
  }

  def validateNumber(field: Field, numb: Int): Unit = {
    field.isOpened should be(true)
    field.isBomb should be(false)
    field.surroundingBombs should be(numb)
    field.isFlagged should be(false)
  }

  def validateOpenBomb(x: Int, y: Int, fieldMatrix: FieldMatrix) = {
    fieldMatrix.get(x, y).isBomb should be(true)
    fieldMatrix.get(x, y).isOpened should be(true)
  }
}