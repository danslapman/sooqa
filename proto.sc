trait Atom
trait Expr[-In, +Out] extends Atom

case class VALUE[T](t: T) extends Expr[Nothing, T]
case class PATH(p: String) extends Expr[Nothing, Nothing]
case class EQ[T](lhs: Expr[Nothing, T], rhs: Expr[Nothing, T]) extends Expr[(T, T), Boolean]
case class AND(exprs: Expr[Nothing, Boolean]*) extends Expr[Nothing, Boolean]
case class OBJECT(fields: (PATH, Atom)*) extends Atom

def render(k: Atom): String = k match {
  case VALUE(value: Int) => value.toString
  case VALUE(value: String) => s""""$value""""
  case PATH(path) => path
  case EQ(lhs, rhs) => s"{${render(lhs)}: ${render(rhs)}}"
  case AND(exprs @ _*) => s"{$$and: [${exprs.map(render).mkString(", ")}]}"
  case OBJECT(fields @ _*) =>
    val pairs = fields.map { case (k, v) => s"${render(k)}: ${render(v)}"}
    s"{${pairs.mkString(", ")}}"
}

def optimize(k: Atom): Atom = {
  def iteration(k: Atom): Atom = k match {
    case AND(EQ(PATH(p1), VALUE(v1)), EQ(PATH(p2), VALUE(v2))) =>
      OBJECT((PATH(p1), VALUE(v1)), (PATH(p2), VALUE(v2)))
    case atom => atom
  }

  val pass = iteration(k)
  if (k == pass) pass else optimize(pass)
}

val query = AND(EQ(PATH("b"), VALUE(42)), EQ(PATH("c"), VALUE("peka")))
val optimized = optimize(query)

println(render(query))
println(render(optimized))