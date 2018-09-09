package sooqa.plaintext

import sooqa.ast._
import sooqa.plaintext.ast._

package object renderer {
  val render: PartialFunction[Atom, String] = {
    case PRIMITIVE(value: Int) => value.toString
    case PRIMITIVE(value: String) => s"'$value'"
    case SEQUENCE(values) => values.map(render).mkString("[", ", ", "]")
    case OBJECT(fields) =>
      fields.map(f => s"'${f._1}': ${render(f._2)}").mkString("{", ", ", "}")
    case ON_FIELD(path, predicate) => s"'$path': ${render(predicate)}"
    case EQ(arg) => s"{$$eq: ${render(arg)}}"
    case AND(seq) => s"{$$and: ${seq.map(render).mkString("[", ",", "]")}}"
  }
}
