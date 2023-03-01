package PW04

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case class splitString(s : String)
case class lowCasedSwappedString(s : Array[String])
case class resultString(s : Array[String])
case class Error(reason: String)

class SplitWorker extends Actor {
  def receive = {
    case splitString(input) =>
      val words = input.split("\\s+")
      sender() ! lowCasedSwappedString(words)
    case Error(reason) => throw new Exception(s"SplitWorker stopped because of $reason")
  }
}

class LowCaseSwapWorker extends Actor {
  def receive = {
    case lowCasedSwappedString(words) =>
      val lowCaseSwappedWords = words.map(word => word.toLowerCase().replace("m", "temp").replace("n", "m").replace("temp", "n"))
      sender() ! resultString(lowCaseSwappedWords)
    case Error(reason) => throw new Exception(s"LowerCaseSwapWorker stopped because of $reason")
  }
}

class JoinResultWorker extends Actor {
  def receive = {
    case resultString(lowCaseSwappedWords) =>
      val result = lowCaseSwappedWords.mkString(" ")
      println(s"Cleaned string: $result")
    case Error(reason) => throw new Exception(s"JoinResultWorker stopped because of $reason")
  }
}

class SupervisorStringProcessor extends Actor {
  private var splitWorker: ActorRef = context.actorOf(Props.apply[SplitWorker](), "split-worker")
  private var lowCaseSwapWorker: ActorRef = context.actorOf(Props.apply[LowCaseSwapWorker](), "low-case-swap-worker")
  private var joinResultWorker: ActorRef = context.actorOf(Props.apply[JoinResultWorker](), "join-result-worker")

  override def preStart(): Unit = {
    splitWorker = context.actorOf(Props.apply[SplitWorker](), "splitWorker")
    lowCaseSwapWorker = context.actorOf(Props.apply[LowCaseSwapWorker](), "lowCaseSwapWorker")
    joinResultWorker = context.actorOf(Props.apply[JoinResultWorker](), "joinResultWorker")
  }

  def receive = {
    case splitString(messyString) =>
      splitWorker ! splitString(messyString)
    case lowCasedSwappedString(words) =>
      lowCaseSwapWorker ! lowCasedSwappedString(words)
    case resultString(lowCasedSwappedString) =>
      joinResultWorker ! resultString(lowCasedSwappedString)
    case Error(reason) =>
      throw new Exception(s"SupervisorStringProcessor stopped because of $reason")
  }
}

object MainCleanString{
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("StringProcessingSystem")
    val supervisor = system.actorOf(Props.apply[SupervisorStringProcessor](), name = "supervisor")

    supervisor ! splitString("Test m is n and n is m, mommy, mnmnnm")

    Thread.sleep(500)

//    supervisor ! Error("Error occurred")

    system.terminate()
  }
}
