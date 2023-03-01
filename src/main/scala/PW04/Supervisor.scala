package PW04

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case class Echo(message: String)
case class Kill(name: String)
case object Print

class Worker extends Actor{
  def receive = {
    case Echo(message) => println(message)
    case Kill(name) => throw new Exception(s"$name killed")
  }

  override def preRestart(reason: Throwable, message: Option[Any]) = {
    println(s"${self.path.name} restarting...")
    super.preRestart(reason, message)
  }
}

class Supervisor(numberOfWorkers : Int) extends Actor {
  //map maintains workers name and their reference
  private var workers: Map[String, ActorRef] = Map()

  override def preStart() = {
    for (i <- 1 to numberOfWorkers) {
      workers += (s"worker-$i" -> context.actorOf(Props.apply[Worker](), s"worker_$i"))
    }
  }

  def receive = {
    case Echo(message) =>
      workers.values.foreach(worker => worker ! Echo(message))
    case Kill(name) =>
      if (workers.contains(name)){
        val victim = workers(name)
        victim ! Kill
        workers -= name
        workers += (name -> context.actorOf(Props.apply[Worker](), name))
      } else {
        println(s"Worker with name $name not found!")
      }
    case Print => println(workers)
  }
}


object MainSupervisor {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("SupervisorSystem")
    val supervisor = system.actorOf(Props(new Supervisor(numberOfWorkers = 3)), name = "supervisor")

    supervisor ! Echo("Hello, actors!")

//    supervisor ! Print

    Thread.sleep(1000)

    supervisor ! Kill("worker-1")

    Thread.sleep(1000)

//    supervisor ! Print

    supervisor ! Echo("Are all workers present?")

    system.terminate()
  }

}
