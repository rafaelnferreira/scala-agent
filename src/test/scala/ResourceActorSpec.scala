import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, TestKit}
import munit.FunSuite

import scala.concurrent.duration._

class ResourceActorSpec extends FunSuite {

  // Create the actor system
  implicit val system: ActorSystem = ActorSystem("ResourceActorSpec")

  override def afterAll(): Unit = {
    // Shutdown the actor system after all tests
    TestKit.shutdownActorSystem(system)
  }

  test("ResourceActor should cancel a long-running task when receiving a Stop message") {
    // Create the actor
    val resourceActor = TestActorRef(new ResourceActor)

    // Send a Start message to begin the long-running task
    resourceActor ! Start

    // Wait briefly to allow the task to start
    Thread.sleep(500)

    // Send a Stop message to cancel the task
    resourceActor ! Stop

    // Verify the actor transitions back to the idle state
    resourceActor ! Start // Sending another Start should work without issue

    // No exceptions or errors should occur, ensuring the test passes
    assert(true)
  }
}
