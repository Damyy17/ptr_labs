package PW02

import java.util.Dictionary
import scala.collection.mutable.ListBuffer

object Min_Task_7 {

  def translator(dictionary: Map[String, String], orignal_text : String) : List[String] = {
    var translated_text = new ListBuffer[String]
    var splited_text = orignal_text.split(" ")
    for (word <- splited_text){
      if (dictionary.keySet.contains(word)){
        translated_text += dictionary.get(word).get.toString
      } else {
        translated_text += word
      }
    }
    return translated_text.toList
  }

  def main(args: Array[String]): Unit = {
    var original_text = "mama is with papa"
    var dictionary = Map(
      "mama" -> "mother",
      "papa" -> "father"
    )
    for (el <- translator(dictionary, original_text)){
      print(el + " ")
    }
  }
}
