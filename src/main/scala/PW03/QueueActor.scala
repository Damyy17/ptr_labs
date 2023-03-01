package PW03
import scala.collection.mutable
import scala.collection.mutable.Queue
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.DurationInt
import scala.collection.mutable.Queue
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object QueueActor {
  sealed trait QueueMessage
  case class Enqueue[T](item: T) extends QueueMessage
  case object Dequeue extends QueueMessage

  def createQueueActor[T](): QueueActor[T] = new QueueActor[T]

  class QueueActor[T] {
    private val queue = mutable.Queue[Any]()

    def receive(msg: QueueMessage): Future[Unit] = msg match {
      case Enqueue(item) =>
        queue.enqueue(item)
        Future.unit
      case Dequeue =>
        if (queue.isEmpty)
          Future.failed(new NoSuchElementException("queue is empty"))
        else
          Future.successful(queue.dequeue())
    }

    def printQueue() : Unit = println(queue)
  }
}
object MainQ {
  def main(args: Array[String]): Unit = {
    val queueActor = QueueActor.createQueueActor[Int]()
    //enqueue an item
    val future1 = queueActor.receive(QueueActor.Enqueue(42))
    val future2 = queueActor.receive(QueueActor.Enqueue(23))
    val future3 = queueActor.receive(QueueActor.Enqueue(43))
    val result1 = Await.result(future1, 1.second)
    println("Queue after enqueue ")
    println(queueActor.printQueue())

    //dequeue item
    val future4 = queueActor.receive(QueueActor.Dequeue)
    println("Queue after dequeue ")
    val result3 = Await.result(future3, 1.second)
    println(result3)
    val future5 = queueActor.receive(QueueActor.Dequeue)
    queueActor.printQueue()
    val result4 = Await.result(future4, 1.second)
    println(result4)

  }
}

