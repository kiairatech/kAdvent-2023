package com.tyluur.day3

import com.tyluur.Puzzle

/**
 * Day3 is an implementation of the Puzzle class for Advent of Code 2023, Day 3.
 * It solves two parts of the puzzle:
 * - Part 1: Calculate the sum of all part numbers adjacent to certain symbols in an engine schematic.
 * - Part 2: Calculate the sum of gear ratios, where a gear is represented by a '*' symbol adjacent to two part numbers.
 *
 * @constructor Initializes the Day3 puzzle with the corresponding day number.
 *
 * @author Tyluur
 * @since December 8th, 2023
 */
object Day3 : Puzzle<List<String>>(3) {

	/**
	 * Parses the input data into a list of strings.
	 *
	 * @param input Input data as a sequence of strings.
	 * @return A list of strings representing the engine schematic.
	 */
	override fun parse(input: Sequence<String>): List<String> {
		return input.toList()
	}

	/**
	 * Calculates the sum of part numbers adjacent to certain symbols in the engine schematic.
	 *
	 * @param input The engine schematic represented as a list of strings.
	 * @return The sum of part numbers.
	 */
	override fun solvePart1(input: List<String>): Long {
		// Implementation of part 1 logic
		val numbers = input.flatMapIndexed { i, s -> Regex("\\d+").findAll(s).map { i to it.range } }
		return numbers.sumOf { (i, range) ->
			if (getAdjacentSymbol(input, i, range) != null) input[i].substring(range).toLong() else 0
		}
	}

	/**
	 * Calculates the sum of gear ratios in the engine schematic.
	 *
	 * @param input The engine schematic represented as a list of strings.
	 * @return The sum of gear ratios.
	 */
	override fun solvePart2(input: List<String>): Long {
		// Implementation of part 2 logic
		val numbers = input.flatMapIndexed { i, s -> Regex("\\d+").findAll(s).map { i to it.range } }
		var sum = 0L
		for ((i, r1) in numbers) {
			for ((j, r2) in numbers) {
				val (x1, y1) = getAdjacentSymbol(input, i, r1) ?: continue
				val (x2, y2) = getAdjacentSymbol(input, j, r2) ?: continue
				if ((i != j || r1 != r2) && x1 == x2 && y1 == y2 && input[x1][y1] == '*')
					sum += input[i].substring(r1).toLong() * input[j].substring(r2).toLong()
			}
		}
		return sum / 2
	}

	/**
	 * Helper function to find an adjacent symbol in the engine schematic.
	 *
	 * @param input The engine schematic represented as a list of strings.
	 * @param i The current row index.
	 * @param range The range of characters to search for adjacent symbols.
	 * @return The coordinates (row, column) of the adjacent symbol, or null if not found.
	 */
	private fun getAdjacentSymbol(input: List<String>, i: Int, range: IntRange): Pair<Int, Int>? {
		listOf(i - 1, i + 1).forEach { r ->
			for (j in maxOf(range.first - 1, 0)..minOf(range.last + 1, input[i].lastIndex))
				if (r in input.indices && input[r][j] in "@#$%&*/=+-")
					return r to j
		}
		listOf(range.first - 1, range.last + 1).forEach { c ->
			if (c in input[i].indices && input[i][c] in "@#$%&*/=+-")
				return i to c
		}
		return null
	}
}
