package course.functional.collections.set

abstract class FSet[A] extends (A => Boolean){
  infix def + (elem: A) : FSet[A]
  infix def ++ (anotherSet: FSet[A]): FSet[A]

  def contains(elem: A): Boolean
  def apply(elem: A): Boolean = contains(elem)

  def head : A
  def tail : FSet[A]

  def map[B](f: A => B): FSet[B]
  def flatMap[B](f: A => FSet[B]): FSet[B]
  def filter(predicate: A => Boolean): FSet[A]
  def foreach(f: A => Unit): Unit
}

case class EmptySet[A]() extends FSet[A]:
  override infix def +(elem: A): FSet[A] = FunctionalSuperSet(elem, this)

  override infix def ++(anotherSet: FSet[A]): FSet[A] = anotherSet

  override def contains(elem: A): Boolean = false
  override def head: A = throw new  NoSuchElementException()
  override def tail: FSet[A] = this

  override def map[B](f: A => B): FSet[B] = EmptySet[B]()
  override def flatMap[B](f: A => FSet[B]): FSet[B] = EmptySet[B]()
  override def filter(predicate: A => Boolean): FSet[A] = this
  override def foreach(f: A => Unit): Unit = ()


case class FunctionalSuperSet[A](override val head: A,
                                 override val tail: FSet[A]) extends FSet[A]:

  override infix def +(elem: A): FSet[A] = FunctionalSuperSet(elem, this)

  override infix def ++(anotherSet: FSet[A]): FSet[A] = FunctionalSuperSet(head, tail ++ anotherSet)
  override def contains(elem: A): Boolean =
    if elem == head then true
    else tail.contains(elem)

  override def map[B](f: A => B): FSet[B] = this match
    case FunctionalSuperSet(head, tail) => FunctionalSuperSet(f(head), tail.map(f))

  override def flatMap[B](f: A => FSet[B]): FSet[B] = this match
    case FunctionalSuperSet(head, tail) => FunctionalSuperSet(f(head).head, tail.flatMap(f))

  override def filter(predicate: A => Boolean): FSet[A] =
    if predicate(head) then FunctionalSuperSet(head, tail.filter(predicate))
    else tail.filter(predicate)

  override def foreach(f: A => Unit): Unit =
    f(head)
    tail.foreach(f)


object FunctionalSuperSet {
  def main(args: Array[String]): Unit = {
    val names =
      FunctionalSuperSet("Andre",
        FunctionalSuperSet("Lucas",
          FunctionalSuperSet("Santos",
            FunctionalSuperSet("Silva", EmptySet()
            )
          )
        )
      )

    println(names)
    println(names.filter(_.equals("Santos")))
    println(names.map(n => n.toUpperCase))

    val names2 = FunctionalSuperSet("Andre" + "Lucas", EmptySet())
    val names3 = names2 ++ names


    names3.foreach(println(_))
  }
}

