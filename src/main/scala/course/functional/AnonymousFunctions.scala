package functional

import java.util.UUID

object AnonymousFunctions extends App {

  val doubler: Int => Int = new Function1[Int, Int]{
    override def apply(x: Int): Int = x * 2
  }
  println(s"doubler = ${doubler(2)}")
  // syntax sugar
  val doubler_v2: Int => Int = (x: Int) => x * 2
  println(s"doubler syntax sugar = ${doubler_v2(2)}")

  val adder: (Int, Int) => Int = new Function2[Int, Int, Int]{
    override def apply(x: Int, y: Int): Int = x + y
  }
  println(s"adder = ${adder(2,3)}")
  // syntax sugar
  // lambda = anonymous function instances
  val adder_v2: (Int, Int) => Int = (x: Int, y: Int) => x + y
  println(s"adder syntax sugar = ${adder_v2(2,3)}")

  val something:() => Unit = () => println("Hello World")
  println(something) // take care here
  println(something()) //here is correct

  val randomUUID: () => UUID = () => UUID.randomUUID()
  println(randomUUID())

  //syntax with curly braces
  val bracesRandomUUID = {() =>
    UUID.randomUUID()
  }

  val myNameIs = { (name: String) =>
    s"My name is $name"
  }

  val myNameIsAndILike = { (name: String, like: String) =>
    s"My name is $name, and I like $like"
  }

  println(bracesRandomUUID())
  println(myNameIs("André Lucas"))
  println(myNameIsAndILike("André Lucas", "Scala Lang"))

  //Type inference
  val double_v3: Int => Int = x => x * 2
  val myNameInference: Any => String = x => s"My name is $x"
  println(double_v3(2))
  println(myNameInference("Andre"))
  println(myNameInference(2))

  //Shortest lambda
  val double_v4: Int => Int = _ * 2
  val adder_v3: (Int, Int) => Int = _ + _
  val xyz: (Int, Int, Int) => Int = _ + _ + _
  
  println(s"double_v4 = ${double_v4(2)}")
  println(s"adder_v3 = ${adder_v3(2, 4)}")
  println(s"x + y + z = ${xyz(1, 2, 3)}" )


}
