import akka.actor.{ActorSystem, Props}
import scala.io.StdIn
import scala.util.control.Breaks._

object Main extends App {
  // Initialize the actor system
  implicit val system: ActorSystem = ActorSystem("ResourceActorSystem")

  // Create the ResourceActor
  val resourceActor = system.actorOf(Props[ResourceActor](), "resourceActor")

  println("CLI Application for ResourceActor")
  println("================================")
  println("Options:")
  println("1 - Send Start message")
  println("2 - Send Stop message")
  println("3 - Exit application")

  // CLI loop
  breakable {
    while (true) {
      print("\nEnter your choice: ")
      val choice = StdIn.readLine()

      choice match {
        case "1" =>
          println("Sending Start message...\n")
          resourceActor ! Start
        case "2" =>
          println("Sending Stop message...\n")
          resourceActor ! Stop
        case "3" =>
          println("Exiting application...")
          break();
        case _ =>
          println("Invalid option. Please choose 1, 2, or 3.")
      }
      Thread.sleep(250)
    }
  }

  // Shutdown the actor system
  system.terminate()
  println("Actor system terminated. Goodbye!")
}
