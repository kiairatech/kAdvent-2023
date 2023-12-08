package com.tyluur.day4

import java.io.File

class Day4(private val filePath: String) {

	private val scratchcards: List<Scratchcard> by lazy { parseScratchcards() }

	private fun parseScratchcards(): List<Scratchcard> {
		return File(filePath).readLines().map { line ->
			// Splitting the line into parts and then extracting winning and owned numbers
			val parts = line.split(": ", " | ")
			val winningNumbers = parts[1].split(" ").filter { it.isNotEmpty() }.map(String::toInt).toSet()
			val ownedNumbers = parts[2].split(" ").filter { it.isNotEmpty() }.map(String::toInt).toSet()
			Scratchcard(winningNumbers, ownedNumbers)
		}
	}

	fun solvePart1(): Int {
		return scratchcards.sumOf { it.calculatePoints() }
	}

	fun solvePart2(): Int {
		val counts = MutableList(scratchcards.size) { 1 }
		for ((index, card) in scratchcards.withIndex()) {
			repeat(card.winningNumbersCount) { offset ->
				if (index + offset + 1 < scratchcards.size) {
					counts[index + offset + 1] += counts[index]
				}
			}
		}
		return counts.sum()
	}

	data class Scratchcard(val winningNumbers: Set<Int>, val ownedNumbers: Set<Int>) {
		val winningNumbersCount: Int = winningNumbers.intersect(ownedNumbers).size

		fun calculatePoints(): Int {
			return 1.shl(winningNumbersCount).coerceAtLeast(1) - 1
		}
	}
}

fun main() {
	val solver = Day4("src/main/resources/day-4-input.txt") // Replace with the actual file path
	println("Total points (Part 1): ${solver.solvePart1()}")
	println("Total scratchcards won (Part 2): ${solver.solvePart2()}")
}
