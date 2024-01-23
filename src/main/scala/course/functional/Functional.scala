package functional

object Functional extends App {

  // 1. a function which takes 2 strings and concatenates them

  val myFunctionalString: (String, String) => String = (v1: String, v2: String) => v1 + v2

  println(myFunctionalString("meu", "ovo"))

  // 3. a Function which returns another function which takes an int and returns an int

  val myFunctionWhichReturnInt: Int => Int = (v1: Int) => v1

  val myFunctionWhichReturnAnotherFunction: Int => Int = (v1: Int) => myFunctionWhichReturnInt(v1)

  println(myFunctionWhichReturnAnotherFunction(10))


  val specialFunc: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]]{
    override def apply(v1: Int): Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(v1: Int): Int = v1 + v1
    }
  }

  println(specialFunc(5)(5))

  val prettyFunction: Function1[Int, Function1[Int, Int]] = (v1: Int) => (v1: Int) => v1

  println(prettyFunction(5)(22))
}
