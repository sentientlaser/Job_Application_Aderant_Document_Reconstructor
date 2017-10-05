package org.bertinshawd.documentreconstructor

import org.junit.Assert

/**
 * Helper to create fragemented data.  I found that manually fragmenting text was a waste of time.
 */
abstract class DocumentReconstructorTestFixture {
  /** The solver instance to use.  Allows testing of different selectors, etc.
   */
  val solver:DocumentReconstructor
  
  /**  Abstract placeholder for the test data: override this in subclasses to create different test cases 
   */
  val raw:String
  
  /** the test fragments, based on the input string `raw`
   */
  final lazy val fragments:List[String] = randomize(fragment(raw))
  
  /** Helper to create a random value
   */
  final private def rnd(min:Int, range:Int)= ((Math.random() * range) + min).asInstanceOf[Int]
  
  /** Randomize a list of strings
   */
  final private def randomize(input:List[String]):List[String] = {
     if (input.length == 1) return input
     val ind = (Math.random() * input.length-1).asInstanceOf[Int] + 1
     val fragment = input(ind)
     val remainder = input.take(ind) ++ input.drop(ind+1)
     return randomize(remainder) :+ fragment 
  }
  
  /** Take a string and break it into a number of randomized, overlapping substrings
   */
  final private def fragment(data:String):List[String] = {
    if (data.length() <= 25) {
      return List[String](data)
    }
    val fragmentLength = rnd(10, 15)
    val fragmentOverlap = rnd(5, 5)
    val fragmentValue = data.substring(0, fragmentLength)
    val remainder = data.substring(fragmentLength - fragmentOverlap, data.length)
    return fragment(remainder) :+ fragmentValue
  }
  
  /** Run the test against the overloaded property raw
   */
  protected final def run():Unit = {
    println(solver.solution)
    Assert.assertEquals(raw, solver.solution)
  }
}