package ca.kiaira.advent2023.day12

/**
 * Abstract class representing the general concept of a spring condition.
 *
 * @property description The string representation of the spring condition.
 */
abstract class SpringCondition(val description: String) {

	/**
	 * Abstract function to count possible arrangements. Must be implemented in subclasses.
	 *
	 * @param remain A list of integers representing remaining damaged group sizes.
	 * @param cache A cache used to store and retrieve previously computed counts.
	 * @return The count of possible arrangements as a Long value.
	 */
	abstract fun countPossibleArrangements(
		remain: List<Int>,
		cache: MutableMap<Triple<String?, Int?, List<Int>>, Long>
	): Long
}
