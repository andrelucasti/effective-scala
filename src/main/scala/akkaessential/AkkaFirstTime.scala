package akkaessential

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

object AkkaFirstTime {
  val simpleActorBehavior: Behaviors.Receive[String] = Behaviors.receiveMessage{(message: String) =>
      println(s"Simple Actor Receiving a message: $message")

      Behaviors.same
    }


  def simpleActor(): Unit = {
    val simpleSystemActor = ActorSystem(simpleActorBehavior, "ActorSystem")
    val messages = List("Andre", "Lucas", "Scala", "Akka")

    messages.foreach(f => simpleSystemActor ! f)

  }

  def main(args: Array[String]): Unit = {
    simpleActor()
  }
}
