package PW02

import scala.collection.mutable.ListBuffer

object Min_Task_5 {

  def randomSeq(list : List[Int], n : Int) : List[Int] = {
    val rand = scala.util.Random
    var randList = new ListBuffer[Int]()
    while (randList.length < n){
      randList.addOne(list(rand.nextInt(list.length)))
    }
    return randList.toList
  }

  def main(args: Array[String]): Unit = {
    var list = List[Int](5, 1, 6, 3, 7, 3, 2)
    println(randomSeq(list, 4))
  }

}
