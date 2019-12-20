package model

import model.Difficulty.Difficulty

import scala.util.Random

class Creator {

  def create(difficulty: Difficulty, bombLocations: List[(Int, Int)]): FieldMatrix = {
    FieldMatrix(
      (for {i <- 0 until difficulty._1; j <- 0 until difficulty._2} yield
        Field(j, i, bombLocations.contains(j, i), false, false, -1, false))
        .grouped(difficulty._1)
        .toVector
        .map(_.toVector),
      difficulty)
  }

  def createRandomBombLocations(difficulty: Difficulty): List[(Int, Int)] = {
    for {
      random <- Random.shuffle(List.range(0, difficulty._1 * difficulty._2)).take(difficulty._3)
    } yield (random % difficulty._1, random / difficulty._1)
    /*
    List(
      // DO NOT MODIFY! Tests will fail if you do.
      (0, 4), (0, 5), (0, 7), (1, 7), (2, 3), (4, 4), (5, 5), (6, 3), (6, 5), (7, 3)
    )
     */
  }

}
