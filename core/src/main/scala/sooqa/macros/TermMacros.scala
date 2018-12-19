package sooqa.macros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.reflect.runtime.universe._

object TermMacros {
  def createTerm[T <: AnyRef : c.WeakTypeTag, U: c.WeakTypeTag]
    (c: Context {type PrefixType = PropertyAccess[T]})(statement: c.Tree): c.Tree = {

    import c.universe._

    val q"""(..$args) => $select""" = statement

    val selectors = select.collect {
      case Select(_, TermName(property)) => property;
    }.reverse.mkString(".")

    val propertyType = weakTypeOf[U]

    q"""new _root_.sooqa.dsl.Term[${propertyType}] (${selectors})"""
  }
}
