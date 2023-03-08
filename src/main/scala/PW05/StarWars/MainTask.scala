//package PW05.StarWars
//
//import scala.concurrent.Future
//import akka.actor.typed.ActorSystem
//import akka.actor.typed.scaladsl.Behaviors
//import akka.http.scaladsl.server.Route
//import akka.http.scaladsl.server.Directives.*
//
//import akka.http.scaladsl.unmarshalling._
//import akka.http.scaladsl.model._
//import akka.util.ByteString
//
//import scala.concurrent.ExecutionContext
//import akka.http.scaladsl.Http
//import akka.http.scaladsl.model.StatusCodes.Success
//
//import scala.util.Failure
//
//case class Movie(id: Int, title: String, release_year: Int, director: String)
//
//trait MovieRepository {
//  def create(movie: Movie) : Future[Option[Movie]]
//  def read(id: Int) : Future[Option[Movie]]
//  def update(id: Int, movie: Movie): Future[Option[Movie]]
//  def delete(id: Int) : Future[Boolean]
//}
//
//class UserRepositoryImpl extends MovieRepository {
//  private var movies = Map.empty[Int, Movie]
//
//  override def create(movie: Movie): Future[Option[Movie]] = {
//    movies.get(movie.id) match {
//      case Some(_) => Future.successful(None)
//      case None =>
//        movies += (movie.id -> movie)
//        Future.successful(Some(movie))
//    }
//  }
//
//  override def read(id: Int): Future[Option[Movie]] = {
//    Future.successful(movies.get(id))
//  }
//
//  override def update(id: Int, movie: Movie): Future[Option[Movie]] = {
//    movies.get(id) match {
//      case Some(_) =>
//        movies += (id -> movie)
//        Future.successful(Some(movie))
//      case None => Future.successful(None)
//    }
//  }
//
//  override def delete(id: Int): Future[Boolean] = {
//    movies.get(id) match {
//      case Some(_) =>
//        movies -= id
//        Future.successful(true)
//      case None => Future.successful(false)
//    }
//  }
//}
//
//implicit val movieUnmarshaller: FromRequestUnmarshaller[PW05.StarWars.Movie] =
//  Unmarshaller.byteStringUnmarshaller
//    .map(_.utf8String)
//    .map(PW05.StarWars.Movie.fromString)
//
//class MovieRoutes (movieRepository: MovieRepository)(implicit system: ActorSystem[_], ec: ExecutionContext){
//  val routes: Route =
//    pathPrefix("movies") {
//      concat(
//        pathEnd {
//          concat(
//            get {
//              complete(movieRepository.readAll())
//            },
//            post {
//              entity(as[Movie]) { movie =>
//                onSuccess(movieRepository.create(movie)) {
//                  case Some(createdMovie) => complete(createdMovie)
//                  case None => complete("Movie already exists")
//                }
//              }
//            }
//          )
//        },
//        path(IntNumber) { id =>
//          concat(
//            get {
//              onSuccess(movieRepository.read(id)) {
//                case Some(movie) => complete(movie)
//                case None => complete("Movie not found")
//              }
//            },
//            put {
//              entity(as[Movie]) { movie =>
//                onSuccess(movieRepository.update(id, movie)) {
//                  case Some(updatedMovie) => complete(updatedMovie)
//                  case None => complete("Movie not found")
//                }
//              }
//            },
//            delete {
//              onSuccess(movieRepository.delete(id)) {
//                case true => complete("Movie deleted")
//                case false => complete("Movie not found")
//              }
//            }
//          )
//        }
//      )
//    }
//}
//
//object Main extends App {
//  implicit val system: ActorSystem[_] = ActorSystem(Behaviors.empty, "my-system")
//  implicit val ec: ExecutionContext = system.executionContext
//
//  val movieRepository = new MovieRepositoryImpl()
//  val movieRoutes = new MovieRoutes(movieRepository)
//
//  val httpServerFuture = Http().newServerAt("localhost", 8080).bind(userRoutes.routes)
//
//  httpServerFuture.onComplete {
//    case Success(binding) =>
//      println(s"Server is listening on ${binding.localAddress}")
//    case Failure(ex) =>
//      println(s"Server could not start: $ex")
//      system.terminate()
//  }
//}
//
