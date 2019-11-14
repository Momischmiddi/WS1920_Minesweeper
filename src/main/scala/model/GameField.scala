package model

import model.Difficulty.Difficulty

class GameField(val fields: Array[Array[Field]],  val difficulty: Difficulty = Difficulty.Easy)
