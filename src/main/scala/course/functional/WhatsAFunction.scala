package functional

object WhatsAFunction {

  // FP: functions are "first-class" citizens

  val concatStrings = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1 + v2
  }
  val concatStrings_v2: (String, String) => String = (v1, v2) => v1 + v2

  val functionToAnotherFunction = new Function1[Int, Function0[Int]]{
    override def apply(v1: Int): () => Int = new Function0[Int]:
      override def apply(): Int = 2 * v1
  }

  def main(args: Array[String]): Unit = {
    println(concatStrings("Andre", "Lucas"))
    println(concatStrings_v2("Andre", "Lucas"))

  }
}
