package libra

import libra.nonsi.Angle
import libra.ops.quantity.ConvertTo
import ops.base.{Conversion, ConversionFactor, Show}
import spire._
import spire.algebra._
import spire.math._
import spire.implicits._
import singleton.ops._
import libra.si.{Second, Time}
import shapeless._

/* Non-SI units */
package object nonsi {
  type Angle

  implicit def angleShow: Show[Angle] = Show[Angle]("∠")

  /** Angle units */
  trait Degree extends UnitOfMeasure[Angle]
  trait Arcminute extends UnitOfMeasure[Angle]
  trait Arcsecond extends UnitOfMeasure[Angle]
  trait Radian extends UnitOfMeasure[Angle]
  trait Gradian extends UnitOfMeasure[Angle]
  trait Turn extends UnitOfMeasure[Angle]

  implicit def degreeShow: Show[Degree] = Show[Degree]("degree")
  implicit def arcminuteShow: Show[Arcminute] = Show[Arcminute]("arcminute")
  implicit def arcsecondShow: Show[Arcsecond] = Show[Arcsecond]("arcsecond")
  implicit def radian: Show[Radian] = Show[Radian]("rad")
  implicit def gradian: Show[Gradian] = Show[Gradian]("gon")
  implicit def turn: Show[Turn] = Show[Turn]("tr")

  implicit def arcminuteArcsecondConversion[A](
      implicit c: ConvertableTo[A]
  ): ConversionFactor[A, Angle, Arcminute, Arcsecond] =
    new ConversionFactor(c.fromInt(60))

  implicit def radianDegreeConversion[A](
      implicit c: ConvertableTo[A]
  ): ConversionFactor[A, Angle, Radian, Degree] =
    new ConversionFactor(c.fromDouble(180.0 / pi))

  implicit def degreeArcminuteConversion[A](
      implicit c: ConvertableTo[A]
  ): ConversionFactor[A, Angle, Degree, Arcminute] =
    new ConversionFactor(c.fromInt(60))

  implicit def gradianRadianConversion[A](
      implicit c: ConvertableTo[A]
  ): ConversionFactor[A, Angle, Gradian, Radian] =
    new ConversionFactor(c.fromDouble(pi / 200.0))

  implicit def turnGradianConversion[A](
      implicit c: ConvertableTo[A]
  ): ConversionFactor[A, Angle, Turn, Gradian] =
    new ConversionFactor(c.fromInt(400))

  type AngularVelocityQuantity[A, L <: UnitOfMeasure[Angle], T <: UnitOfMeasure[Time]] =
    Quantity[A, Term[Angle, L, Fraction[1, 1]] :: Term[Time, T, Fraction[-1, 1]] :: HNil]

  implicit final class BaseQuantityNonSIOps[A](val a: A) extends AnyVal {
    def degree: QuantityOf[A, Angle, Degree] = Quantity(a)
    def arcminute: QuantityOf[A, Angle, Arcminute] = Quantity(a)
    def arcsecond: QuantityOf[A, Angle, Arcsecond] = Quantity(a)
    def degreessPerSecond: AngularVelocityQuantity[A, Degree, Second] = Quantity(a)
    def arcminutesPerSecond: AngularVelocityQuantity[A, Arcminute, Second] = Quantity(a)
    def arcsecondsPerSecond: AngularVelocityQuantity[A, Arcsecond, Second] = Quantity(a)
    def radian: QuantityOf[A, Angle, Radian] = Quantity(a)
    def gradian: QuantityOf[A, Angle, Gradian] = Quantity(a)
    def turn: QuantityOf[A, Angle, Turn] = Quantity(a)
  }
}
