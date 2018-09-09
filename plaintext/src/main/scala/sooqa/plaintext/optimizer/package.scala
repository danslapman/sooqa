package sooqa.plaintext

import sooqa.ast._
import sooqa.plaintext.ast._

package object optimizer {
  def optimize(k: Atom): Atom = {
    def iteration(k: Atom): Atom = k match {
      case AND(atoms)
        if atoms.forall(_.isInstanceOf[ON_FIELD]) && atoms.forall(_.asInstanceOf[ON_FIELD].pred.isInstanceOf[EQ[_]]) =>
        OBJECT(atoms.map(_.asInstanceOf[ON_FIELD]).map(of => of.path -> of.pred.asInstanceOf[EQ[_]].arg).toMap)
      case NOT(NOT(expr)) => expr
      case atom => atom
    }

    val pass = iteration(k)
    if (k == pass) pass else optimize(pass)
  }
}
