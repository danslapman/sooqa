package sooqa

package object ast {
  trait Atom
  trait Expr[-In, +Out] extends Atom

  //Producer of T
  type Prod[T] = Expr[Nothing, T]

  case class PRIMITIVE[T](t: T) extends Expr[Nothing, T]
  case class SEQUENCE[T](atoms: Seq[Prod[T]]) extends Expr[Nothing, Seq[T]]
  object SEQUENCE {
    def of[T](producers: Prod[T]*): SEQUENCE[T] = SEQUENCE(producers)
  }
  case class ON_FIELD(path: String, pred: Prod[Boolean]) extends Expr[Nothing, Boolean]
  case class NOT[-I](expr: Expr[I, Boolean]) extends Expr[I, Boolean]
  case class EQ[T](arg: Prod[T]) extends Expr[T, Boolean]
  case class NEQ[T](arg: Prod[T]) extends Expr[T, Boolean]
  case class GT[T](arg: Prod[T]) extends Expr[T, Boolean]
  case class GTE[T](arg: Prod[T]) extends Expr[T, Boolean]
  case class LT[T](arg: Prod[T]) extends Expr[T, Boolean]
  case class LTE[T](arg: Prod[T]) extends Expr[T, Boolean]
  case class IN[T](arg: Prod[Seq[T]]) extends Expr[Seq[T], Boolean]
  case class NIN[T](arg: Prod[Seq[T]]) extends Expr[Seq[T], Boolean]
  case class AND(exprs: SEQUENCE[Boolean]) extends Expr[Nothing, Boolean]
  case class OR(exprs: SEQUENCE[Boolean]) extends Expr[Nothing, Boolean]
}
