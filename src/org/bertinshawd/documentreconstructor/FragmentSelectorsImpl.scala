package org.bertinshawd.documentreconstructor


/** A selector that doesn't do any optimization
 */
object NoHeuristicSelector extends DocumentFragmentSelector {
  
  override def getCandidate(candidates: List[String]):String = {
    return candidates(0)
  }
  
  /** Get only the fragments that can possibly be matched, reducing the amount of candidates to greedy match with
   */
  override def getMatchingCandidates(candidate: String, candidates: List[String]): List[String] = {
    return candidates
  }
}

/** A selector that uses some basic frequency heuristics
 */
class SimpleFrequencySelector(final val input: List[String]) extends DocumentFragmentSelector {
  import scala.collection.mutable.ListMap


  /**  Counts all characters in the list of input string fragments
   *   
   *   Used for statistical optimisation of the solver
   */
  class CharacterCountMap( final val input: List[String]) extends ListMap[Char, Int] {
    private var total$: Double = 0
    
    /**
     * After construction, this will be the total number of characters across ALL fragments.
     */
    def total() = total$
  
    /* counts each character of each fragment
     */
    input.foreach { fragment: String =>
      fragment.foreach { chr: Char =>
        total$ += 1
        put(chr, getOrElseUpdate(chr, 0) + 1)
      }
    }
  }
  
  /** Converts a CharacterCountMap into a simple frequency distribution
   */
  class CharacterFrequencyMap( final val input: CharacterCountMap) extends ListMap[Char, Double] {
    input.foreach { item: (Char, Int) =>
      val chr = item._1                 // the character
      val count = item._2               // the count
      put(chr, count / input.total)     // save the frequency entry
    }
  }
  
  private val characterCounts = new CharacterCountMap(input)
  private val characterFrequencies = new CharacterFrequencyMap(characterCounts)

  /** Simple class to count the number of shared distinct characters in two strings
   */
  case class DistinctCharacterIntersection(candidate: String, fragment: String) extends StringLikenessHeuristic {
    override def value(): Int = {
      return fragment.distinct.intersect(candidate.distinct).length()
    }
  }

  /**  Compute the 'rarest' character in a fragment 
   *   
   *   'Rarer' fragments will be matched with less candidates, which means less 
   *   costly greedy matching later.
   *   
   *   While this doesn't reduce the Big-O complexity, it's a substantial optimisation.
   */
  def minFrequencyOfFragment(fragment: String):Double = {
    return fragment
      .map { c => characterFrequencies(c) }
      .asInstanceOf[Vector[Double]]
      .min
  }

  /** Gets the fragment of the list that has the rarest character. 
   */
  override def getCandidate(candidates: List[String]):String = {
    return candidates.minBy { fragment => minFrequencyOfFragment(fragment) }
  }
  
  /** Gets only those fragments that share some distinct characters
   */
  override def getMatchingCandidates(candidate: String, candidates: List[String]): List[String] = {
    return candidates
      .map { fragment: String => DistinctCharacterIntersection(candidate, fragment) }
      .filter { f => f.value() > 0 }
      .sortBy { f => f.value() }
      .map { f => f.fragment }
  }

}