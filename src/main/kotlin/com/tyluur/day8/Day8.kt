package com.tyluur.day8

import com.tyluur.Puzzle

/**
 * Solution for Day 8 of the Advent of Code challenge.
 * It involves navigating a network of nodes based on a sequence of left/right instructions.
 *
 * @author Tyluur <contact@tyluur.com>
 * @since December 8th, 2023
 */
object Day8 : Puzzle<Pair<List<Day8.Direction>, Map<String, Pair<String, String>>>>(8) {

	/**
	 * Enumeration for directions.
	 */
	enum class Direction { LEFT, RIGHT }

	/**
	 * Parses the input data for the Day 8 puzzle.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return A pair of a list of directions (List<Direction>) and a map of node connections (Map<String, Pair<String, String>>).
	 */
	override fun parse(input: Sequence<String>): Pair<List<Direction>, Map<String, Pair<String, String>>> {
		val lines = input.toList() // Convert the sequence to a list

		val directions = lines.first().map { if (it == 'L') Direction.LEFT else Direction.RIGHT }
		val entries = lines.drop(1) // Drop the first line (directions)
			.filter { it.isNotBlank() }.associate { line ->
				val instruction = line.split(" = ")
				val (left, right) = instruction.last().split("(", ")", ", ").filter(String::isNotBlank)
				instruction.first() to (left to right)
			}

		return directions to entries
	}

	/**
	 * Solves Part 1 of the Day 8 puzzle.
	 * Navigates through the node network, starting from node 'AAA', and follows the sequence
	 * of left/right instructions until reaching the node 'ZZZ'.
	 *
	 * @param input The parsed input data consisting of directions and node connections.
	 * @return The number of steps (Long) to reach node 'ZZZ'.
	 */
	override fun solvePart1(input: Pair<List<Direction>, Map<String, Pair<String, String>>>): Long {
		return countSteps("AAA", { it == "ZZZ" }, input).toLong()
	}

	/**
	 * Solves Part 2 of the Day 8 puzzle.
	 * It starts from all nodes ending with 'A' and follows the sequence of instructions
	 * until all paths reach nodes ending with 'Z'.
	 *
	 * @param input The parsed input data consisting of directions and node connections.
	 * @return The number of steps (Long) taken until all nodes being navigated end with 'Z'.
	 */
	override fun solvePart2(input: Pair<List<Direction>, Map<String, Pair<String, String>>>): Long {
		return input.second
			.filter { it.key.endsWith('A') }
			.map { entry -> countSteps(entry.key, { it.endsWith('Z') }, input) }
			.map(Int::toBigInteger)
			.reduce { acc, steps -> acc * steps / acc.gcd(steps) }
			.toLong()
	}

	/**
	 * Counts the number of steps required to navigate from a starting node to a target condition.
	 *
	 * @param from The starting node as a string.
	 * @param until A lambda function defining the condition to meet the target node.
	 * @param network A pair of list of directions and a map of node connections.
	 * @return The number of steps (Int) required to reach the target condition.
	 */
	private fun countSteps(
		from: String,
		until: (String) -> Boolean,
		network: Pair<List<Direction>, Map<String, Pair<String, String>>>,
	): Int {
		val (directions, entries) = network
		var current = from
		var steps = 0

		while (!until(current)) {
			val next = entries[current]!!
			val dir = directions[steps % directions.size]
			current = if (dir == Direction.LEFT) next.first else next.second
			steps++
		}

		return steps
	}
}
