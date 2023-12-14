package ca.kiaira.advent2023.day14

import ca.kiaira.advent2023.Puzzle

class Day14ParabolicReflectorDish : Puzzle<Day14ParabolicReflectorDish.GridState>(14) {

	override fun parse(input: Sequence<String>): GridState {
		val lines = input.toList()
		val grid = lines.map { line ->
			line.map { char -> char == 'O' }.toBooleanArray()
		}

		return GridState(grid, lines.first().length, lines.size)
	}

	override fun solvePart1(input: GridState): Any {
		return input.calculateTotalLoad()
	}

	data class GridState(private val grid: List<BooleanArray>, val width: Int, val height: Int) {
		fun calculateTotalLoad(): Int {
			var totalLoad = 0
			for (y in 0 until height) {
				for (x in 0 until width) {
					if (grid[y][x]) {
						// The load is equal to the number of rows from the rock to the south edge of the platform
						totalLoad += height - y
					}
				}
			}
			return totalLoad
		}

	}
}

fun main() {
	val puzzle = Day14ParabolicReflectorDish()
	puzzle.solve()
}
