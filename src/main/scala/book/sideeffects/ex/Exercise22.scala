package book.sideeffects.ex

import scala.annotation.tailrec

object Exercise22 {

  def isSorted[A](array: Array[A], gt: (A,A) => Boolean): Boolean = {
    @tailrec
    def loop(n:Int): Boolean =
      if n + 1 >= array.length then true
      else if (gt(array(n+1), array(n)))
        loop(n + 1)
      else false

    loop(0)
  }

  def main(args: Array[String]): Unit = {
    println(isSorted(Array(1), _ > _)) // true

    println(isSorted(Array(1,2), _ > _)) // true
    println(isSorted(Array(1,2,3), _ > _)) // true
    println(isSorted(Array(1,4,3), _ > _)) // false

    println(isSorted(Array(6,5), _ < _)) // true
    println(isSorted(Array(5,4,3), _ < _)) // true
    println(isSorted(Array(7,5,4,3), _ < _)) // true

  }
}
