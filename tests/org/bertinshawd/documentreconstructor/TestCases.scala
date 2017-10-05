package org.bertinshawd.documentreconstructor

import org.junit.Test


class TestCase1 extends DocumentReconstructorTestFixture{
  override val raw = "I AM forced into speech because men of science have refused to follow my advice without knowing why. It is altogether against my will that I tell my reasons for opposing this contemplated invasion of the antarctic—with its vast fossil hunt and its wholesale boring and melting of the ancient ice caps. And I am the more reluctant because my warning may be in vain."
  override val solver = new GreedyMatchSolver(fragments, new SimpleFrequencySelector(fragments))
  @Test def test() = run()
}

class TestCase2 extends DocumentReconstructorTestFixture{
  override val raw = "Doubt of the real facts, as I must reveal them, is inevitable; yet, if I suppressed what will seem extravagant and incredible, there would be nothing left. The hitherto withheld photographs, both ordinary and aerial, will count in my favor, for they are damnably vivid and graphic. Still, they will be doubted because of the great lengths to which clever fakery can be carried. The ink drawings, of course, will be jeered at as obvious impostures, notwithstanding a strangeness of technique which art experts ought to remark and puzzle over."
  override val solver = new GreedyMatchSolver(fragments, NoHeuristicSelector)
  @Test def test() = run()
}

class TestCase3 extends DocumentReconstructorTestFixture{
  override val raw = "In the end I must rely on the judgment and standing of the few scientific leaders who have, on the one hand, sufficient independence of thought to weigh my data on its own hideously convincing merits or in the light of certain primordial and highly baffling myth cycles; and on the other hand, sufficient influence to deter the exploring world in general from any rash and over-ambitious program in the region of those mountains of madness. It is an unfortunate fact that relatively obscure men like myself and my associates, connected only with a small university, have little chance of making an impression where matters of a wildly bizarre or highly controversial nature are concerned."
  override val solver = new GreedyMatchSolver(fragments, NoHeuristicSelector)
  @Test def test() = run()
}