package course.functional

case class Person(name: String, age: Int)
object Person {
  def unapply(p: Person): Option[String] = Some(p.name)
}

object PartialFunction {
  val aFunction: (Int, Int) => Int = (x, y) => x + y

  val aFussyFunction = (p: Person) =>
    if (p.name == "andre") 1994
    else if (p.name == "karol") 1993
    else throw new RuntimeException("no suitable possible")

  val aFussyFunction2: PartialFunction[Person, Int] = {
    case Person("andre") => 1994
    case Person("karol") => 1993
  }

  val aFussyFunction3 = (p: Person) => p match
    case Person("andre") => 1994
    case Person("karol") => 1993

  def main(args: Array[String]): Unit = {
    println(aFunction(1, 2))

    println(aFussyFunction2(Person("andre", 21)))
  }

}
