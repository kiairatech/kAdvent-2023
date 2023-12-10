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
		return input.sumOf { calculateValue(it, true) }
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
		return input.sumOf { calculateValue(it, false) }
	}

	private fun calculateValue(history: List<Int>, calculateNext: Boolean): Int {
		return calculate(history, mutableListOf(history.toMutableList()), calculateNext)
	}

	private tailrec fun calculate(
		history: List<Int>,
		sequences: MutableList<MutableList<Int>>,
		calculateNext: Boolean
	): Int {
		if (sequences.last().all { it == 0 }) {
			for (i in sequences.size - 2 downTo 0) {
				val nextValue = if (calculateNext) {
					sequences[i].last() + sequences[i + 1].last()
				} else {
					sequences[i].first() - sequences[i + 1].first()
				}
				if (calculateNext) sequences[i].add(nextValue) else sequences[i].add(0, nextValue)
			}
			return if (calculateNext) sequences[0].last() else sequences[0].first()
		} else {
			val newSequence = (0 until sequences.last().size - 1).map { i ->
				sequences.last()[i + 1] - sequences.last()[i]
			}.toMutableList()
			sequences.add(newSequence)
			return calculate(history, sequences, calculateNext)
		}
	}
}
