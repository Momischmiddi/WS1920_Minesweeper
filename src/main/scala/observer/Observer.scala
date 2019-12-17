package observer

import model.Field
import view.GameStatus.GameStatus

trait Observer {
  def receiveGameFieldUpdate(fields: Array[Array[Field]], gameStatus: GameStatus)
}
