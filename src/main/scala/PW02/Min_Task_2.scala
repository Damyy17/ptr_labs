package PW02

object Min_Task_2 {

  def pi = 3.14159

  def areaCalc(h : Int, r : Int) : Double = {
    val area = 2 * pi * r * h + 2 * pi * r * r
    return area
  }

  def main(args: Array[String]): Unit = {
    print(areaCalc(3, 4))
  }

}
