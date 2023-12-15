package ca.kiaira.advent2023.day15

import ca.kiaira.advent2023.Puzzle
import java.util.*

/**
 * Day15 is an object representing the puzzle for Advent of Code 2023, Day 15.
 * It inherits from the [Puzzle] class with a generic type of [List]<[String]>.
 *
 * This object provides methods to parse the input, solve both parts of the puzzle,
 * and calculate hash values for certain strings.
 *
 * @author Muthigani Kiaira <mukiaira@gmail.com>
 * @since December 15th, 2023
 */
object Day15 : Puzzle<List<String>>(15) {

	/**
	 * Parses the input by joining lines, splitting by ',' and filtering out empty strings.
	 *
	 * @param input A sequence of strings representing the input data.
	 * @return A list of non-empty strings.
	 */
	override fun parse(input: Sequence<String>): List<String> {
		return input.joinToString(separator = "").split(',').filter { it.isNotEmpty() }
	}

	/**
	 * Solves Part 1 of the puzzle.
	 *
	 * @param input A list of input strings.
	 * @return The sum of hash values of the input strings.
	 */
	override fun solvePart1(input: List<String>): Any {
		val hashValues = input.map { calculateHash(it) }
		return hashValues.sum()
	}

	/**
	 * Solves Part 2 of the puzzle.
	 *
	 * @param input A list of input strings.
	 * @return The sum of focusing powers calculated from lenses in boxes.
	 */
	override fun solvePart2(input: List<String>): Any {
		val boxes = Array(256) { LinkedList<Lens>() }

		for (command in input) {
			if (command.endsWith('-')) {
				val labelToRemove = command.dropLast(1)
				val hashToRemove = calculateHash(labelToRemove)
				boxes[hashToRemove].removeIf { it.label == labelToRemove }
			} else {
				val (label, focalString) = command.split('=')
				val focalLength = focalString.toInt()
				val hash = calculateHash(label)
				val index = boxes[hash].indexOfFirst { it.label == label }
				if (index == -1) {
					boxes[hash].add(Lens(label, focalLength))
				} else {
					boxes[hash][index].focalLength = focalLength
				}
			}
		}

		val focusingPowers = boxes.flatMapIndexed { hash, box ->
			box.mapIndexed { index, lens -> (hash + 1) * (index + 1) * lens.focalLength }
		}

		return focusingPowers.sum()
	}

	/**
	 * Calculates the hash value of a string.
	 *
	 * @param str The input string.
	 * @return The hash value as an integer.
	 */
	private fun calculateHash(str: String): Int {
		var hash = 0

		for (char in str) {
			hash += char.toInt()
			hash *= 17
			hash %= 256
		}

		return hash
	}
}

/**
 * Entry point of the program. Calls the solve method of Day15.
 */
fun main() = Day15.solve()
