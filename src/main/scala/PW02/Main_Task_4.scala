package PW02

import scala.collection.mutable.ListBuffer

object Main_Task_4 {

  def mapNumberLetter : Map[Int, List[String]] = Map(
    2 -> List("a", "b", "c"),
    3 -> List("d", "e", "f"),
    4 -> List("g", "h", "i"),
    5 -> List("j", "k", "l"),
    6 -> List("m", "n", "o"),
    7 -> List("p", "q", "r", "s"),
    8 -> List("t", "u", "v"),
    9 -> List("w", "x", "y", "z")
  )

  def letterCombinations(number : Int) : List[String] = {
    val n1 : Int = number / 10
    val n2 = number - n1 * 10
    var finalList = ListBuffer[String]()
    for (letter <- mapNumberLetter(n1); letter2 <- mapNumberLetter(n2)){
      finalList += letter.concat(letter2)
    }
    return finalList.toList
  }

  def main(args: Array[String]): Unit = {
    println(letterCombinations(23))
  }

}
