package ca.kiaira.advent2023.day4

import ca.kiaira.advent2023.Puzzle
import kotlin.math.pow

/**
 * Day 4 puzzle implementation.
 *
 * @author Muthigani Kiaira
 * @since December 10th, 2023
 */
object Day4 : Puzzle<List<String>>(4) {

	/**
	 * Parses the input data into a list of strings.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return A list of strings.
	 */
	override fun parse(input: Sequence<String>): List<String> {
		return input.toList()
	}

	/**
	 * Solves part 1 of the puzzle.
	 *
	 * @param input The input data as a list of strings.
	 * @return The solution for part 1 as an integer.
	 */
	override fun solvePart1(input: List<String>): Any {
		var result = 0
		val instances = IntArray(input.size) { 1 }

		for (i in input.indices) {
			val (winningNumbersLine, myNumbersLine) = parseLines(input[i])

			val winningNumbers = findWinningNumbers(winningNumbersLine)
			val myNumbers = myNumbersLine.split("\\s+".toRegex())

			val count = countMatchingNumbers(winningNumbers, myNumbers)

			updateInstances(i, count, instances)
			if (count != 0) {
				result += 2.0.pow((count - 1).toDouble()).toInt()
			}
		}

		return result
	}

	/**
	 * Solves part 2 of the puzzle.
	 *
	 * @param input The input data as a list of strings.
	 * @return The solution for part 2 as an integer.
	 */
	override fun solvePart2(input: List<String>): Any {
		val instances = IntArray(input.size) { 1 }

		for (i in input.indices) {
			val (winningNumbersLine, myNumbersLine) = parseLines(input[i])

			val winningNumbers = findWinningNumbers(winningNumbersLine)
			val myNumbers = myNumbersLine.split("\\s+".toRegex())

			val count = countMatchingNumbers(winningNumbers, myNumbers)

			updateInstances(i, count, instances)
		}

		var result = 0
		for (instance in instances) {
			result += instance
		}

		return result
	}

	/**
	 * Parses a line into a pair of strings.
	 *
	 * @param line The input line to parse.
	 * @return A pair of strings.
	 */
	private fun parseLines(line: String): Pair<String, String> {
		val temp = line.split(":[\\s]+".toRegex())[1].split("[\\s]+\\|[\\s]+".toRegex())
		return Pair(temp[0], temp[1])
	}

	/**
	 * Counts the number of matching numbers between two lists of strings.
	 *
	 * @param winningNumbers The list of winning numbers.
	 * @param myNumbers The list of my numbers.
	 * @return The count of matching numbers as an integer.
	 */
	private fun countMatchingNumbers(winningNumbers: List<String>, myNumbers: List<String>): Int {
		var count = 0
		for (myNumber in myNumbers) {
			for (winningNumber in winningNumbers) {
				if (myNumber == winningNumber) {
					count++
					break
				}
			}
		}
		return count
	}

	/**
	 * Updates instances based on the index and count.
	 *
	 * @param index The index to start updating from.
	 * @param count The count of instances to update.
	 * @param instances The array of instances to update.
	 */
	private fun updateInstances(index: Int, count: Int, instances: IntArray) {
		for (j in 0 until count) {
			val cardToCopy = index + j + 1
			if (cardToCopy < instances.size) {
				instances[cardToCopy] += instances[index]
			}
		}
	}

	/**
	 * Splits a winning numbers line into a list of strings.
	 *
	 * @param winningNumbersLine The winning numbers line to split.
	 * @return A list of strings representing winning numbers.
	 */
	private fun findWinningNumbers(winningNumbersLine: String): List<String> {
		return winningNumbersLine.split("\\s+".toRegex())
	}
}
