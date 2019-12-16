package model

class Field(val xLocation: Int, val yLocation: Int, val isBomb: Boolean = false,
            val isFlagged: Boolean = false, val isOpened: Boolean = false, val surroundingBombs: Int = -1,
            val isRedBomb: Boolean)
