package akkaessential.person

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object Happy {
  def apply(): Behavior[String] = {
    Behaviors.receive { (context, message) =>
      context.log.info(s"I've received $message. That's great!")
      Behaviors.same
    }
  }
}

object Sad {
  def apply(): Behavior[String] = {
    Behaviors.receive { (context, message) =>
      context.log.info(s"$message That's suck")
      Behaviors.same
    }
  }
}

object Person {
  def happy: Behavior[String] = Behaviors.receive{(context, message) =>
    context.log.info(s"I've received $message. That's great!")
    Behaviors.same
  }

  def sad: Behavior[String] = Behaviors.receive { (context, message) =>
    context.log.info(s"$message That's suck")
    Behaviors.same
  }

  def apply(message: String): Behavior[String] = {
    message match {
      case "Akka is awesome" => happy
      case "Akka is bad" => sad
    }
  }
}

def simpleActorSystem(message: String): Unit = {
  val actorSystem = ActorSystem(Person(message), "Person")
  actorSystem ! message
  actorSystem.terminate()
}

object PersonTest {
  def main(args: Array[String]): Unit = {
    simpleActorSystem("Akka is awesome")
    simpleActorSystem("Akka is bad")
  }
}
