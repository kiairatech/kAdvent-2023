package com.tyluur.day9

import com.tyluur.Puzzle

object Day9 : Puzzle<List<List<Int>>>(9) {

	override fun parse(input: Sequence<String>): List<List<Int>> {
		return input.map { line ->
			line.split(" ").map { it.toInt() }
		}.toList()
	}

	override fun solvePart1(input: List<List<Int>>): Int {
		return input.sumOf { calculateNextValue(it) }
	}

	override fun solvePart2(input: List<List<Int>>): Int {
		return input.sumOf { calculatePreviousValue(it) }
	}

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