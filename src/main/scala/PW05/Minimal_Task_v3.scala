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
import spray.json.*
import DefaultJsonProtocol.*
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString

case class Quote(text: String, author: String, tags: List[String])

object Quote {
  implicit val format: RootJsonFormat[Quote] = jsonFormat3(Quote.apply)
}

object task3 {
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
              Quote(text, author, tags.toList)
            }
            quotes.foreach(println)

            val quotesJson = quotes.toJson.prettyPrint
            val sink = FileIO.toPath(new java.io.File("/Users/damy17/Documents/IdeaProjects/ptr_labs/src/main/scala/PW05/quotes.json").toPath)
            val fileWriteFuture = Source.single(ByteString(quotesJson)).runWith(sink)
            Await.ready(fileWriteFuture, 5.seconds).onComplete {
              case Success(_) =>
                println("Quotes written to file successfully.")
                system.terminate()
              case Failure(ex) =>
                println(s"Failed to write quotes to file: ${ex.getMessage}")
                system.terminate()
            }
          case Failure(ex) =>
            println(s"Failed to get response body: ${ex.getMessage}")
            system.terminate()
        }
      case Failure(ex) =>
        println(s"Request failed: ${ex.getMessage}")
        system.terminate()
    }
  }
}
