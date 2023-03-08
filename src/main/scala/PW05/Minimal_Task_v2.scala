package PW05

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future, blocking}
import scala.util.{Failure, Success}
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract.*
import net.ruippeixotog.scalascraper.dsl.DSL.*

object task2 {
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
        response.headers.foreach(h => println(s"${h.name}: ${h.value}"))
        val bodyFuture = response.entity.toStrict(5.seconds).map(_.data.utf8String)
        Await.ready(bodyFuture, 5.seconds).onComplete {
          case Success(body) =>
            val doc = JsoupBrowser().parseString(body)
            val quotes = doc >> elements(".quote") map { quote =>
              val text = quote >> element(".text") >> allText
              val author = quote >> element(".author") >> allText
              val tags = quote >> elements(".tags .tag") >> texts
              Map(
                "text" -> text,
                "author" -> author,
                "tags" -> tags
              )
            }
            quotes.foreach(println)
          case Failure(ex) => println(s"Failed to get response body: ${ex.getMessage}")
        }
        system.terminate()
      case Failure(ex) =>
        println(s"Request failed: ${ex.getMessage}")
        system.terminate()
    }
  }
}
