package PW02

import scala.collection.mutable.ListBuffer
import scala.math.sqrt

object Bonus_Task_3 {

  def isPrime(n: Int) : Boolean = {
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
      return true
    else
      return false
  }

  def factorize(n : Int) : List[Int] = {
    var num = n
    var factors = new ListBuffer[Int]
    for (i<-2 to n if num % i == 0 && isPrime(i)){
      factors += i
    }
    factors.reverse
    return factors.toList
  }
  def main(args: Array[String]): Unit = {
    println(factorize(42))
  }
}
