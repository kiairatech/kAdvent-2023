package com.tyluur.day12

/**
 * Concrete implementation of [SpringCondition] for a specific type of spring condition.
 * This class handles the logic to count the number of possible arrangements of springs based on their condition.
 *
 * @property description The string representation of the spring condition.
 */
class SpecificSpringCondition(description: String) : SpringCondition(description) {

	/**
	 * Counts the possible arrangements of operational and broken springs that meet the criteria.
	 * It uses a recursive approach to explore all valid combinations based on the condition and remaining groups.
	 *
	 * @param remain A list of integers representing remaining damaged group sizes.
	 * @param cache A cache used to store and retrieve previously computed counts, to improve efficiency.
	 * @return The count of possible arrangements as a Long value.
	 */
	override fun countPossibleArrangements(
		remain: List<Int>,
		cache: MutableMap<Triple<String?, Int?, List<Int>>, Long>
	): Long {
		/**
		 * Recursive helper function to count arrangements.
		 * It explores different scenarios based on the current condition character and the state of the remaining groups.
		 *
		 * @param condition The current spring condition as a string.
		 * @param withinRun The size of the current run of damaged springs (null if not within a run).
		 * @param remain The list of remaining damaged group sizes.
		 * @return The count of possible arrangements.
		 */
		fun countArrangements(condition: String, withinRun: Int?, remain: List<Int>): Long {
			val key = Triple(condition, withinRun, remain)

			// Return cached result if available to avoid redundant calculations
			cache[key]?.let { return it }

			// Base case: if the condition string is empty
			if (condition.isEmpty()) {
				// If not within a run and no remaining groups, it's a valid arrangement
				if (withinRun == null && remain.isEmpty()) return 1
				// If the current run matches the only remaining group size, it's valid
				if (remain.size == 1 && withinRun != null && withinRun == remain[0]) return 1
				return 0
			}

			// Calculate the number of possible additional damaged springs
			val possibleMore = condition.count { it == '#' || it == '?' }

			// Check conditions for valid arrangements
			if (withinRun != null && possibleMore + withinRun < remain.sum()) return 0
			if (withinRun == null && possibleMore < remain.sum()) return 0
			if (withinRun != null && remain.isEmpty()) return 0

			var poss: Long = 0

			// Explore different scenarios based on the first character of the condition string
			when (condition[0]) {
				'.' -> {
					// If the first spring is operational and not part of the current damaged group
					if (withinRun != null && withinRun != remain[0]) return 0
					// Continue with the next spring, ending the current damaged group if any
					if (withinRun != null) poss += countArrangements(condition.drop(1), null, remain.drop(1))
				}
				'#', '?' -> {
					// If the first spring is damaged or unknown, and matches the current damaged group size
					if (withinRun != null && withinRun == remain[0])
						poss += countArrangements(condition.drop(1), null, remain.drop(1))
					// Continue with the next spring, increasing the size of the current damaged group
					if (withinRun != null)
						poss += countArrangements(condition.drop(1), withinRun + 1, remain)
					// Start a new damaged group if not currently within a run
					if (withinRun == null)
						poss += countArrangements(condition.drop(1), 1, remain)
				}
				else -> {
					// If the condition is unknown, explore both possibilities: operational and damaged
					if (withinRun == null)
						poss += countArrangements(condition.drop(1), null, remain)
				}
			}

			// Cache and return the result
			cache[key] = poss
			return poss
		}

		// Initial call to the recursive function with the full condition string
		return countArrangements(this.description, null, remain)
	}
}
