package com.tyluur.day6

import com.tyluur.Puzzle
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

/**
 * Object representing the solution for Day 6 of Advent of Code.
 *
 * This object extends the Puzzle class and provides specific implementations for parsing the input
 * and solving the two parts of the puzzle. The input data for this puzzle consists of race times
 * and record distances, represented as pairs of Long values.
 *
 * @author Tyluur
 * @since December 11th, 2023
 */
object Day6 : Puzzle<List<Pair<Long, Long>>>(6) {

	/**
	 * Parses the input data for the puzzle.
	 *
	 * The input is a sequence of strings, where the first line contains race times and the second line
	 * contains record distances. This method splits these lines, converts them to long values, and pairs
	 * each race time with its corresponding record distance.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return A list of pairs, where each pair contains a race time and a record distance.
	 */
	override fun parse(input: Sequence<String>): List<Pair<Long, Long>> {
		val lines = input.toList() // Convert sequence to list
		val raceTimes = lines[0].split("\\s+".toRegex()).drop(1).map { it.toLong() }
		val records = lines[1].split("\\s+".toRegex()).drop(1).map { it.toLong() }
		return raceTimes.zip(records)
	}

	/**
	 * Solves Part 1 of the puzzle.
	 *
	 * This method calculates the number of ways to beat the record for each race using the quadratic formula.
	 * It iterates through each race, calculates potential roots for beating the record, and multiplies these
	 * possibilities together to find the total number of ways.
	 *
	 * @param input The parsed input data, a list of pairs of race times and record distances.
	 * @return The total number of ways to beat the records for all races.
	 */
	override fun solvePart1(input: List<Pair<Long, Long>>): Long {
		return input.fold(1L) { acc, (time, record) ->
			val discriminantSqrt = sqrt(time.toDouble() * time - 4.0 * record)
			var possibleRoot1 = floor((time + discriminantSqrt) / 2).toLong()
			var possibleRoot2 = ceil((time - discriminantSqrt) / 2).toLong()
			if (possibleRoot1 * (time - possibleRoot1) <= record) possibleRoot1--
			if (possibleRoot2 * (time - possibleRoot2) <= record) possibleRoot2++
			acc * (possibleRoot1 - possibleRoot2 + 1)
		}
	}

	/**
	 * Solves Part 2 of the puzzle.
	 *
	 * This method combines all race times and record distances into a single long race and a single record
	 * and then calculates the number of ways to beat this combined record, similar to the approach in Part 1.
	 *
	 * @param input The parsed input data, a list of pairs of race times and record distances.
	 * @return The number of ways to beat the record in the combined long race.
	 */
	override fun solvePart2(input: List<Pair<Long, Long>>): Long {
		val combinedRaceTime = input.map { it.first }.joinToString("").toLong()
		val combinedRecord = input.map { it.second }.joinToString("").toLong()

		val discriminantSqrt = sqrt(combinedRaceTime.toDouble() * combinedRaceTime - 4.0 * combinedRecord)
		var possibleRoot1 = floor((combinedRaceTime + discriminantSqrt) / 2).toLong()
		var possibleRoot2 = ceil((combinedRaceTime - discriminantSqrt) / 2).toLong()
		if (possibleRoot1 * (combinedRaceTime - possibleRoot1) <= combinedRecord) possibleRoot1--
		if (possibleRoot2 * (combinedRaceTime - possibleRoot2) <= combinedRecord) possibleRoot2++
		return possibleRoot1 - possibleRoot2 + 1
	}
}
