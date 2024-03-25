package book.handlingerrors

enum Optional[+A]:
  case Some(value: A)
  case None

  def filter(f: A => Boolean): Optional[A] = this match
    case Some(a) => if f(a) then Some(a) else None
    case None => None

  def filterV2(f: A => Boolean): Optional[A] =
    flatMap(c => if f(c) then Some(c) else None)
  
  def map[B](f: A => B): Optional[B] = this match
    case None => None
    case Some(a) => Some(f(a))

  def flatMap[B](f: A => Optional[B]): Optional[B] = this match
    case None => None
    case Some(a) => f(a)

  def getOrElse[B >: A](default: => B): B = this match
    case None => default
    case Some(None) => default
    case Some(a) => a


object Optional {
  def main(args: Array[String]): Unit = {
    val total = Some(10).map(_ * 2)
    val totalFlat = Some(10).flatMap(c => total.map(_ + 2 + c))
    val totalGetOrElse = Some(None).getOrElse(10)
    val totalGetOrElse2 = None.getOrElse(10)

    val filterByLetters1 = Some("Andre").filter(t => t.contains("c"))
    val filterByLetters1V2 = Some("Andre").filterV2(t => t.contains("c"))

    val filterByLetters2 = Some("Andre").filter(t => t.contains("A"))
    val filterByLetters2V2 = Some("Andre").filterV2(t => t.contains("A"))

    println(total)
    println(totalFlat)
    println(totalGetOrElse)
    println(totalGetOrElse2)
    println(filterByLetters1)
    println(filterByLetters1V2)
    println(filterByLetters2)
    println(filterByLetters2V2)
  }
}