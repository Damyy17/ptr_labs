package PW02

object Bonus_Task_1 {

  def takeCommonPrefix(str1: String, str2: String) : String = (str1.inits.toSet intersect str2.inits.toSet).maxBy(_.length)

  def commonPrefix(list : List[String]) : String = {
    var commonPrefix = list.head
    for(el <- list) {
      commonPrefix = takeCommonPrefix(el, commonPrefix)
    }
    return commonPrefix
  }

  def main(args: Array[String]): Unit = {
    val list = List("flower", "flow")
    println(commonPrefix(list))
  }
}
