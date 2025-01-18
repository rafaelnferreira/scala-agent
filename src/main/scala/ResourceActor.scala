import akka.actor.{Actor, ActorSystem, Props}
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import akka.actor.Cancellable
import scala.concurrent.duration._
import com.typesafe.scalalogging.Logger
import java.util.concurrent.FutureTask

case object Start
case object Stop
case object Completed

class ResourceActor extends Actor {
  private val logger = Logger(getClass)
  private val executor = Executors.newFixedThreadPool(4)

  // Idle state where no task is running
  def idle: Receive = {
    case Start =>
      logger.info("Start received. Starting the long-running task...")

      context.become(running(createJob()))

    case Stop =>
      logger.info("Stop received, but no active task to cancel.")
  }

  // Running state where a task is active
  def running(task: FutureTask[Unit]): Receive = {
    case Start =>
      logger.info(
        "Start received. Cancelling the current task and starting a new one..."
      )
      task.cancel(true)
      context.become(running(createJob()))

    case Stop =>
      logger.info("Stop received. Cancelling the active task...")
      task.cancel(true)
      context.become(idle)
    
    case Completed => 
      logger.info("Task completed successfully.")
      context.become(idle)
  }

  private def createJob(): FutureTask[Unit] = {
    val task = new FutureTask[Unit](() => {
      Thread.sleep(30000)
      self ! Completed
    })

    executor.submit(task)
    task
  }

  override def receive: Receive = idle

  // Shutdown the thread pool when the actor is stopped
  override def postStop(): Unit = {
    executor.shutdown()
    logger.info("Thread pool shut down.")
  }
}
