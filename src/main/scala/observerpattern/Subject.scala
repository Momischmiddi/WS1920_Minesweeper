package observerpattern

import model.FieldMatrix
import model.GameStatus.GameStatus

trait Subject {

  private var observers: List[Observer] = Nil

  def addGameListener(observer: Observer): Unit = observers = observer :: observers
  def getGameListeners(): List[Observer] = observers
  def removeGameListener(observer: Observer): Unit= observers = observers diff List(observer)
  def removeAllGameListeners(): Unit = observers = observers.diff(observers)
  def fireFieldUpdated(fieldMatrix: FieldMatrix, gameStatus: GameStatus): Unit = observers.foreach(_.gameFieldUpdated(fieldMatrix, gameStatus))

}
