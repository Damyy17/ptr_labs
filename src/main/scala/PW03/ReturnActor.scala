package PW03

import PW01.HelloWorld
import akka.actor.typed.javadsl.Behaviors
import akka.actor.{Actor, Props}
import akka.actor.ActorSystem

class ReturnActor extends Actor {
  def receive: Receive = {
    case message : String =>
      println("Received: " + message.toLowerCase)
    case integers : Int =>
      println("Received: " + (integers + 1))
    case idk : Any =>
      println("Received: I don ’ t know how to HANDLE this !")
  }
}

object Main1 extends App {
  val system = ActorSystem("ActorSystem")
  val actor = system.actorOf(Props.apply[ReturnActor](), "ReturnActor1")

  actor ! "helloWordLLL"
  actor ! 12
  actor ! true
}

