package PW02

import scala.collection.mutable.ListBuffer

object Min_Task_9 {

  def rotateLeft(list : List[Int], n : Int) : List[Int] = {
    var rotatedListLeft = list.drop(n % list.length)
    var rotatedListRight = list.take(n)
    var rotatedList = rotatedListLeft ::: rotatedListRight

    return rotatedList
  }

  def main(args: Array[String]): Unit = {
    var list = List[Int](1, 2, 4 ,8, 4)
    var n = 3
    println(rotateLeft(list, 3))
  }

}
