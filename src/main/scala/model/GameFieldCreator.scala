package model

import model.Difficulty.Difficulty

import scala.util.Random

class GameFieldCreator {

  def createGameField(difficulty: Difficulty, bombLocations: List[(Int, Int)]): GameField = {
    new GameField(
      (for {i <- 0 until difficulty._1; j <- 0 until difficulty._2 } yield new Field(j, i, bombLocations contains (j, i)))
        .grouped(difficulty._1) // Doesnt matter if X or Y, since its quadratic
        .toArray
        .map(_.toArray),
      difficulty)
  }

  def createRandomBombLocations(difficulty: Difficulty): List[(Int, Int)] = {
    for {
      random <- Random.shuffle(List.range(0, difficulty._1 * difficulty._2)).take(difficulty._3)
    } yield (random % difficulty._1, random / difficulty._1)
  }
}
