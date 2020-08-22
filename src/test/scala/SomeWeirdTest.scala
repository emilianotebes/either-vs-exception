import org.scalatest.flatspec._
import org.scalatest.matchers._

import scala.math.BigDecimal.RoundingMode

class SomeWeirdTest extends AnyFlatSpec with should.Matchers {

  val greetingWithExceptions = new GreetingWithExceptions()
  val greetingWithMonad = new GreetingWithMonad()

  "Failing using monad" should "take less time than failing with exceptions" in {

    val times = 10000000

    println("Running " + times + " times. Be patient...")
    val executionMetricWithExceptions = runNTimes(times, { i => {
      greetingWithExceptions.greet(i)
    }
    })

    val executionMetricWithMonad = runNTimes(times, { i => {
      greetingWithExceptions.greet(i)
    }
    })

    val durationWithExceptions = executionMetricWithExceptions.timeUsed
    val durationWithMonad = executionMetricWithMonad.timeUsed
    assert(durationWithMonad < durationWithExceptions)

    val absoluteTimeDifference = durationWithExceptions - durationWithMonad
    val percentTimeDifference = BigDecimal(absoluteTimeDifference) / BigDecimal(durationWithMonad) * BigDecimal(100)

    println("Timing: ")
    println(durationWithMonad + "ms using monad")
    println(durationWithExceptions + "ms using exception")
    println(absoluteTimeDifference + " difference")
    println("Failing with exception took " + percentTimeDifference.setScale(4, RoundingMode.UP) + "% longer than monad")
    println("-------------------------")


    println("Memory usage")
    val memoryUsageWithMonad = executionMetricWithMonad.memoryUsed
    val memoryUsageWithExceptions = executionMetricWithExceptions.memoryUsed
    assert(memoryUsageWithMonad < memoryUsageWithExceptions)

    val absoluteMemoryDifference = executionMetricWithExceptions.memoryUsed - executionMetricWithMonad.memoryUsed
    val percentMemoryDifference = BigDecimal(absoluteMemoryDifference) / BigDecimal(memoryUsageWithMonad) * BigDecimal(100)

    println(memoryUsageWithMonad + " bytes using monad")
    println(memoryUsageWithExceptions + " bytes using exception")
    println("Using exception used " + absoluteMemoryDifference + " more bytes than using monad")
    println("Failing with exceptions used " + percentMemoryDifference.setScale(4, RoundingMode.UP) + "% more memory than monad")
    println("-------------------------")

  }

  def runNTimes(times: Int, methodCallback: Int => String): ExecutionResultMetric = {
    var totalUsedMemory = 0L
    var totalUsedTime = 0L

    for (i <- 1 to times) {
      val initialFreeMemory = Runtime.getRuntime.freeMemory()
      val initialTimeNanos = System.nanoTime()
      methodCallback(i)
      val finalTimeNanos = System.nanoTime()
      val finalFreeMemory = Runtime.getRuntime.freeMemory()
      val usedMemoryInIteration = initialFreeMemory - finalFreeMemory
      if (usedMemoryInIteration > 0) {
        totalUsedMemory = totalUsedMemory + usedMemoryInIteration
      } else {
        //Discarding iteration if unlucky enough to get garbage collection in middle.
      }
      totalUsedTime = totalUsedTime + finalTimeNanos - initialTimeNanos
    }
    ExecutionResultMetric(totalUsedTime / 1000, totalUsedMemory)
  }

}