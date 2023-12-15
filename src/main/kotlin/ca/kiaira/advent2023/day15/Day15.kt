package ca.kiaira.advent2023.day15

import ca.kiaira.advent2023.Puzzle

class Day15Puzzle : Puzzle<List<String>>(15) {

	override fun parse(input: Sequence<String>): List<String> {
		// Joining the sequence into a single string, then splitting by comma and filtering out any empty strings
		return input.joinToString(separator = "").split(',').filter { it.isNotEmpty() }
	}

	override fun solvePart1(input: List<String>): Any {
		// Sum of HASH algorithm results for each step
		return input.sumOf { hashAlgorithm(it) }
	}

	private fun hashAlgorithm(inputString: String): Int {
		var currentValue = 0
		for (char in inputString) {
			val asciiCode = char.code
			currentValue += asciiCode
			currentValue *= 17
			currentValue %= 256
		}
		return currentValue
	}

}

fun main() {
	val puzzle = Day15Puzzle()
	puzzle.solve()
}
