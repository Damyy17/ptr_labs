package PW05

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.{Failure, Success}

object task1 {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("AkkaHttpExample")
    implicit val ec: ExecutionContext = system.dispatcher

    val url = "https://quotes.toscrape.com/"
    val request = HttpRequest(uri = url)
    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)

    // processing the response
    responseFuture.onComplete {
      case Success(response) =>
        println(s"Response status code: ${response.status}")
        println("Response headers:")
        response.headers.foreach(header => println(s"${header.name}: ${header.value}"))
        val bodyFuture = response.entity.toStrict(5.seconds).map(_.data.utf8String)
        Await.ready(bodyFuture, 5.seconds).onComplete {
          case Success(body) => println(s"Response body: $body")
          case Failure(ex) => println(s"Failed to get response body: ${ex.getMessage}")
        }
        system.terminate()
      case Failure(ex) =>
        println(s"Request failed: ${ex.getMessage}")
        system.terminate()
    }
  }
}








