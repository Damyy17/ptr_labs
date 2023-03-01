package PW03

import akka.actor.{Actor, ActorSystem, Props}

class AvgActor extends Actor {
  var sum: Double = 0.0
  var count = 0

  def receive : Receive = {
    case n: Int =>
      sum += n
      count += 1
      val avg = sum / count
      println("Average sum: " + avg)
  }
}

object Main4 extends App {
  val system = ActorSystem("SumSystem")
  val avgActor = system.actorOf(Props.apply[AvgActor]())

  avgActor ! 0
  avgActor ! 10
  avgActor ! 10
  avgActor ! 10
}


