class GreetingWithExceptions {

  def greet(i: Int): String = {
    try {
      greetButMayFail(i)
    } catch {
      case exception: Exception => exception.getMessage
    }
  }

  private def greetButMayFail(i: Int): String = {
    if (i % 2 == 0) {
      throw new MyBusinessException("Oops...")
    }
    "Hello"
  }
}


