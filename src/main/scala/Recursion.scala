import scala.annotation.tailrec

object Recursion extends App {

  def factorial(n: Int): Int =
    if n <= 1 then 1
    else
      n * factorial(n - 1)

  println(factorial(3))

  //println(factorial(100000)) // StackOverFlow

  // WHEN YOU NEED LOOPS. USE _TAIL_ RECURSION.

  def anotherFactorial(n: Int, accumulator: BigInt): BigInt = {
    if n <= 1 then accumulator
    else anotherFactorial(n - 1, n * accumulator)
    //The last expression preserve the same stack frame and not use additional stack frames for recursive calls.
  }

  // anotherFactorial(3, 1) = (2, 3 * 1) (3)
  // anotherFactorial(2, 3) = (1, 2 * 3) (6)
  // anotherFactorial(1, 6) =            (6)
  println(anotherFactorial(3, 1))

  // With Wrapping the function

  def auxiliarFactorial(n: Int): BigInt = {
    @tailrec
    def anotherFactorialUnit(n: Int, accumulator: BigInt): BigInt = {
      if n <= 1 then accumulator
      else anotherFactorialUnit(n - 1, n * accumulator) // TAIL RECURSION = use recursive call as the LAST expression
    }
    anotherFactorialUnit(n, 1)
  }

  println(auxiliarFactorial(3))


  // 1. Concatenate a String n times
  def concatenateString(times: Int, name: String): String =
    @tailrec
    def concatenateStringUntil(n: Int, aName: String, accumulator: String): String = {
      if n < 1 then accumulator
      else concatenateStringUntil(n - 1, aName, accumulator + aName)
    }
    concatenateStringUntil(times, name, "")

  println(concatenateString(1000, "andre \n"))

  // 2. IsPrime function tail recursive
  def isPrime(number: Int):Boolean =
    @tailrec
    def aux(n: Int, isStillPrime: Boolean): Boolean = {
      if !isStillPrime then return false
      if n <= 1 then true
      else aux(n - 1, isStillPrime = number % n != 0)
    }
    aux(number/2, true)

    // isPrime(2) =
    // aux(2, true) = (1, false)


  println(isPrime(1))
  println(isPrime(2))
  println(isPrime(3))
  println(isPrime(4))
  println(isPrime(37))
  println(isPrime(2003))
  println(isPrime(37 * 17))
}

