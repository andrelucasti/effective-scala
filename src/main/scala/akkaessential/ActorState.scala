package akkaessential

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

import scala.collection.mutable.ListBuffer

object ActorState {
  def numberWord: Behavior[String] = Behaviors.setup{ context =>
    val wordsBuffer = ListBuffer[String]()
    def splitWord(pMessage: String) = pMessage.split(" ")

    Behaviors.receiveMessage{ message =>
      val words = splitWord(message)
      context.log.info(s"Receiving message: $message")
      context.log.info(s"Number of words received: ${words.toList.size}")

      wordsBuffer.appendAll(words)

      context.log.info(s"Total of words received: ${wordsBuffer.size} ")
      Behaviors.same
    }
  }

  def actorWords(): Unit = {
    val actorSystem = ActorSystem(numberWord, "actorWord")
    actorSystem ! "andre lucas"
    actorSystem ! "santos silva"
    actorSystem ! "I love code"
  }

  def main(args: Array[String]): Unit = {
    actorWords()
  }
}
