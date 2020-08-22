# Exceptions vs Either in Scala

## Motivations
One of the arguments often used in favor of monads when returning business errors is that
they are faster than throwing exceptions. A valid question to ask it's if that is true or not. 
And if true, how much faster?

## Testing process
We are going to execute a trivial method. A method that returns a hardcoded String. It fails 50% of executions. 
There are 2 different implementations, one uses `Either[MyError,String]` and the other one throws `MyBusinessException` 
and handles it, to return an error message. 

We execute each method a huge number of times, so that we have enough data. 
After both executions, processing time and memory usage data is collected and compared.  

## Usage
* clone
* run `sbt test`
* read the outcome of the execution and see what happens.

## Results and final thoughts
 Indeed, using exceptions instead of errors is more time consuming (5% slower in average), but strangely, memory usage is not always more. 
 Besides, this test scenario is extreme, because no service fails 50% of the times and is invoked so many times in a row. 
 
 So, is Either really worth? Considering these results, and applied to real use cases, 
 it depends more on a subjective opinion based on code readability and clear internal apis rather than on a perceptible performance improve, and should be used according to the programmer's taste.         