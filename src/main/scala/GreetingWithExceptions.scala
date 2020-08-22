class GreetingWithExceptions {

  def greet(i: Int): String = {
    try {
      greetButMayFail(i)
    } catch {
      case e: MyBusinessException => e.getMessage
      case _ => "WTF?"
    }
  }

  private def greetButMayFail(i: Int): String = {
    if (i % 2 == 0) {
      throw new MyBusinessException("Oops...")
    }
    "Hello"
  }
}


