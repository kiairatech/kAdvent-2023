package com.tyluur.day12

import com.tyluur.Puzzle

/**
 * Day12 class represents the solution to Advent of Code 2023 - Day 12: Hot Springs puzzle.
 * This class extends the [Puzzle] class, which is a base class for AoC puzzle solutions.
 *
 * @constructor Creates a Day12 instance.
 *
 * @author Tyluur
 * @since December 10th, 2023
 */
object Day12 : Puzzle<Pair<List<String>, List<List<Int>>>>(12) {

	// A cache to store computed results to avoid redundant calculations
	private val cache = mutableMapOf<Triple<String?, Int?, List<Int>>, Long>()

	/**
	 * Parse the input into a pair of lists: spring conditions and damaged groups.
	 *
	 * @param input The input sequence containing the rows of spring conditions.
	 * @return A pair of lists where the first list represents spring conditions and
	 * the second list represents damaged groups.
	 */
	override fun parse(input: Sequence<String>): Pair<List<String>, List<List<Int>>> {
		val rows = input.toList()
		val springDescriptions = rows.map { row ->
			val parts = row.split(" ")
			val springCondition = parts[0]
			val damagedGroups = parts[1].split(",").map { it.toInt() }
			Pair(springCondition, damagedGroups)
		}
		val springConditions = springDescriptions.map { it.first }
		val damagedGroups = springDescriptions.map { it.second }
		return Pair(springConditions, damagedGroups)
	}

	/**
	 * Solve Part 1 of the puzzle: Count all the different arrangements of operational
	 * and broken springs that meet the given criteria for each row.
	 *
	 * @param input A pair of lists where the first list represents spring conditions
	 * and the second list represents damaged groups.
	 * @return The sum of possible arrangement counts.
	 */
	override fun solvePart1(input: Pair<List<String>, List<List<Int>>>): Any {
		val (springConditions, damagedGroups) = input
		var countArrangements: Long = 0
		springConditions.forEachIndexed { index, condition ->
			countArrangements += countPossibleArrangements(condition, null, damagedGroups[index])
		}
		return countArrangements
	}

	/**
	 * Solve Part 2 of the puzzle: Count all the different arrangements of operational
	 * and broken springs that meet the given criteria for each row after unfolding
	 * the condition records.
	 *
	 * @param input A pair of lists where the first list represents spring conditions
	 * and the second list represents damaged groups.
	 * @return The sum of possible arrangement counts after unfolding the records.
	 */
	override fun solvePart2(input: Pair<List<String>, List<List<Int>>>): Any {
		val (springConditions, damagedGroups) = input
		var countArrangements: Long = 0
		springConditions.forEachIndexed { index, condition ->
			val duplicatedCondition = (1..5).joinToString("") { "?$condition" }
			countArrangements += countPossibleArrangements(duplicatedCondition.drop(1), null, List(5) { damagedGroups[index] }.flatten())
		}
		return countArrangements
	}

	/**
	 * Recursive function to count the possible arrangements of operational and broken springs
	 * that meet the given criteria for a row.
	 *
	 * @param condition The current spring condition as a string.
	 * @param withinRun The size of the current run of damaged springs (null if not within a run).
	 * @param remain The list of remaining damaged group sizes.
	 * @return The count of possible arrangements.
	 */
	private fun countPossibleArrangements(condition: String, withinRun: Int?, remain: List<Int>): Long {
		// Create a key to store results in the cache
		val key = Triple(condition, withinRun, remain)
		cache[key]?.let { return it } // If result is already cached, return it

		if (condition.isEmpty()) {
			// Base case: if the condition is empty, check if it's a valid arrangement
			if (withinRun == null && remain.isEmpty()) return 1
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

		// Handle different cases for each type of spring condition
		if (condition[0] == '.' && withinRun != null && withinRun != remain[0]) return 0
		if (condition[0] == '.' && withinRun != null) poss += countPossibleArrangements(condition.drop(1), null, remain.drop(1))
		if (condition[0] == '?' && withinRun != null && withinRun == remain[0]) poss += countPossibleArrangements(condition.drop(1), null, remain.drop(1))
		if ((condition[0] == '#' || condition[0] == '?') && withinRun != null) poss += countPossibleArrangements(condition.drop(1), withinRun + 1, remain)
		if ((condition[0] == '?' || condition[0] == '#') && withinRun == null) poss += countPossibleArrangements(condition.drop(1), 1, remain)
		if ((condition[0] == '?' || condition[0] == '.') && withinRun == null) poss += countPossibleArrangements(condition.drop(1), null, remain)

		// Cache the result and return it
		cache[key] = poss
		return poss
	}
}