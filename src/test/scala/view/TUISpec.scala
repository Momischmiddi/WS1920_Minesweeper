package view

import model.GameStatus.GameStatus
import model.{FieldMatrix, GameStatus}
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterEach, WordSpec}
import traits.TestBase
import view.tui.MswTUI
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class TUISpec extends WordSpec with TestBase with BeforeAndAfterEach  {

  var(fieldMatrix, bombs, model, controller) = createTestObjects()
  val tui = new MswTUI(controller, model)

  override def beforeEach(): Unit = {
    val (_fieldMatrix, _bombs, _model, _controller) = createTestObjects()

    fieldMatrix = _fieldMatrix
    bombs = _bombs
    model = _model
    controller = _controller
  }


  "A TUI" should {
    "render a new-move-header string if the game still is in progress" in {
      val out = tui.createHeader(GameStatus.InProgress)

      out should be(
        "\n-------------------------------------------------------\n" +
          "*********************** New move **********************\n" +
          "-------------------------------------------------------\n"
      )
    }

    "render a game-lost-header string if the game is lost" in {
      val out = tui.createHeader(GameStatus.Lost)

      out should be(
        "\n-------------------------------------------------------\n" +
          "***********************!You lost!**********************\n" +
          "-------------------------------------------------------\n"
      )
    }

    "render a game-won-header string if the game is lost" in {
      val out = tui.createHeader(GameStatus.Won)

      out should be(
        "\n-------------------------------------------------------\n" +
          "***********************!You won!***********************\n" +
          "-------------------------------------------------------\n"
      )
    }

    "render a flag, if a field was flagged" in {
      model.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
        val out = tui.createField(fieldMatrix)

        out.charAt(0).toString should be("\u2690")
      })

      controller.handleClick(0, 0, fieldMatrix, true)
    }

    "render an empty field, if an empty field was selected" in {
      model.addGameListener((fieldMatrix: FieldMatrix, gameStatus: GameStatus) => {
        val out = tui.createField(fieldMatrix)

        out.charAt(0).toString should be("\u25A1")
      })

      controller.handleClick(0, 0, fieldMatrix, false)
    }

    "print to the console when the model has changed" in {
      // Pipe stdout to own output
      val newOut = new ByteArrayOutputStream
      val oldOut = System.out
      System.setOut(new PrintStream(newOut))

      tui.gameFieldUpdated(fieldMatrix, GameStatus.InProgress)
      // Reset to stdout
      System.setOut(oldOut)

      newOut.toString() should not be ""
    }

    "print a String to the standard output" in {
      val testOutput = "TestOutput"

      // Pipe stdout to own output
      val newOut = new ByteArrayOutputStream
      val oldOut = System.out
      System.setOut(new PrintStream(newOut))

      tui.render(testOutput);

      // Reset to stdout
      System.setOut(oldOut)
      newOut.toString() should be(testOutput)
    }
  }
}