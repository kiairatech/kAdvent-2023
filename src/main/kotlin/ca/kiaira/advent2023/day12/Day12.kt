package ca.kiaira.advent2023.day12

import ca.kiaira.advent2023.Puzzle

/**
 * Object representing the solution to the "Advent of Code - Day 12: Hot Springs" puzzle.
 *
 * This object extends the [Puzzle] class, specifically handling puzzles with [SpringConditionRecords] as input.
 * It provides implementations for parsing the puzzle input and solving both parts of the puzzle.
 *
 * @constructor Creates an instance of Day12 puzzle solver.
 *
 * @author Muthigani Kiaira <mukiaira@gmail.com>
 * @since December 12th, 2023
 */
object Day12 : Puzzle<SpringConditionRecords>(12) {

	/**
	 * A cache to store computed results to avoid redundant calculations.
	 * It maps a triple of spring condition (String?), current run size (Int?), and remaining damaged sizes (List<Int>)
	 * to the count of possible arrangements (Long).
	 */
	private val cache = mutableMapOf<Triple<String?, Int?, List<Int>>, Long>()

	/**
	 * Parses the input into [SpringConditionRecords], which encapsulate the spring conditions and damaged groups.
	 *
	 * @param input The input sequence containing the rows of spring conditions.
	 * @return An instance of [SpringConditionRecords] representing the parsed input.
	 */
	override fun parse(input: Sequence<String>): SpringConditionRecords {
		val rows = input.toList()
		val springConditions = rows.map { HotSpringsArranger(it.split(" ")[0]) }
		val damagedGroups = rows.map { DamagedGroup(it.split(" ")[1].split(",").map { size -> size.toInt() }) }
		return SpringConditionRecords(springConditions, damagedGroups)
	}

	/**
	 * Solves Part 1 of the puzzle: Count all the different arrangements of operational and broken springs that meet
	 * the given criteria for each row.
	 *
	 * @param input An instance of [SpringConditionRecords] representing the puzzle input.
	 * @return The sum of possible arrangement counts for Part 1.
	 */
	override fun solvePart1(input: SpringConditionRecords): Any {
		return input.springConditions.zip(input.damagedGroups).sumOf { (condition, group) ->
			condition.countPossibleArrangements(group.sizes, cache)
		}
	}

	/**
	 * Solves Part 2 of the puzzle: Count all the different arrangements of operational and broken springs that meet
	 * the given criteria for each row after unfolding the condition records.
	 *
	 * @param input An instance of [SpringConditionRecords] representing the puzzle input.
	 * @return The sum of possible arrangement counts for Part 2 after unfolding the records.
	 */
	override fun solvePart2(input: SpringConditionRecords): Any {
		return input.springConditions.zip(input.damagedGroups).sumOf { (condition, group) ->
			val duplicatedCondition =
				HotSpringsArranger((1..5).joinToString("") { "?${condition.description}" }.drop(1))
			duplicatedCondition.countPossibleArrangements(List(5) { group.sizes }.flatten(), cache)
		}
	}
}