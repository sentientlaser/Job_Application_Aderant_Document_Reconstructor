package org.bertinshawd.documentreconstructor


/** a generic type to represent some string storage type that has a simple integer similarity metric
 */
trait StringLikenessHeuristic {
  def value(): Int
}

abstract class DocumentFragmentSelector {
  /** Get a candidate fragment, based on some property that might make it faster to select than another.
   */
  def getCandidate(candidates: List[String]):String
  
  /** Get only the fragments that can possibly be matched, reducing the amount of candidates to greedy match with
   */
  def getMatchingCandidates(candidate: String, candidates: List[String]): List[String]
}

/** Api definition for a solver
 *  
 *  Takes a list of fragments as input, and a hueristic selector.
 *  
 *  Works by construction: after construction, the solution is available in the property 'solution'
 */
abstract class DocumentReconstructor(val input: List[String], val selector:DocumentFragmentSelector) {
  /** Placeholder for the solution.
   *  
   *  Is set after construction of the implementing class
   */
  val solution:String
}