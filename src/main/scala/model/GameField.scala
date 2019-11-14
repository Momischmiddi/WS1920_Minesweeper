package model

import model.Difficulty.Difficulty

class GameField(val fields: List[List[Field]], val difficulty: Difficulty = Difficulty.Easy)
