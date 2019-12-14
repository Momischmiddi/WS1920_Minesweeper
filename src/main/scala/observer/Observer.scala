package observer

import model.{Field}

trait Observer {
  def receiveGameFieldUpdate(fields: Array[Array[Field]])
  def receiveGameEndUpdate(gameWon: Boolean, fields: Array[Array[Field]])
}
