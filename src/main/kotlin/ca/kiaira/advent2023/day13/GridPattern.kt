package ca.kiaira.advent2023.day13

/**
 * This class represents a grid pattern, which is a 2D array of characters.
 * It provides methods for transposing the grid and finding a matching reflection.
 * @param grid The 2D array of characters that represents the grid pattern.
 *
 * @author Muthigani Kiaira <mukiaira@gmail.com>
 * @since December 12th, 2023
 */
class GridPattern(private val grid: Array<CharArray>) {

	/**
	 * Transposes the current grid pattern, swapping rows and columns.
	 * @return A new GridPattern object with the transposed grid.
	 */
	fun transpose(): GridPattern {
		val transposedGrid = Array(grid[0].size) { col ->
			CharArray(grid.size) { row -> grid[row][col] }
		}
		return GridPattern(transposedGrid)
	}

	/**
	 * Finds the index at which the grid pattern has a matching reflection with a given number of non-matching places.
	 * @param nonMatchingCount The number of non-matching places allowed for a valid reflection.
	 * @return The index of the first valid reflection found or null if none is found.
	 */
	fun findMatchingReflection(nonMatchingCount: Int): Int? {
		for (i in 0 until grid.size - 1) {
			var nonMatchingPlaces = 0
			for (j in grid.indices) {
				val mirroredIndex = i + 1 + (i - j)
				if (mirroredIndex in grid.indices) {
					// Compare corresponding characters in the current row and mirrored row
					nonMatchingPlaces += grid[j].indices.count { k ->
						grid[j][k] != grid[mirroredIndex][k]
					}
				}
			}
			if (nonMatchingPlaces == nonMatchingCount) {
				return i
			}
		}
		return null
	}

	companion object {
		/**
		 * Solves the grid pattern to find a matching reflection with a given number of non-matching places.
		 * If a valid reflection is found, it returns the result as 100 times the index plus one.
		 * @param pattern The GridPattern to be solved.
		 * @param nonMatchingCount The number of non-matching places allowed for a valid reflection.
		 * @return The result of the solving process, or 0 if no valid reflection is found.
		 */
		fun solvePattern(pattern: GridPattern, nonMatchingCount: Int): Int {
			pattern.findMatchingReflection(nonMatchingCount)?.let { return 100 * (it + 1) }
			pattern.transpose().let { transposedPattern ->
				transposedPattern.findMatchingReflection(nonMatchingCount)?.let { return it + 1 }
			}
			return 0
		}
	}
}