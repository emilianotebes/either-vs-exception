object Runner {
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
