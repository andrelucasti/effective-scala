object Functions extends App {

  def introduce(name: String, age: Int) = s"Hi, my name is $name and I am $age years old. "
  //println(introduce("andre", 29))

  def factorial(n: Int): Int = if (n == 1) n else n * factorial(n - 1)
  println(factorial(5))

  def fibonacci(n: Int): Int = {
    if (n < 2) 1
    else {
      val before = fibonacci(n - 2)
      val current = fibonacci(n - 1)
      val result = current + before

      println(s"($before + $current) = $result")

      result
    }
  }

  println(fibonacci(9))

  def isNumberPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean =
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t - 1)

    isPrimeUntil(n / 2)
  }

  println(isNumberPrime(37))
  println(isNumberPrime(2003))
  println(isNumberPrime(37 * 17))
}
