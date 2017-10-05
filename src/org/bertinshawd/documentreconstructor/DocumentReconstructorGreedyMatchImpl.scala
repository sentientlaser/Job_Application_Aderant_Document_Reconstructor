package org.bertinshawd.documentreconstructor

/** The actual solver.  Implementation of the solver interface 
 */
class GreedyMatchSolver(override final val input: List[String], override final val selector:DocumentFragmentSelector) extends DocumentReconstructor(input, selector){
  /** A class representing a match 
   *  
   *  to improve readbility this includes two strings, the match length and 
   *  if the match is left to right (or right to left).
   */
  final case class Match(
    val fragment1: String, 
    val fragment2: String,
    val length: Int,
    val isLeft: Boolean)

  /** Removes a string from a list of strings
   */
  private final def removeCandidate(candidate: String, candidates: List[String]):List[String] = {
    val ind = candidates.indexOf(candidate)
    return candidates.take(ind) ++ candidates.drop(ind + 1)
  }

  /** Compute the match length between two strings.
   */
  protected def matchLength(left: String, right: String): Int = {
    var ind = 0
    for (ind <- 1 to left.length()) {
      def rightPart = right.take(ind)
      if (left.endsWith(rightPart)) {
        return rightPart.length()
      }
    }
    return 0
  }

  /** computes all possible matches of a candidate string and possible matches.
   */
  protected def greedyMatchLengths(candidate: String, matches: List[String]):List[Match] = {
    val leftMatches = matches.map { fragment =>
      Match(fragment, candidate, matchLength(fragment, candidate), true)
    }
    val rightMatches = matches.map { fragment =>
      Match(candidate, fragment, matchLength(candidate, fragment), false)
    }

    val allMatches = leftMatches ++ rightMatches

    if (allMatches.length == 0) {
      throw new Exception("Cannot continue matching")
    }

    return allMatches
  }

  /** get the best match by match length
   */
  protected def bestMatchFrom(candidates: List[Match]):Match = {
    return candidates.sortBy { f => -f.length }.head
  }

  /** 'weld' the match together: returns the two fragments joined in order with no overlap
   */
  protected def weldMatch(value: Match): String = {
    val left = value.fragment1
    val right = value.fragment2.drop(value.length)
    return left + right
  }

  /** Compute a new list based on an existing list, but with all matched strings removed
   */
  protected def removeWeldedMatch(value: Match, remainder: List[String]): List[String] = {
    var list = removeCandidate(value.fragment2, remainder)
    list = removeCandidate(value.fragment1, list)
    return list
  }

  /** Recursively reconstruct the document.
   *  
   *  This function is only really used as an initialiser for the 'solution' property
   */
  protected def solve(candidates: List[String]): String = {
    if (candidates.size == 1) return candidates(0)                       // when the fragments are a singleton list, halt

    val candidate = selector.getCandidate(candidates)                    // get a candidate fragment
    val remainder = removeCandidate(candidate, candidates)               // get all the remaining fragments
    val matches = selector.getMatchingCandidates(candidate, remainder)   // get all valid matches
    val matchLengths = greedyMatchLengths(candidate, matches)            // get all matches
    val bestMatch = bestMatchFrom(matchLengths)                          // get only the best match
    val newString = weldMatch(bestMatch)                                 // join the best matched strings
    val newRemainder = removeWeldedMatch(bestMatch, remainder)           // remove the matched strings
    val newCandidates = newRemainder :+ newString                        // add the newly welded string to the list
    return solve(newCandidates)                                          // solve with the updated set of fragments
  }

  /**  This property contains the solution.  
   *   
   *   Since it is lazy, it will only be computed on access.
   */
  override lazy val solution = solve(input)
}








