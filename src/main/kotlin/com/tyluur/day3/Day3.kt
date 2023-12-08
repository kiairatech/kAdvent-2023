package com.tyluur.day3

import com.tyluur.Puzzle

/**
 * Day 3 puzzle solution.
 */
object Day3 : Puzzle<Array<IntArray>>(3) {
	override fun parse(input: Sequence<String>): Array<IntArray> {
		return input.map { line ->
			line.toCharArray().map { char ->
				when (char) {
					'.' -> 0 // Use 0 to represent empty space
					'*' -> -1 // Use -1 to represent symbols (adjust as needed)
					else -> char.toString().toIntOrNull() ?: 0 // Use integer values for numbers
				}
			}.toIntArray()
		}.toList().toTypedArray()
	}

	override fun solvePart1(input: Array<IntArray>): Int {
		var sum = 0

		// Iterate over the 2D array and calculate the sum of adjacent numbers
		for (y in input.indices) {
			for (x in input[y].indices) {
				if (input[y][x] != -1 && isAdjacentToSymbol(input, x, y)) {
					sum += input[y][x]
				}
			}
		}

		return sum
	}

	override fun solvePart2(input: Array<IntArray>): Int {
		var gearSum = 0

		// Iterate over the 2D array and find and calculate the gear ratios
		for (y in input.indices) {
			for (x in input[y].indices) {
				if (input[y][x] == -1 && isGear(input, x, y)) {
					gearSum += calculateGearRatio(input, x, y)
				}
			}
		}

		return gearSum
	}

	private fun isAdjacentToSymbol(input: Array<IntArray>, x: Int, y: Int): Boolean {
		val adjacentPositions = listOf(
			x - 1 to y, x + 1 to y, // left and right
			x to y - 1, x to y + 1, // up and down
			x - 1 to y - 1, x + 1 to y + 1, // diagonal
			x - 1 to y + 1, x + 1 to y - 1
		)

		return adjacentPositions.any { (ax, ay) ->
			ay >= 0 && ay < input.size && ax >= 0 && ax < input[ay].size && input[ay][ax] != 0
		}
	}

	private fun isGear(input: Array<IntArray>, x: Int, y: Int): Boolean {
		val adjacentPositions = listOf(
			x - 1 to y, x + 1 to y, // left and right
			x to y - 1, x to y + 1, // up and down
			x - 1 to y - 1, x + 1 to y + 1, // diagonal
			x - 1 to y + 1, x + 1 to y - 1
		)

		val adjacentNumbers = adjacentPositions.count { (ax, ay) ->
			ay >= 0 && ay < input.size && ax >= 0 && ax < input[ay].size && input[ay][ax] > 0
		}

		return adjacentNumbers == 2
	}

	private fun calculateGearRatio(input: Array<IntArray>, x: Int, y: Int): Int {
		val adjacentPositions = listOf(
			x - 1 to y, x + 1 to y, // left and right
			x to y - 1, x to y + 1, // up and down
			x - 1 to y - 1, x + 1 to y + 1, // diagonal
			x - 1 to y + 1, x + 1 to y - 1
		)

		val partNumbers = adjacentPositions.mapNotNull { (ax, ay) ->
			if (ay >= 0 && ay < input.size && ax >= 0 && ax < input[ay].size) {
				input[ay][ax]
			} else {
				0
			}
		}

		return partNumbers.reduce(Int::times)
	}
}
