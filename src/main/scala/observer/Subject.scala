package observer

import model.{Field}

trait Subject {

  private var observers: List[Observer] = Nil

  def addGameChangeListener(observer: Observer) = observers = observer :: observers
  def removeGameChangeListener(observer: Observer) = observers = observers diff List(observer)
  def fireGameChangeEvent(fields: Array[Array[Field]]) = observers.foreach(_.receiveUpdate(fields))

}
