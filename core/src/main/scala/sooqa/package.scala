import sooqa.macros.PropertyAccess

package object sooqa {
  def q[T <: AnyRef]: PropertyAccess[T] = new PropertyAccess[T]
}
