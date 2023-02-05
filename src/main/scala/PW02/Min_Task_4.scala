package PW02

import scala.collection.mutable.ListBuffer

object Min_Task_4 {

  def uniqueSum(list : List[Int]) : Int = {
    var uniqueList = new ListBuffer[Int]()
    var sum : Int = 0
    for (i <- list if !uniqueList.contains(i)){
      uniqueList += i
      sum += i
    }
    return sum
  }

  def main(args: Array[String]): Unit = {
    var list = List[Int](1, 3, 4, 2, 6, 2)
    println(uniqueSum(list))
  }
}
