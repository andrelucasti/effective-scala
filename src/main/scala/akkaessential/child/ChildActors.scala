package akkaessential.child

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akkaessential.child.ChildActors.Parent_V2.{Command, CreateChild, TellChild}

// actors can create other actors (child)
object ChildActors {

  object Parent_V2 {
    trait Command
    case class CreateChild(name: String) extends Command
    case class TellChild(name: String, message: String) extends Command

    def apply(): Behavior[Command] = createChild(Map())

    private def createChild(children: Map[String, ActorRef[String]]): Behavior[Command] = Behaviors.receive { (context, message) =>
      message match
        case CreateChild(name) =>
          context.log.info(s"[parent] Creating a child with name $name")
          val childRef = context.spawn(Child(), name)
          createChild(children + (name -> childRef))
        case TellChild(name, message) =>
          val childOption = children.get(name)
          childOption.fold(context.log.info(s"[parent] child $name could not be found"))(child => child ! message)
          Behaviors.same
    }
  }

  object Child {
    def apply(): Behavior[String] = Behaviors.receive{ (context, message) =>
      context.log.info(s"[child] Hello, I'm ${context.self.path.name} and I received: $message")
      Behaviors.same
    }
  }

  def demonParent(): Unit = {
    val parentSystem = ActorSystem(Parent_V2(), "ParentSystem")
    parentSystem ! CreateChild("Andre Lucas")
    parentSystem ! TellChild("Andre Lucas", "Hello")

    parentSystem.terminate()

  }

  def demonUserGuardian(): Unit = {
    val userGuardian: Behavior[Nothing] = Behaviors.setup{ context =>
      // Setup all the important actors in the app
      // Setup the initial interaction

      val parentSystem = context.spawn(Parent_V2(), "Parent") // parent is a child of userGuardian
      parentSystem ! CreateChild("Andre")
      parentSystem ! CreateChild("Silva")

      parentSystem ! TellChild("Andre", "Hello Jovem")
      parentSystem ! TellChild("Silva", "Hello Velho")

      parentSystem ! TellChild("Andre", "Hello Velho")
      parentSystem ! TellChild("Santos", "Hello Velho")

      //user guardian usually has not behaviors
      Behaviors.empty
    }

    val system = ActorSystem(userGuardian, "System")
    Thread.sleep(1000)

    system.terminate()
  }

  def main(args: Array[String]): Unit = {
    demonUserGuardian()
  }
}
