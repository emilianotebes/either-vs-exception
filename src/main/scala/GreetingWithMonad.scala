class GreetingWithMonad {
  def greet(i: Int): String = {
    greetButMayFail(i) match {
      case Right(message) => message
      case Left(error) => error.message
    }
  }

  private def greetButMayFail(i: Int): Either[MyError, String] = {
    if (i % 2 == 0) {
      Left(MyError("Oops..."))
    }
    Right("Hello")
  }
}
