package functional

/**
 * HOFs = Higher-Order Functions
 */
object HOFsCurrying {

  // higher order functions (HOFs)

  val aHof: (Int, (Int => Int)) => Int = (x, func) => x * 2
  val anotherHof: (Int => (Int => Int)) = (x) => (y => (x + y) * 2)


  def main(args: Array[String]): Unit = {
    println(anotherHof(2)(2))

  }
}
