package com.tyluur.day7

import com.tyluur.Puzzle

/**
 * Solution for Day 7 of the Advent of Code challenge.
 * This class extends the Puzzle class and is specialized in handling poker hands.
 *
 * @author Tyluur
 * @since December 8th, 2023
 */
object Day7 : Puzzle<List<Day7.Hand>>(7) {

	/**
	 * Parses the input into a list of Hand objects.
	 * Each line of the input represents a hand of poker cards with a bid.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return A list of Hand objects.
	 */
	override fun parse(input: Sequence<String>): List<Hand> {
		return input.map { line ->
			val (cards, bid) = line.split(" ")
			Hand(cards, bid.toInt(), false)
		}.toList()
	}

	/**
	 * Solves Part 1 of the Day 7 puzzle.
	 * Calculates the total score of the hands without adjusting for part 2 rules.
	 *
	 * @param input The parsed input data as a list of Hand objects.
	 * @return The total score as a Long.
	 */
	override fun solvePart1(input: List<Hand>): Long {
		return calculateTotalScore(input)
	}

	/**
	 * Solves Part 2 of the Day 7 puzzle.
	 * Adjusts the hands for part 2 rules and then calculates the total score.
	 *
	 * @param input The parsed input data as a list of Hand objects.
	 * @return The total score as a Long.
	 */
	override fun solvePart2(input: List<Hand>): Long {
		val adjustedInput = input.map { it.copy(adjustForPart2 = true) }
		return calculateTotalScore(adjustedInput)
	}

	/**
	 * Calculates the total score of a list of hands.
	 * Sorts the hands based on their value and multiplies each hand's bid by its position.
	 *
	 * @param hands The list of Hand objects to calculate the score for.
	 * @return The total score as a Long.
	 */
	private fun calculateTotalScore(hands: List<Hand>): Long {
		val sortedHands = hands.sortedWith(HandComparator)
		return sortedHands.foldIndexed(0L) { index, acc, hand ->
			acc + (index + 1) * hand.bid
		}
	}

	/**
	 * Data class representing a poker hand.
	 * Contains information about the hand's cards, bid, and if it should adjust for Part 2 rules.
	 *
	 * @property cards String representation of the hand's cards.
	 * @property bid The bid associated with the hand.
	 * @property adjustForPart2 Flag to indicate if the hand should be adjusted for Part 2 rules.
	 */
	data class Hand(val cards: String, val bid: Int, val adjustForPart2: Boolean) {
		val totalValue: String by lazy {
			var cardsValue = cards.map {
				if (adjustForPart2) weights2.indexOf(it).toString(16)
				else weights.indexOf(it).toString(16)
			}.joinToString("")

			if (adjustForPart2 && cards.contains('J')) {
				val maxTypeValue = replacements.map { r ->
					getTypeValue(cards.replace('J', r))
				}.maxOrNull() ?: throw IllegalStateException("Max type value not found")
				return@lazy maxTypeValue.toString() + cardsValue
			}
			getTypeValue(cards).toString() + cardsValue
		}

		companion object {
			private val typeValue = mapOf(
				"5" to 7,
				"41" to 6,
				"32" to 5,
				"311" to 4,
				"221" to 3,
				"2111" to 2,
				"11111" to 1
			)
			private const val weights = "23456789TJQKA"
			private const val weights2 = "J23456789TQKA"
			private const val replacements = "23456789TQKA"

			fun getTypeValue(cards: String): Int {
				val countMap = cards.groupingBy { it }.eachCount()
				val sortedCounts = countMap.values.sortedDescending()
				val key = sortedCounts.joinToString("")
				return typeValue[key] ?: throw IllegalArgumentException("Invalid hand type")
			}
		}
	}

	/**
	 * Comparator for Hand objects.
	 * Compares two Hand objects based on their total value.
	 */
	object HandComparator : Comparator<Hand> {
		override fun compare(o1: Hand, o2: Hand): Int {
			return o1.totalValue.compareTo(o2.totalValue)
		}
	}
}
