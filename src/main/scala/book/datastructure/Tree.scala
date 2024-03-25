package book.datastructure

enum Tree[+A]:
  case Leaf(value: A)
  case Branch(left: Tree[A], right: Tree[A])

  def size: Int = this match
    case Leaf(_) => 1
    case Branch(left, right) => left.size + right.size



object Tree:
  extension (tree: Tree[Int])
    def firstPositive: Int = tree match
      case Leaf(value) => value
      case Branch(left, right) =>
        val leftPos = left.firstPositive
        if leftPos > 0 then leftPos
        else right.firstPositive

    def maximum: Int = tree match
      case Leaf(value) => value
      case Branch(left, right) => left.maximum.max(right.maximum)

  def main(args: Array[String]): Unit = {
    val b = Branch(Leaf("Andre"), Leaf("Lucas"))

    val numbers = Branch(
      Branch(
        Leaf(-50),
        Branch(
          Leaf(0),
          Leaf(20)
        )
      ),
      Branch(
        Leaf(-10),
        Leaf(50)
      )
    )

    println(numbers.firstPositive)
    println(b.size)
    println(numbers.maximum)
  }
