package com.tyluur.day9

import com.tyluur.Puzzle
import com.tyluur.day9.Day9.number

/**
 * Object representing the solution for Day 9 of the Advent of Code.
 *
 * It extends the Puzzle class and provides specific implementations for parsing and solving
 * the puzzle for Day 9, which involves extrapolating values from historical data.
 *
 * @property number The day number of the puzzle, which is 9.
 *
 * @author Tyluur <contact@tyluur.com>
 * @since December 9th, 2023
 */
object Day9 : Puzzle<List<List<Int>>>(9) {

	/**
	 * Parses the input data from a sequence of strings into a list of lists of integers.
	 *
	 * Each line of the input is split into a list of integers.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return The parsed input data as a list of lists of integers.
	 */
	override fun parse(input: Sequence<String>): List<List<Int>> {
		return input.map { line ->
			line.split(" ").map { it.toInt() }
		}.toList()
	}

	/**
	 * Solves Part 1 of the Day 9 puzzle.
	 *
	 * This involves summing the next extrapolated value for each series in the input.
	 *
	 * @param input The parsed input data.
	 * @return The solution to Part 1 of the puzzle.
	 */
	override fun solvePart1(input: List<List<Int>>): Int {
		return input.sumOf { calculateNextValue(it) }
	}

	/**
	 * Solves Part 2 of the Day 9 puzzle.
	 *
	 * This involves summing the previous extrapolated value for each series in the input.
	 *
	 * @param input The parsed input data.
	 * @return The solution to Part 2 of the puzzle.
	 */
	override fun solvePart2(input: List<List<Int>>): Int {
		return input.sumOf { calculatePreviousValue(it) }
	}

	/**
	 * Calculates the next value in a series based on the difference method.
	 *
	 * It generates sequences of differences until all differences are zero,
	 * then extrapolates the next value.
	 *
	 * @param history The series of historical values.
	 * @return The next extrapolated value.
	 */
	private fun calculateNextValue(history: List<Int>): Int {
		val sequences = mutableListOf(history.toMutableList())

		// Generate sequences of differences
		while (true) {
			val newSequence = (0 until sequences.last().size - 1).map { i ->
				sequences.last()[i + 1] - sequences.last()[i]
			}.toMutableList()

			sequences.add(newSequence)

			// Break if all differences are zero
			if (newSequence.all { it == 0 }) break
		}

		// Extrapolate the next value
		for (i in sequences.size - 2 downTo 0) {
			sequences[i].add(sequences[i].last() + sequences[i + 1].last())
		}

		// The next value is the last element of the first sequence
		return sequences[0].last()
	}

	/**
	 * Calculates the previous value in a series based on the difference method.
	 *
	 * Similar to calculateNextValue, but extrapolates backwards.
	 *
	 * @param history The series of historical values.
	 * @return The previous extrapolated value.
	 */
	private fun calculatePreviousValue(history: List<Int>): Int {
		val sequences = mutableListOf(history.toMutableList())

		// Generate sequences of differences
		while (true) {
			val newSequence = (0 until sequences.last().size - 1).map { i ->
				sequences.last()[i + 1] - sequences.last()[i]
			}.toMutableList()

			sequences.add(newSequence)

			// Break if all differences are zero
			if (newSequence.all { it == 0 }) break
		}

		// Extrapolate the previous value
		for (i in sequences.size - 2 downTo 0) {
			sequences[i].add(0, sequences[i].first() - sequences[i + 1].first())
		}

		// The previous value is the first element of the first sequence
		return sequences[0].first()
	}
}
