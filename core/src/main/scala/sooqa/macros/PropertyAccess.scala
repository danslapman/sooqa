package sooqa.macros

import sooqa.dsl.Term

import scala.language.experimental.macros

final class PropertyAccess[ParentT <: AnyRef] {
  def apply[T](statement: ParentT => T): Term[T] = macro TermMacros.createTerm[ParentT, T]
}
