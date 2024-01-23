package book.sideeffects

class Cafe0:
  def buyCoffee(cc: CreditCard0): Coffee0 =
    val cup = Coffee0()
    cc.charge(cup.price) // Side Effects
    cup

class CreditCard0:
  def charge(price: Double): Unit = // Methods that return void, typically have side effects
    println(s"charging $price")

class Coffee0:
  val price: Double = 2.0

val cc = CreditCard0()
val cafe = Cafe0()
val cup = cafe.buyCoffee(cc)

