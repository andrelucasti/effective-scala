package akkaessential.child

import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Terminated}
import akka.actor.typed.scaladsl.Behaviors
import akkaessential.child.StopActors.Child.Message
import akkaessential.child.StopActors.Parent.{CreateChild, StopChild, TellChild, WatchChild}

object StopActors {

  object Parent {
    trait Command
    case class CreateChild(name: String) extends Command
    case class TellChild(name: String, message: String) extends Command
    case class StopChild(name: String) extends Command
    case class WatchChild(name: String) extends Command

    def apply(): Behavior[Command] = createChild(Map())

    def createChild(children: Map[String, ActorRef[Message]]):Behavior[Command] = Behaviors.receive[Command]{ (context, message) =>
      message match
        case CreateChild(name) =>
          context.log.info(s"[parent] Creating a child with name $name")
          val childRef: ActorRef[Message] = context.spawn(Child(), name)
          createChild(children + (name -> childRef))

        case TellChild(name, message) =>
          children.get(name).fold(context.log.info(s"[parent] child $name could not be found"))(child => child ! Message(name, message))
          Behaviors.same

        case StopChild(name) =>
          val childOption = children.get(name)
          context.log.info(s"[parent] attempting to stop child with name $name")
          childOption.fold(context.log.info(s"[parent] child $name could not be found"))(child => context.stop(child))
          createChild(children - name)

        case WatchChild(name) =>
          val childOption = children.get(name)
          context.log.info(s"[parent] watching the child with name $name")
          childOption.fold(context.log.info(s"[parent] child $name could not be found"))(child => context.watch(child))
          Behaviors.same

    }.receiveSignal{
      case(context, Terminated(ref)) =>
        context.log.info(s"Child ${ref.path} was stopped")
        val childName = ref.path.name

        createChild(children - childName)
    }

  }

  object Child {
    case class Message(id: String, body: String)
    def apply(): Behavior[Message] = receiveMessage()

    def receiveMessage(): Behavior[Message] = Behaviors.receive{ (context, message) =>
      context.log.info(s"[${context.self.path.name}] Hello, I'm ${message.id} and I received: ${message.body}")
      Behaviors.same
    }
  }

  def startGuardian(): Unit = {
    val userGuardian: Behavior[Nothing] = Behaviors.setup { context =>
      val parent = context.spawn(Parent(), "Parent")

      parent ! CreateChild("andre")
      parent ! CreateChild("lucas")

      parent ! TellChild("andre", "hello")

      parent ! WatchChild("lucas")
      parent ! StopChild("lucas")

      parent ! TellChild("lucas", "Can you hear me ?")

      Behaviors.empty
    }

    val system = ActorSystem(userGuardian, "system")
    Thread.sleep(1000)
    system.terminate()
  }

  def main(args: Array[String]): Unit = {
    startGuardian()
  }
}
