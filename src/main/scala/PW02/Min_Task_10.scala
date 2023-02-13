package PW02

import scala.collection.mutable.ListBuffer

object Min_Task_10 {

  def listRightTriangles() : List[(Int, Int, Int)] = {
    var finalList = List[(Int, Int, Int)]()
    for (a <- 1 to 20; b <- 1 to 20) {
      val c = math.round(math.sqrt(a*a + b*b))
      if (c % 1 == 0 && a*a + b*b == c*c) {
        finalList = (a, b, c.toInt) :: finalList
      }
    }
    return finalList
  }

  def main(args: Array[String]): Unit = {
    println(listRightTriangles())
  }
}
