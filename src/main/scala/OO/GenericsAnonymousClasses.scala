package OO

object GenericsAnonymousClasses {
  trait Predicate[T]{
    def test(t: T): Boolean
  }

  class EvenPredicate extends Predicate[Int] {
    override def test(t: Int): Boolean = ???
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
    def map[B](transformer: Transformer[A, B]): LList[B]
    def filter(predicate: Predicate[A]):LList[A]
  }
  class EmptyList[A] extends LList[A] {
    override def head: A = throw new NoSuchElementException
    override def tail: LList[A] = throw new NoSuchElementException
    override def map[B](transformer: Transformer[A, B]): LList[B] = new EmptyList[B]
    override def filter(predicate: Predicate[A]): LList[A] = new EmptyList[A]

  }

  class MyLinkedList[A](override val head: A, override val tail: LList[A]) extends LList[A] {
    override def map[B](transformer: Transformer[A, B]): LList[B] =
      new MyLinkedList[B](transformer.transform(head), tail.map(transformer))

    override def filter(predicate: Predicate[A]): LList[A] =
      if predicate.test(head) then new MyLinkedList[A](head, tail.filter(predicate))
      else tail.filter(predicate)

  }

  val countChars = new Transformer[String, Int] {
    override def transform(a: String): Int = a.length
  }

  val nameEqualsAndre = new Predicate[String] {
    override def test(t: String): Boolean = t equals "Andre"
  }

  def main(args: Array[String]): Unit = {
    val llNames = new MyLinkedList[String]("Andre",
                      new MyLinkedList[String]("Lucas",
                        new MyLinkedList[String]("Santos",
                          new MyLinkedList[String]("Silva", new EmptyList[String]))))


    println(llNames.head)


    val charsCounted = llNames.map(transformer = countChars)
    println(charsCounted.head)

    val charsCountedLambda = llNames.map(l => l.length)
    println(charsCountedLambda.head)

    val llAsAndre = llNames.filter(nameEqualsAndre)
    println(llAsAndre.head) // Andre
    println(llAsAndre.tail) // Empty $EmptyList@...


    val llAsLucasLambda = llNames.filter(l => l.equals("Lucas"))
    println(llAsLucasLambda.head) // Lucas
    println(llAsLucasLambda.tail) // Empty $EmptyList@...
  }
}
