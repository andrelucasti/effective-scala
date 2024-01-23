package akkaessential.actor

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object ActorState {

  object WordCounter {
    def apply(): Behavior[String] = counter()

    def counter(): Behavior[String] = Behaviors.setup { context =>
      var totalCount = 0
      Behaviors.receiveMessage{ message =>

        val wordSize = message.split(" ").length
        totalCount += wordSize
        context.log.info(s"Messages word received: $wordSize - Total count: $totalCount")

        Behaviors.same
      }
    }
  }

  object WordCounter_V2 {
    def apply(): Behavior[String] = counter(0)

    def counter(totalCount: Int): Behavior[String] = Behaviors.receive{ (context, message) =>
      val wordSize = message.split(" ").length
      context.log.info(s"Messages word received: $wordSize - Total count: ${totalCount + wordSize}")
      counter(totalCount = totalCount + wordSize)
    }
  }

  def wordActor(): Unit = {
    val actorSystem = ActorSystem(WordCounter_V2(), "WordCounter")
    actorSystem ! "Andre Lucas"
    actorSystem ! "Andre Silva"
    actorSystem ! "andrelucas.io"
  }
  def main(args: Array[String]): Unit = {
    wordActor()
  }
}
