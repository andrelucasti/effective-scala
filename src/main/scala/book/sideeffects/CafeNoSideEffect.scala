package book.sideeffects

class Cafe:
  def buyCoffe(cc: CreditCard): (Coffee, Charge) =
    val cup = Coffee()
    (cup, Charge(cc, cup.price))



class CreditCard

class Coffee:
  val price = 2.0

case class Charge(cc: CreditCard, amount: Double):
  def combine(other: Charge) : Charge =
    if cc == other.cc then
      Charge(cc, amount + other.amount)
    else
      throw Exception()