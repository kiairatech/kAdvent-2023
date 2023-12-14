package ca.kiaira.advent2023.day14

import ca.kiaira.advent2023.Puzzle

class Day14Puzzle : Puzzle<Array<Array<Char>>>(14) {

	override fun parse(input: Sequence<String>): Array<Array<Char>> {
		return input.map { it.toCharArray().toTypedArray() }.toList().toTypedArray()
	}

	override fun solvePart1(input: Array<Array<Char>>): Any {
		val rows = input.size
		val columns = input.first().size
		val loads = Array(rows) { IntArray(columns) }

		for (row in 0 until rows) {
			for (col in 0 until columns) {
				if (input[row][col] == 'O') {
					var targetRow = row
					while (targetRow > 0 && input[targetRow - 1][col] == '.') {
						targetRow--
					}
					input[targetRow][col] = 'O'
					if (targetRow != row) input[row][col] = '.'
					loads[targetRow][col] = rows - targetRow
				}
			}
		}

		return loads.sumOf { it.sum() }
	}
}

fun main() = Day14Puzzle().solve()