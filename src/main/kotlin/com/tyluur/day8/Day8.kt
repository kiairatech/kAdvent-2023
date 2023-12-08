package com.tyluur.day8

import com.tyluur.Puzzle

/**
 * Object representing the solution for Day 8 of the Advent of Code challenge.
 *
 * This class extends the abstract Puzzle class, providing specific implementations
 * for parsing the input and solving the puzzle's challenges. The puzzle involves
 * navigating through a network of nodes based on a sequence of left/right instructions.
 * Part 1 focuses on reaching a specific node, while Part 2 involves simultaneous
 * navigation from multiple starting nodes.
 */
object Day8 : Puzzle<Pair<List<Char>, Map<String, Pair<String, String>>>>(8) {

	/**
	 * Parses the input data for the Day 8 puzzle.
	 *
	 * The method converts the input sequence of strings into a pair consisting of a list
	 * of instructions and a map of node connections. The first line of the input represents
	 * the navigation instructions, while the subsequent lines define the node connections.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return A pair of a list of instructions (List<Char>) and a map of node connections (Map<String, Pair<String, String>>).
	 */
	override fun parse(input: Sequence<String>): Pair<List<Char>, Map<String, Pair<String, String>>> {
		val lines = input.toList()
		val instructions = lines.first().toList()
		val nodeMap = lines.drop(2)
			.map { it.split(" = ", "(", ", ", ")").filter { it.isNotEmpty() } }
			.associate { it[0] to (it[1] to it[2]) }
		return instructions to nodeMap
	}

	/**
	 * Solves Part 1 of the Day 8 puzzle.
	 *
	 * Navigates through the node network, starting from node 'AAA', and follows the sequence
	 * of left/right instructions until reaching the node 'ZZZ'. Counts the number of steps taken
	 * to reach the target node.
	 *
	 * @param input The parsed input data consisting of instructions and node connections.
	 * @return The number of steps (Int) to reach node 'ZZZ'.
	 */
	override fun solvePart1(input: Pair<List<Char>, Map<String, Pair<String, String>>>): Any {
		val (instructions, nodeMap) = input

		var current = "AAA" // Starting node
		var steps = 0
		var instructionIndex = 0

		while (current != "ZZZ") {
			val (left, right) = nodeMap[current] ?: error("Node $current not found in map")
			current = if (instructions[instructionIndex] == 'L') left else right
			instructionIndex = (instructionIndex + 1) % instructions.size
			steps++
		}

		return steps
	}

	/**
	 * Solves Part 2 of the Day 8 puzzle.
	 *
	 * Simultaneously navigates from all nodes ending with 'A' based on the sequence of
	 * instructions until all paths reach nodes ending with 'Z'. The process involves tracking
	 * multiple paths and updating them in each step as per the instructions.
	 *
	 * @param input The parsed input data consisting of instructions and node connections.
	 * @return The number of steps (Int) taken until all nodes being navigated end with 'Z'.
	 */
	override fun solvePart2(input: Pair<List<Char>, Map<String, Pair<String, String>>>): Any {
		val (instructions, nodeMap) = input
		val startingNodes = nodeMap.keys.filter { it.endsWith("A") }.toMutableSet()
		var currentNodes = startingNodes
		var steps = 0
		var instructionIndex = 0

		while (!currentNodes.all { it.endsWith("Z") }) {
			currentNodes = currentNodes.map { node ->
				val (left, right) = nodeMap[node] ?: error("Node $node not found in map")
				if (instructions[instructionIndex] == 'L') left else right
			}.toMutableSet()
			instructionIndex = (instructionIndex + 1) % instructions.size
			steps++
		}

		return steps
	}
}
