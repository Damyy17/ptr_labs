package PW02

import scala.collection.mutable.ListBuffer

object Main_Task_1 {

  def removeConsecutiveDuplicates(list : List[Int]) : List[Int] = {
    var listWithoutConsecutiveDuplicataes = new ListBuffer[Int]
    var tmp = -999
    for( el <- list) {
      if (tmp != el){
        tmp = el
        listWithoutConsecutiveDuplicataes += tmp
      }
    }
    return listWithoutConsecutiveDuplicataes.toList
  }

  def main(args: Array[String]): Unit = {
    var list = List[Int](1, 2, 2, 2, 4, 8, 4)
    println(removeConsecutiveDuplicates(list))
  }
}
