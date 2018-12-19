package sooqa.dsl

import sooqa.ast._

final case class Term[T](`_name`: String) {
  def ===[U <: T](rhs: U) = ON_FIELD(`_name`, EQ(PRIMITIVE(rhs)))
}
