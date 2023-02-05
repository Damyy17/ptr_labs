package PW02

import scala.collection.mutable.ListBuffer
import scala.io.StdIn.readInt

object Min_Task_3 {

  def reverseList(list : List[Int]) : List[Int] = list.reverse

  def main(args: Array[String]): Unit = {
    val list = new ListBuffer[Int]()
    val n = readInt()

    for(i <- 0 until n){
      val el = readInt()
      list.addOne(el)
    }

    println(list)
    println(reverseList(list.toList))
  }
}
