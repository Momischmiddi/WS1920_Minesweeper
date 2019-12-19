package model

case class Field(xLocation: Int, yLocation: Int, isBomb: Boolean = false,
                 isFlagged: Boolean = false, isOpened: Boolean = false,
                 surroundingBombs: Int = -1, isRedBomb: Boolean) {

  def setBombs(bombAmount: Int): Field = Field(xLocation, yLocation, isBomb, isFlagged, isOpened, bombAmount, isRedBomb)

  def flipFlag(): Field = Field(xLocation, yLocation, isBomb, !isFlagged, false, surroundingBombs, isRedBomb)

  def open(): Field = Field(xLocation, yLocation, isBomb, isFlagged, true, surroundingBombs, isRedBomb)

}
