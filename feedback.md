# MP3 Feedback

## Grade: C

## Comments
### `TimeDelayQueue` (Tasks 1 and 2)
- Rep invariant:
    - Provided points are good.
    - Is there any relationship between `timeDelayQueue` and `ids`? What about between `count` and `countt`?
- Abstraction function should refer to the fields and explain how they represent the queue.
- No thread-safety argument or synchronization in code.
- `countt` is poorly named and does not appear to be necessary since you can call `count.get()` in `getTotalMsgCount`.
- Specs:
    - `@return` in specs should be more detailed.
    - For `add`, the part about when it returns false should be moved to `@return`.
    - `@param timeWindow` is missing for `getPeakLoad`, and should specify the units.
- Usage of `Thread.sleep(1)` is confusing.
### `TransientPubSubMessage` (Task 1)
- Description, AF/RI, and thread-safety argument are missing.
### `TwitterListener` (Task 3)
- RI points looks good. You can also mention that `lastFetch` is no earlier than `OCT_1_2022`.
- AF should be more detailed and should refer to the fields.
- Thread-safety argument:
    - A field being `final` does not help if it is a reference type, since it can still be modified.
    - Although the fields are not shared with other objects, a given instance can still be accessed by multiple threads.
    - Using a synchronized datatype is by itself not enough for thread safety.
        - E.g., if you check `containsKey` on a map before doing an operation, the map may have changed by the time you enter the `if` block.
- Usage of `IllegalArgumentException` does not seem reasonable for invalid usernames.
    - Client requests may contain invalid usernames, but this should just count as a failed operation (i.e., return false), not as an unrecoverable error.
