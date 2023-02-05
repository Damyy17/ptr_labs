package PW02
import scala.io.StdIn.readInt

val pi = 3.14159
def area(h:Int, r:Int): Double = 2 * pi * r * h + 2 * pi * r * r

@main def main() = {
  val height = readInt()
  val radius = readInt()
  println(area(height, radius))
}