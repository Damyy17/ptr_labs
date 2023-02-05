package PW02

import scala.io.StdIn.readInt
import scala.math.sqrt

object Min_Task_1 {
  println("Write number:")
  var number = readInt()

  def isPrime(n: Int) : String = {
    val primes: Array[Boolean] = new Array[Boolean](n+1)
    for (x <- 0 to n)
      primes(x) = true

    primes(1) = false
    var i = 2
    while (i <= n) {
      var j = 2
      while (j < sqrt(i)){
        if (i % j == 0){
          primes(i) = false
        }
        j+=1
      }
      i+=1
    }
    if (primes(n))
      return "is Prime"
    else
      return "is Not Prime"
  }

  println("Number " + number + " " + isPrime(number))
}
