package ca.kiaira.advent2023.day12

/**
 * Concrete implementation for a specific type of Spring Condition.
 * This class calculates the number of possible arrangements based on a given condition.
 *
 * @param description The description of the arrangement condition.
 */
class HotSpringsArranger(description: String) : SpringCondition(description) {

	/**
	 * Calculates the number of possible arrangements based on the provided conditions.
	 *
	 * @param remain A list of integers representing remaining elements.
	 * @param cache A mutable map used for caching results to optimize performance.
	 *
	 * @return The number of possible arrangements.
	 */
	override fun countPossibleArrangements(
		remain: List<Int>,
		cache: MutableMap<Triple<String?, Int?, List<Int>>, Long>
	): Long {
		// Nested function to perform the actual calculation.
		fun countArrangements(condition: String, withinRun: Int?, remain: List<Int>): Long {
			// Create a unique key for caching based on condition, withinRun, and remain.
			val key = Triple(condition, withinRun, remain)

			// Check if the result is already cached, and if so, return it.
			cache[key]?.let { return it }

			// Check if the condition is empty and calculate possible arrangements accordingly.
			if (condition.isEmpty()) {
				if (withinRun == null && remain.isEmpty()) return 1
				if (remain.size == 1 && withinRun != null && withinRun == remain[0]) return 1
				return 0
			}

			// Count the number of '#' and '?' characters in the condition.
			val possibleMore = condition.count { it == '#' || it == '?' }

			// Various conditions to determine if the arrangement is valid.
			if (withinRun != null && possibleMore + withinRun < remain.sum()) return 0
			if (withinRun == null && possibleMore < remain.sum()) return 0
			if (withinRun != null && remain.isEmpty()) return 0

			var poss: Long = 0 // Initialize the number of possible arrangements.

			// Consider different characters in the condition for arrangement calculation.
			if (condition[0] == '.' && withinRun != null && withinRun != remain[0]) return 0
			if (condition[0] == '.' && withinRun != null) poss += countArrangements(
				condition.drop(1),
				null,
				remain.drop(1)
			)
			if (condition[0] == '?' && withinRun != null && withinRun == remain[0]) poss += countArrangements(
				condition.drop(1),
				null,
				remain.drop(1)
			)
			if ((condition[0] == '#' || condition[0] == '?') && withinRun != null) poss += countArrangements(
				condition.drop(1),
				withinRun + 1,
				remain
			)
			if ((condition[0] == '?' || condition[0] == '#') && withinRun == null) poss += countArrangements(
				condition.drop(1),
				1,
				remain
			)
			if ((condition[0] == '?' || condition[0] == '.') && withinRun == null) poss += countArrangements(
				condition.drop(1),
				null,
				remain
			)

			// Cache the calculated result and return it.
			cache[key] = poss
			return poss
		}

		// Start the calculation with the initial condition.
		return countArrangements(this.description, null, remain)
	}
}
