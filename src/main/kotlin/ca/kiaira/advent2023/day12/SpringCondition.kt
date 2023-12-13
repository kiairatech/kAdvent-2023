package ca.kiaira.advent2023.day12

/**
 * Abstract base class for representing various conditions of a spring in a spring system.
 * This class serves as a foundation for more specific spring condition types, enabling
 * polymorphic behavior and consistent handling of different spring conditions in the system.
 *
 * @property description A descriptive string detailing the specific nature or characteristic
 *                       of the spring condition. This description is intended to provide
 *                       clarity on the condition's impact or relevance to the system.
 *
 * The primary function of this class is to define a common interface for different spring
 * conditions, particularly focusing on the method to count possible arrangements of springs
 * based on their conditions and the grouping of damages.
 *
 * Example Usage:
 * ```
 * abstract class SpecificSpringCondition(description: String) : SpringCondition(description) {
 *     override fun countPossibleArrangements(
 *         remain: List<Int>,
 *         cache: MutableMap<Triple<String?, Int?, List<Int>>, Long>
 *     ): Long {
 *         // Implementation details here
 *     }
 * }
 * ```
 *
 * The `countPossibleArrangements` function is abstract and must be implemented by subclasses.
 * It calculates the number of possible arrangements of springs, taking into account the
 * remaining damaged group sizes and a cache for optimization purposes. This function is
 * essential for solving complex problems related to spring system configurations and repairs.
 */
abstract class SpringCondition(val description: String) {

	/**
	 * Abstract function to calculate the count of possible arrangements of springs.
	 * This method is critical for assessing the impact of various spring conditions on
	 * the overall system and for planning repair or replacement strategies.
	 *
	 * @param remain A list of integers representing the sizes of remaining groups of damaged springs.
	 *               This parameter is crucial for determining the scope and scale of the problem,
	 *               particularly in scenarios where multiple damages occur.
	 *
	 * @param cache A mutable map acting as a cache to store and retrieve previously computed counts.
	 *              This cache optimizes the calculation process by avoiding redundant computations,
	 *              particularly in complex systems with numerous springs and damage groups.
	 *
	 * @return The count of possible arrangements as a Long value. This count enables system
	 *         administrators or repair technicians to understand the variety and extent of
	 *         viable repair or reconfiguration options available for the spring system.
	 */
	abstract fun countPossibleArrangements(
		remain: List<Int>,
		cache: MutableMap<Triple<String?, Int?, List<Int>>, Long>
	): Long
}
