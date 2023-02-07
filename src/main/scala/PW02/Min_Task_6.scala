package PW02

import scala.collection.mutable.ListBuffer

object Min_Task_6 {

  def firstFibonacciElements(n : Int) : List[Int] = {
    var fibList = new Array[Int](n)
    fibList(0) = 1
    fibList(1) = 1
    for (i <- 2 until n){
      fibList(i) = fibList(i-1) + fibList(i-2)
    }
    return fibList.toList
  }

  def main(args: Array[String]): Unit = {
    println(firstFibonacciElements(7))
  }

}
