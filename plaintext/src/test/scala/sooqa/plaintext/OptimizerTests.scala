package sooqa.plaintext

import sooqa.ast._
import sooqa.plaintext.optimizer._
import sooqa.plaintext.renderer._
import org.scalatest.{FunSuite, Matchers}

class OptimizerTests extends FunSuite with Matchers {
  test("Basic") {
    render(optimize(
      AND(
        SEQUENCE.of(
          ON_FIELD("b", EQ(PRIMITIVE(42))),
          ON_FIELD("c", EQ(PRIMITIVE("peka")))
        )
      ))) shouldBe "{'b': 42, 'c': 'peka'}"
  }

  test("Double unequality") {
    optimize(NOT[Nothing](NOT[Nothing](ON_FIELD("b", EQ(PRIMITIVE(42)))))) shouldBe
      ON_FIELD("b", EQ(PRIMITIVE(42)))
  }
}
