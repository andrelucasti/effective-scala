package course

import scala.annotation.tailrec

object Recursion2 {

  def sumUntil(n: Int): Int = if n <= 0 then 0 else n + sumUntil(n - 1)

  def sumUntil2(n: Int): Int = if n >= 5 then n else sumUntil2(n + 1)

  def concatString(word: String, times: Int): String =
    if times <= 0 then word
    else word + " " + concatString(word, times - 1)

  def concatString_v2(word: String, times: Int): String = {
    @tailrec
    def concatStringTailRec(word: String,
                            times: Int,
                            acc: String): String =

      if times <= 0 then acc
      else concatStringTailRec(word, times - 1, acc + " " + word)

    concatStringTailRec(word, times, word)
  }

  def fibonacci(n: Int): Int =
    @tailrec
    def fibonacciTailRec(n: Int, acc: Int): Int =
      if n <= 0 then acc
      else fibonacciTailRec(n - 1, acc + n)

    fibonacciTailRec(n, 0)

  def main(args: Array[String]): Unit = {

    println(concatString("andre", 1))
    println(concatString_v2("lucas", 1))

    println(fibonacci(20000))
  }

}
