package book.datastructure

import scala.annotation.tailrec

enum List[+A] {
  case Empty
  case Cons(head: A, tail: List[A])
}

object List:
  def apply[A](as: A*): List[A] =
    if as.isEmpty then Empty
    else Cons(as.head, apply(as.tail*))

  def sum(numbers: List[Int]): Int = numbers match
    case Empty => 0
    case Cons(head, tail) => head + sum(tail)

  def tail[A](as: List[A]): List[A] =  as match
    case Cons(_, as) => as
    case Empty => sys.error("List empty")

  def setHead[A](head: A, tail: List[A]): List[A] = head match
    case _ => Cons(head, List.tail(tail))

  @tailrec
  def drop[A](as: List[A], n: Int):List[A] = as match
    case Empty => Empty
    case _ =>
      if n <= 0 then as
      else
        drop(tail(as), n - 1)

  def dropFromBook[A](as: List[A], n: Int): List[A] =
    if n <= 0 then as else as match
      case Cons(_, tail) => dropFromBook(tail, n - 1)
      case Empty => Empty

  def dropWhile[A](as: List[A], f: A => Boolean): List[A] = as match
    case Empty => Empty
    case Cons(head, tail) if f(head) => dropWhile(tail, f)
    case _ => as

  def main(args: Array[String]): Unit = {
    val myNames = List("Andre", "Lucas", "Santos", "Silva")

    println(myNames) //Cons(Andre,Cons(Lucas,Cons(Santos,Cons(Silva,Empty))))
    println(tail(myNames)) // Cons(Lucas,Cons(Santos,Cons(Silva,Empty)))
    println(setHead("O Pestinha", myNames)) // Cons(Chocolate,Cons(Lucas,Cons(Santos,Cons(Silva,Empty))))
    println(drop(myNames, 3)) // Cons(Silva,Empty)
    
  }