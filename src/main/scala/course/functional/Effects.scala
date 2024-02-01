package course.functional

object Effects {

  case class MyIO[A](run:() => A){
    def map[B](f: A => B):MyIO[B] = MyIO(()=> f(run()))
    def flatMap[B](f: A => MyIO[B]):MyIO[B] = MyIO(() => f(run()).run())

    def get:Option[A] = Option(run())
  }

  case class MyWeired[A](f:() => A){
    def value: A = f()
  }

  val plus: () => Int = () => 1 + 1

  val weired = MyWeired(plus)
  val weiredSugar = MyWeired( () => 1 + 1 )


  val convertToInt = MyIO(() => "Andre").map(_.length).map(_ + 1)

  def main(args: Array[String]): Unit = {

    println(weired.f())
    println(weiredSugar.f())

    println(convertToInt.run())
  }
}
