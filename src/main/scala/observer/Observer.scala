package observer

import model.{Field}

trait Observer {
  def receiveUpdate(fields: Array[Array[Field]])
}
