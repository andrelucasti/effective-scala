package OO

object GenericsAnonymousClasses {
  trait Predicate[T]{
    def test(t: T): Boolean
  }

  trait Transformer[A, B] {
    def transform(a: A): B
  }

  class StringToIntTransformer extends Transformer[String, Int] {
    override def transform(a: String): Int = a.toInt
  }


  abstract class LList[A] {
    def head: A
    def tail: LList[A]
    def map[B](function: Function1[A, B]): LList[B]
    def filter(predicate: Function1[A, Boolean]):LList[A]
  }
  case class EmptyList[A]() extends LList[A] {
    override def head: A = throw new NoSuchElementException
    override def tail: LList[A] = throw new NoSuchElementException
    override def map[B](function: Function1[A, B]): LList[B] = EmptyList[B]()
    override def filter(predicate: Function1[A, Boolean]): LList[A] = EmptyList[A]()
  }

  case class MyLinkedList[A](override val head: A, override val tail: LList[A]) extends LList[A] {
    override def map[B](transformer: Function1[A, B]): LList[B] =  
      MyLinkedList[B](transformer.apply(head), tail.map(transformer))

    override def filter(predicate: Function1[A, Boolean]): LList[A] =
      if predicate.apply(head) then MyLinkedList[A](head, tail.filter(predicate))
      else tail.filter(predicate)
  }
  
  case class MyLinkedListSugar[A](override val head: A, override val tail: LList[A]) extends LList[A]{
    override def filter(predicate: A => Boolean): LList[A] =
      if predicate.apply(head) then MyLinkedListSugar(head, tail.filter(predicate))
      else tail.filter(predicate)

    override def map[B](function: A => B): LList[B] = MyLinkedListSugar(function.apply(head), tail.map(function))
  }

  val countChars = new Transformer[String, Int] {
    override def transform(a: String): Int = a.length
  }
  val countChars_v2 = new Function1[String, Int] {
    override def apply(a: String): Int = a.length
  }

  val nameEqualsAndre = new Predicate[String] {
    override def test(t: String): Boolean = t equals "Andre"
  }

  val nameEqualsAndre_v2 = new Function1[String, Boolean] {
    override def apply(t: String): Boolean = t equals "Andre"
  }

  def main(args: Array[String]): Unit = {
    val llNames = new MyLinkedList[String]("Andre",
                      new MyLinkedList[String]("Lucas",
                        new MyLinkedList[String]("Santos",
                          new MyLinkedList[String]("Silva", new EmptyList[String]))))


    println(llNames.head)


    val charsCounted = llNames.map(transformer = countChars_v2)
    println(charsCounted.head)

    val charsCountedLambda = llNames.map(l => l.length)
    println(charsCountedLambda.head)

    val llAsAndre = llNames.filter(nameEqualsAndre_v2)
    println(llAsAndre.head) // Andre
    println(llAsAndre.tail) // Empty $EmptyList@...


    val llAsLucasLambda = llNames.filter(l => l.equals("Lucas"))
    println(llAsLucasLambda.head) // Lucas
    println(llAsLucasLambda.tail) // Empty $EmptyList@...
  }
}
