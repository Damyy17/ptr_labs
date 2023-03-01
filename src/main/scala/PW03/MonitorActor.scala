package PW03

import akka.actor.TypedActor.context
import akka.actor.{Actor, ActorContext, ActorRef, ActorSystem, Props, Terminated}

class ActorM extends Actor {
  def receive: Receive = {
    case "start" =>
      println("actor has started")
    case "stop" =>
      context.stop(self)
  }
}

class MonitorActor(actor : ActorRef) extends Actor{
  override def preStart(): Unit = {
    context.watch(actor)
  }

  def receive = {
    case Terminated(actorRef) if actorRef == actor =>
      println("Monitored actor has stopped")
      context.stop(self)
  }
}

object Main3 extends App {
  val system = ActorSystem("System")

  val monitoredActor = system.actorOf(Props.apply[ActorM](), "MonitoredActor")
  val monitoringActor = system.actorOf(Props(new MonitorActor(monitoredActor)), "Monitor")

  monitoredActor ! "start"
  monitoredActor ! "stop"
}
