package PW02

import scala.collection.mutable.ListBuffer

object Main_Task_2 {

  def lineWords(list : List[String]) : List[String] = {
    val firstLine = List('q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p')
    val secondLine = List('a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l')
    val thirdLine = List('z', 'x', 'c', 'v', 'b', 'n', 'm')
    var filteredList = new  ListBuffer[String]
    for(el <- list){
      if(el.toLowerCase().forall(firstLine.contains) 
        || el.toLowerCase().forall(secondLine.contains) 
        || el.toLowerCase().forall(thirdLine.contains)) {
        filteredList += el
      }
    }
    return filteredList.toList
  }

  def main(args: Array[String]): Unit = {
    var list = List("Hello", "Alaska", "Dad", "Peace")
    println(lineWords(list))
  }
}
