package PW03

import akka.actor.typed.javadsl.Behaviors
import akka.actor.{Actor, Props}
import akka.actor.ActorSystem

class PrintingActor extends Actor {
  def receive: Receive = {
    case message : Any => println(message)
  }
}

object Main extends App {
  val system = ActorSystem("ActorSystem")
  val actor = system.actorOf(Props.apply[PrintingActor](), "PrintingActor1")

  actor ! "hello"
  actor ! "bye"
  actor ! 12
}