- Specs can be more specific about "successful" operations (e.g., `addSubscription` is successful only if there isn't a duplicate subscription already).
### `PhemeService` (Task 3)
- RI can be more specific. For example, the `Map`s that have `UUID` keys should have common `keySet`s, assuming that they represent data associated with each user.
- AF is missing.
- Thread-safety argument is not valid, for similar reasons as `TwitterListener`.
- Spec is missing for constructor.
- Variable naming:
    - Some variable names are too short: `sub` and `t` in instance variables, various one- or two-letter local variable names apart from loop variables.
    - Some variable names are in snake_case instead of lowerCamelCase.
- `ArrayList`s in `sub` appear to be used like tuples for representing (`username`, `twitterUsername`, `pattern`).
    - This is not easily readable due to requiring magic numbers to index each list.
    - Instead of `ArrayList`, you can use a small `private static class` (or a Java 14 `record`) to represent each value.
- Again, be more specific about "successful" operations in specs.
- Specs for methods that use `TwitterListener` are inconsistent since they do not mention the `IllegalArgumentException` that might be thrown.
- `@return` for `isDelivered` is incomplete.
### Other comments
- Code is hard to read in some places due to inconsistent spacing.
- Dense code blocks can use some additional comments to improve readability.
- Style suggestions: 
    - Reduce deep `if-else` nesting by inverting the condition and returning early.
    - Avoid repeated calls to `get()` from a map by storing the value in a temporary local variable.
### Contributions
- All members provided contributions files, and contributions appear fair based on those.
- In the future, merge branches instead of having one member copy all the files over to the main branch. Otherwise, `git blame` makes it look like one member did all the work.


**Detailed Code Analysis**

| Filename | Line | Issue | Explanation |
| -------- | ---- | ----- | ----------- |
|PhemeService.java|14|	TooManyMethods|	This class has too many methods, consider refactoring it.|
|PhemeService.java|17|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|PhemeService.java|18|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|PhemeService.java|20|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|PhemeService.java|21|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|PhemeService.java|22|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|PhemeService.java|23|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|PhemeService.java|24|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|PhemeService.java|25|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|PhemeService.java|78|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|78|	CollapsibleIfStatements|	These nested if statements could be combined|
|PhemeService.java|105|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|107|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|126|	UseObjectForClearerAPI|	Rather than using a lot of String arguments, consider using a container object for those values.|
|PhemeService.java|130|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|132|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|152|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'id' (lines '152'-'171').|
|PhemeService.java|154|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|157|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|157|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|161|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|183|	UseObjectForClearerAPI|	Rather than using a lot of String arguments, consider using a container object for those values.|
|PhemeService.java|187|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|190|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|190|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|190|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|195|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|222|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|225|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|229|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|252|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'd' (lines '252'-'252').|
|PhemeService.java|268|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|295|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'id' (lines '295'-'317').|
|PhemeService.java|296|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|299|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|308|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|311|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|312|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|PhemeService.java|313|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|313|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|PhemeService.java|329|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'id' (lines '329'-'348').|
|PhemeService.java|330|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'recent' (lines '330'-'348').|
|PhemeService.java|331|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|335|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|PhemeService.java|337|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|337|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|PhemeService.java|338|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|340|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|342|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|PhemeService.java|342|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|TimeDelayQueue.java|18|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|TimeDelayQueue.java|18|	AvoidFieldNameMatchingTypeName|	It is somewhat confusing to have a field name matching the declaring class name|
|TimeDelayQueue.java|19|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|TimeDelayQueue.java|20|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|TimeDelayQueue.java|20|	ImmutableField|	Private field 'count' could be made final; it is only initialized in the declaration or constructor.|
|TimeDelayQueue.java|21|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|TimeDelayQueue.java|22|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|TimeDelayQueue.java|23|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|TimeDelayQueue.java|56|	AvoidThrowingRawExceptionTypes|	Avoid throwing raw exception types.|
|TimeDelayQueue.java|80|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TimeDelayQueue.java|81|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TimeDelayQueue.java|81|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TimeDelayQueue.java|86|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TimeDelayQueue.java|86|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TimeDelayQueue.java|94|	AvoidThrowingRawExceptionTypes|	Avoid throwing raw exception types.|
|TimeDelayQueue.java|108|	DataflowAnomalyAnalysis|	Found 'DU'-anomaly for variable 'numbers' (lines '108'-'124').|
|TimeDelayQueue.java|109|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TimeDelayQueue.java|110|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TimeDelayQueue.java|115|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'number' (lines '115'-'118').|
|TimeDelayQueue.java|118|	DataflowAnomalyAnalysis|	Found 'DD'-anomaly for variable 'number' (lines '118'-'118').|
|TimeDelayQueue.java|129|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|3|	UnusedImports|	Unused import 'com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer'|
|TwitterListener.java|4|	UnusedImports|	Unused import 'com.google.gson.Gson'|
|TwitterListener.java|5|	UnusedImports|	Unused import 'com.google.gson.GsonBuilder'|
|TwitterListener.java|8|	UnusedImports|	Unused import 'io.github.redouane59.twitter.dto.tweet.Tweet'|
|TwitterListener.java|35|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|TwitterListener.java|36|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|TwitterListener.java|37|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|TwitterListener.java|38|	BeanMembersShouldSerialize|	Found non-transient, non-static member. Please mark as transient or provide accessors.|
|TwitterListener.java|104|	AvoidReassigningParameters|	Avoid reassigning parameters such as 'pattern'|
|TwitterListener.java|104|	UseLocaleWithCaseConversions|	When doing a String.toLowerCase()/toUpperCase() call, use a Locale|
|TwitterListener.java|106|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|110|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|151|	AvoidReassigningParameters|	Avoid reassigning parameters such as 'pattern'|
|TwitterListener.java|151|	UseLocaleWithCaseConversions|	When doing a String.toLowerCase()/toUpperCase() call, use a Locale|
|TwitterListener.java|153|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|156|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|156|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|161|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|188|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|188|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|188|	UseLocaleWithCaseConversions|	When doing a String.toLowerCase()/toUpperCase() call, use a Locale|
|TwitterListener.java|194|	UseCollectionIsEmpty|	Substitute calls to size() == 0 (or size() != 0, size() > 0, size() < 1) with calls to isEmpty()|
|TwitterListener.java|194|	UseCollectionIsEmpty|	Substitute calls to size() == 0 (or size() != 0, size() > 0, size() < 1) with calls to isEmpty()|
|TwitterListener.java|213|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
|TwitterListener.java|214|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|214|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|214|	LawOfDemeter|	Potential violation of Law of Demeter (method chain calls)|
|TwitterListener.java|215|	LawOfDemeter|	Potential violation of Law of Demeter (object not created locally)|
