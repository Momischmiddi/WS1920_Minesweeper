package observerpattern

import model.FieldMatrix
import model.GameStatus.GameStatus

trait Observer {
  def gameFieldUpdated(fieldMatrix: FieldMatrix, gameStatus: GameStatus)
}
