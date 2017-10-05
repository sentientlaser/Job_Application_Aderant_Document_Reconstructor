# Document Reconstruction for Aderant Application.

- I'm assuming these are natural language documents.  
- I'm going to slurp everything and do it in memory, rather than streaming solutions, as per the email.  I'm also not including file loading, but that would be a matter of either pre-slurping files, or loading streams for all files.
- I'm going to use scala.  This is just a personal preference matter.  Though it is worth noting that scala and C# do have substantial overlap in language pragmatics
- I'm going to make the initial assumption that the document is fully recoverable.  If I have time, I'll add the ability to handle partial reconstruction.
- Though I usually believe in "why and how, not what" commenting, I have added some comments that are descriptive of what's happening.
- In retrospect, I do wish I'd lifted the heuristics (other than the greedy match) into something that could be 'plugged' into the solver.

## Files

- `src/org/bertinshawd/documentreconstructor/DocumentReconstructorApi.scala` - The API definitions
- `src/org/bertinshawd/documentreconstructor/DocumentReconstructorGreedyMatchImpl.scala` - The greedy matching implementation of the document reconstructor
- `src/org/bertinshawd/documentreconstructor/FragmentSelectorsImpl.scala` - Implementations for the fragment selectors
- `tests/org/bertinshawd/documentreconstructor/DocumentReconstructorTestFixture.scala` - A simple abstract class that acts as a test fixture
- `tests/org/bertinshawd/documentreconstructor/TestCases.scala` - The actual unit test cases
