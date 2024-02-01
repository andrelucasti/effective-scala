package book.sideeffects

// Adding payment object

class Cafe2:
  // is possible now to use a mock framework
  def buyCoffee2(cc: CreditCard2, p: Payment): Coffee2 = 
    // But there's a difficult to reuse buyCoffee, 
    // I want to buy 12 cups of coffee, how i can do that?
    val cup = Coffee2()
    p.charge(cc, cup.price)
    cup

class  CreditCard2
trait Payment:
  def charge(cc: CreditCard2, price: Double): Unit

class SimulatePayments extends Payment:
  override def charge(cc: CreditCard2,
                      price: Double): Unit =
    println(s"Charging $price to cc")

class Coffee2:
  val price: Double = 2.0

//val cc = CreditCard2()
//val p =  SimulatePayments()
//val cafe = Cafe2()
//val cup = cafe.buyCoffee(cc, p)

