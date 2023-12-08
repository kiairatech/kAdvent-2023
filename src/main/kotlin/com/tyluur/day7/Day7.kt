package com.tyluur.day7

import com.tyluur.Puzzle

object Day7 : Puzzle<List<Hand>>(7) {
	override fun parse(input: Sequence<String>): List<Hand> {
		return input.map { Hand.parse(it) }.toList()
	}

	override fun solvePart1(input: List<Hand>): Long {
		val sortedHands = input.sortedWith(HandComparator(wildcard = false))
		return calculateWinnings(sortedHands)
	}

	override fun solvePart2(input: List<Hand>): Long {
		val sortedHands = input.sortedWith(HandComparator(wildcard = true))
		return calculateWinnings(sortedHands)
	}

	private fun calculateWinnings(sortedHands: List<Hand>): Long {
		var totalWinnings = 0L
		val rankMap = mutableMapOf<Hand, Int>()

		for ((index, hand) in sortedHands.withIndex()) {
			val rank = rankMap[hand] ?: (index + 1)
			rankMap[hand] = rank
			totalWinnings += rank.toLong() * hand.bid
		}

		return totalWinnings
	}
}

data class Hand(val cards: List<Card>, val bid: Int) {
	val type = getType(cards, wildcard = false)
	val wildcardType = getType(cards, wildcard = true)

	init {
		require(cards.size == 5)
	}

	companion object {
		fun parse(s: String): Hand {
			val (cards, bid) = s.split(' ', limit = 2)
			return Hand(cards.map { Card.parse(it) }, bid.toInt())
		}

		private fun getType(cards: List<Card>, wildcard: Boolean): HandType {
			val bag = mutableMapOf<Card, Int>()
			for (card in cards) {
				bag[card] = bag.getOrDefault(card, 0) + 1
			}

			val jokers = if (wildcard) {
				bag.remove(Card.JACK) ?: 0
			} else {
				0
			}

			return getBestType(bag.values.toList(), jokers)
		}

		private fun getBestType(counts: List<Int>, jokers: Int): HandType {
			if (jokers == 0) {
				return getType(counts)
			}

			// try joker as new card type
			var best = getBestType(counts.plus(1), jokers - 1)

			// try joker as every existing card type
			for (i in counts.indices) {
				val copy = counts.toMutableList()
				copy[i]++

				val type = getBestType(copy, jokers - 1)
				if (type < best) {
					best = type
				}
			}

			return best
		}

		private fun getType(counts: List<Int>): HandType {
			require(counts.sum() == 5)
			require(counts.size in 1..5)

			return when (counts.size) {
				1 -> HandType.FIVE_OF_A_KIND
				2 -> if (4 in counts) HandType.FOUR_OF_A_KIND else HandType.FULL_HOUSE
				3 -> if (3 in counts) HandType.THREE_OF_A_KIND else HandType.TWO_PAIR
				4 -> HandType.ONE_PAIR
				else -> HandType.HIGH_CARD
			}
		}
	}
}

class HandComparator(private val wildcard: Boolean) : Comparator<Hand> {
	override fun compare(o1: Hand, o2: Hand): Int {
		if (wildcard) {
			val cmp = o1.wildcardType.compareTo(o2.wildcardType)
			if (cmp != 0) {
				return cmp
			}
		} else {
			val cmp = o1.type.compareTo(o2.type)
			if (cmp != 0) {
				return cmp
			}
		}

		for ((i, card) in o1.cards.withIndex()) {
			val otherCard = o2.cards[i]

			if (wildcard) {
				if (card == Card.JACK && otherCard != Card.JACK) {
					return 1
				} else if (card != Card.JACK && otherCard == Card.JACK) {
					return -1
				}
			}

			val cmp = card.compareTo(otherCard)
			if (cmp != 0) {
				return cmp
			}
		}

		return 0
	}
}

enum class HandType {
	FIVE_OF_A_KIND,
	FOUR_OF_A_KIND,
	FULL_HOUSE,
	THREE_OF_A_KIND,
	TWO_PAIR,
	ONE_PAIR,
	HIGH_CARD,
}

enum class Card {
	ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO;

	companion object {
		fun parse(c: Char): Card {
			return when (c) {
				'A' -> ACE
				'K' -> KING
				'Q' -> QUEEN
				'J' -> JACK
				'T' -> TEN
				'9' -> NINE
				'8' -> EIGHT
				'7' -> SEVEN
				'6' -> SIX
				'5' -> FIVE
				'4' -> FOUR
				'3' -> THREE
				'2' -> TWO
				else -> throw IllegalArgumentException()
			}
		}
	}
}