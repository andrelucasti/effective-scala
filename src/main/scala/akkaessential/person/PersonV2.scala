package akkaessential.person

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object PersonV2 {
  def happy: Behavior[String] = Behaviors.receive { (context, message) =>
    message match {
      case "Akka is bad" =>
        context.log.info("You're wrong, akka is awesome !")
        sad
      case _ =>
        context.log.info(s"I've received $message. That's great!")
        Behaviors.same
    }

  }

  def sad: Behavior[String] = Behaviors.receive { (context, message) =>
    message match {
      case "Akka is awesome" =>
        context.log.info(s"I've received $message. That's great!")
        happy
      case _ =>
        context.log.info(s"$message That's suck")
        Behaviors.same
    }
  }

  def apply(): Behavior[String] = happy
}

object PersonV2Test {
  def simpleActorSystem(): Unit = {
    val actorSystem = ActorSystem(PersonV2(), "Person")
    actorSystem ! "I love hamburger"
    Thread.sleep(500)
    actorSystem ! "Akka is bad"
    Thread.sleep(500)
    actorSystem ! "Oh, I was wrong"
    Thread.sleep(500)
    actorSystem ! "Akka is awesome"
    Thread.sleep(500)
    actorSystem ! "I loving to learn scala + akka"
    Thread.sleep(2000)
    actorSystem.terminate()
  }

  def main(args: Array[String]): Unit = {
    simpleActorSystem()
  }
}

