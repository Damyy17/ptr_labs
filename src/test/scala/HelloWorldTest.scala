import PW01.HelloWorld
import org.scalatest.funsuite.AnyFunSuite

class HelloWorldTest extends AnyFunSuite{
  test("HelloWorld.print"){
    assert(HelloWorld.helloWorld() === "Hello PTR")
  }
}
