import scala.concurrent.Promise

/**
 * На вход Seq[Future[String]]
 * Получить Future[(Seq[String], Seq[Throwable]) - результат агрегации выполненых Future и исключений
 */
object SuccessAndFalse extends App {
  import scala.concurrent.duration.Duration
  import scala.concurrent.{Await, ExecutionContext, Future}
  import scala.util.control.NonFatal
  import scala.util.{Failure, Success}
  implicit val ec = ExecutionContext.global
  val talk = Seq(
    Future {
      Thread.sleep(1000)
      "red"
    },
    Future.failed(new RuntimeException("exception1")),
    Future.successful("blue"),
    Future.failed(new RuntimeException("exception2")),
    Future.successful("green"),
    Future.failed(new RuntimeException("exception3"))
  )
  // получить Future[(Seq[Throwable], Seq[String])]

  val result = Await.result(resultFuture, Duration.Inf)
  println(result)
} 