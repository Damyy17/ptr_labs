package PW02

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Main_Task_5 {

  def groupAnagrams(list : List[String]) : Map[String, ListBuffer[String]] = {
    var anagramsMap = scala.collection.mutable.Map[String, ListBuffer[String]]()
    for(el <- list){
      if (!anagramsMap.contains(el.sorted)){
        var listAnagrams = new ListBuffer[String]
        listAnagrams += el
        anagramsMap += (el.sorted -> listAnagrams)
      } else {
        anagramsMap(el.sorted) += el
      }
    }
    return anagramsMap.toMap
  }

  def main(args: Array[String]): Unit = {
    val list = List[String]("eat", "tea", "tan", "ate", "nat", "bat")
    println(groupAnagrams(list))
  }
}
