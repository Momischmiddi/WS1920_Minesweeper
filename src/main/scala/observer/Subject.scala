package observer

import model.Field
import view.GameStatus.GameStatus

trait Subject {

  private var observers: List[Observer] = Nil

  def addGameListener(observer: Observer): Unit = observers = observer :: observers
  def getGameListeners(): List[Observer] = observers
  def removeGameListener(observer: Observer): Unit= observers = observers diff List(observer)
  def removeAllGameListeners(): Unit = observers = observers.diff(observers)
  def fireFieldChangeEvent(fields: Array[Array[Field]], gameStatus: GameStatus): Unit = observers.foreach(_.receiveGameFieldUpdate(fields, gameStatus))

}
