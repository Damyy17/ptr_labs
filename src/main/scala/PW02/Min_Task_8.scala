package PW02

import scala.collection.mutable.ListBuffer

object Min_Task_8 {

  def minNumberSelect(a : Int, b : Int, c: Int) : Int = {
    var list = List[Int](a, b, c).sorted
    var smallestNumber = ""
    if (list.contains(0) && list(0) == 0){
      smallestNumber = list(1).toString + list(0).toString + list(2).toString
    } else if (list.contains(0) && list(0) == 0 && list(1) == 0){
      smallestNumber = list(2).toString + list(0).toString + list(1).toString
    } else {
      smallestNumber = list(0).toString + list(1).toString + list(2).toString
    }
    return smallestNumber.toInt
  }

  def main(args: Array[String]): Unit = {
    println(minNumberSelect(0, 4 ,0))
  }
}
