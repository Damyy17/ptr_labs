package PW03

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.util.{Failure, Success}

class Semaphore (private var count: Int)(implicit  ec: ExecutionContext){
  private val promiseQueue = collection.mutable.Queue[Promise[Unit]]()

  def acquire(): Future[Unit] = {
    val promise = Promise[Unit]()
      if(count > 0 ){
        count -= 1
        promise.success(())
      } else {
        promiseQueue.enqueue(promise)
      }
    promise.future
  }

  def release(): Unit = {
      if (promiseQueue.nonEmpty) {
        val nextPromise = promiseQueue.dequeue()
        nextPromise.success(())
      } else {
        count += 1
      }
  }
}
object MainSemaphore {
  def main(args: Array[String]): Unit = {

//    val tasks = (1 to 3).map { i =>
//      Future {
//        semaphore.acquire()
//        println(s"Task $i: acquired")
//        Thread.sleep(1000)
//        semaphore.release()
//        println(s"Task $i: released")
//      }
//    }


    val semaphore = Semaphore(1)

    val task1 = for {
      _ <- semaphore.acquire()
      _ <- Future {
        // do some work
      }
      _ = semaphore.release()
    } yield ()

    val task2 = for {
      _ <- semaphore.acquire()
      _ <- Future {
        // do some work
      }
      
    } yield ()

    val task3 = for {
      _ <- semaphore.acquire()
      _ <- Future {
        // do some work
      }
    } yield ()

    Await.result(Future.sequence(Seq(task1, task2, task3)), 10.second)

  }
}

