package ca.kiaira.advent2023.day14

import ca.kiaira.advent2023.Puzzle

/**
 * Represents the Day 14 Puzzle of Advent of Code 2023.
 *
 * This puzzle involves simulating the movement of rocks ('O') in a grid and calculating loads on support beams.
 * It consists of two parts: Part 1 and Part 2.
 *
 * @constructor Creates a Day14Puzzle instance with the puzzle day number (14).
 *
 * @author Muthigani Kiaira <mukiaira@gmail.com>
 * @since December 12th, 2023
 */
object Day14 : Puzzle<Array<Array<Char>>>(14) {

	/**
	 * Parses the input into a 2D grid of characters.
	 *
	 * @param input The input data as a sequence of strings.
	 * @return A 2D grid represented as an array of arrays of characters.
	 */
	override fun parse(input: Sequence<String>): Array<Array<Char>> {
		return input.map { it.toCharArray().toTypedArray() }.toList().toTypedArray()
	}

	/**
	 * Solves Part 1 of the puzzle.
	 *
	 * This method simulates the movement of rocks in four directions (North, South, West, East) and calculates the
	 * loads on support beams after each step.
	 *
	 * @param input The initial 2D grid of characters.
	 * @return The total load on the support beams after simulating the movement.
	 */
	override fun solvePart1(input: Array<Array<Char>>): Any {
		val numRows = input.size
		val numCols = input.first().size
		val loads = Array(numRows) { IntArray(numCols) }

		for (row in 0 until numRows) {
			for (col in 0 until numCols) {
				if (input[row][col] == 'O') {
					var targetRow = row
					while (targetRow > 0 && input[targetRow - 1][col] == '.') {
						targetRow--
					}
					input[targetRow][col] = 'O'
					if (targetRow != row) input[row][col] = '.'
					loads[targetRow][col] = numRows - targetRow
				}
			}
		}

		return loads.sumOf { it.sum() }
	}

	/**
	 * Solves Part 2 of the puzzle.
	 *
	 * This method simulates the movement of rocks for a large number of cycles and detects repeating patterns in the
	 * grid. It then calculates the load on the north support beams in the final state.
	 *
	 * @param input The initial 2D grid of characters.
	 * @return The total load on the north support beams in the final state.
	 */
	override fun solvePart2(input: Array<Array<Char>>): Any {
		val finalState = findRepeatingPattern(input, 1_000_000_000)
		return northLoad(finalState)
	}

	/**
	 * Moves the rocks north within the grid.
	 *
	 * This function simulates the movement of rocks in a northward direction, updating the grid accordingly.
	 *
	 * @param data The input 2D grid of characters.
	 * @return A new 2D grid with the rocks moved north.
	 */
	private fun tiltNorth(data: Array<Array<Char>>): Array<Array<Char>> {
		val newData = data.map { it.clone() }.toTypedArray()
		for (y in newData.indices) {
			for (x in newData[0].indices) {
				if (newData[y][x] == 'O') {
					val newY = (0 until y).reversed().takeWhile { newData[it][x] == '.' }.lastOrNull()
					if (newY != null) {
						newData[newY][x] = 'O'
						newData[y][x] = '.'
					}
				}
			}
		}
		return newData
	}

	/**
	 * Moves the rocks south within the grid.
	 *
	 * This function simulates the movement of rocks in a southward direction, updating the grid accordingly.
	 *
	 * @param data The input 2D grid of characters.
	 * @return A new 2D grid with the rocks moved south.
	 */
	private fun tiltSouth(data: Array<Array<Char>>): Array<Array<Char>> = reverse(tiltNorth(reverse(data)))

	/**
	 * Moves the rocks west within the grid.
	 *
	 * This function simulates the movement of rocks in a westward direction, updating the grid accordingly.
	 *
	 * @param data The input 2D grid of characters.
	 * @return A new 2D grid with the rocks moved west.
	 */
	private fun tiltWest(data: Array<Array<Char>>): Array<Array<Char>> = transpose(tiltNorth(transpose(data)))

	/**
	 * Moves the rocks east within the grid.
	 *
	 * This function simulates the movement of rocks in an eastward direction, updating the grid accordingly.
	 *
	 * @param data The input 2D grid of characters.
	 * @return A new 2D grid with the rocks moved east.
	 */
	private fun tiltEast(data: Array<Array<Char>>): Array<Array<Char>> =
		transpose(reverse(tiltNorth(reverse(transpose(data)))))

	/**
	 * Transposes the given 2D grid.
	 *
	 * This function swaps the rows and columns of the input grid.
	 *
	 * @param data The input 2D grid of characters.
	 * @return The transposed 2D grid.
	 */
	private fun transpose(data: Array<Array<Char>>): Array<Array<Char>> {
		return Array(data[0].size) { x -> Array(data.size) { y -> data[y][x] } }
	}

	/**
	 * Reverses the given 2D grid.
	 *
	 * This function reverses the rows of the input grid.
	 *
	 * @param data The input 2D grid of characters.
	 * @return The reversed 2D grid.
	 */
	private fun reverse(data: Array<Array<Char>>): Array<Array<Char>> = data.reversedArray()

	/**
	 * Performs one cycle of tilting in all directions.
	 *
	 * This function simulates one cycle of rock movement in all four directions (North, South, West, East).
	 *
	 * @param data The input 2D grid of characters.
	 * @return The grid after one cycle of tilting.
	 */
	private fun cycle(data: Array<Array<Char>>): Array<Array<Char>> = tiltEast(tiltSouth(tiltWest(tiltNorth(data))))

	/**
	 * Calculates the load on the north support beams.
	 *
	 * This function computes the total load on the north support beams based on the position of rocks ('O') in the grid.
	 *
	 * @param data The 2D grid of characters.
	 * @return The total load on the north support beams.
	 */
	private fun northLoad(data: Array<Array<Char>>): Int {
		return data.indices.sumOf { y ->
			data[y].indices.sumOf { x ->
				if (data[y][x] == 'O') data.size - y else 0
			}
		}
	}

	/**
	 * Finds and returns a repeating pattern in the grid.
	 *
	 * This function simulates the movement of rocks for a large number of cycles and detects repeating patterns in the
	 * grid. It returns the grid state at the specified repetition index.
	 *
	 * @param data The initial 2D grid of characters.
	 * @param repetitionIndex The index at which the repeating pattern is to be found.
	 * @return The grid state at the specified repetition index.
	 */
	private fun findRepeatingPattern(data: Array<Array<Char>>, repetitionIndex: Long): Array<Array<Char>> {
		val sequence = mutableListOf(data)
		var cycleBegin = 0L
		var currentGrid = data

		while (true) {
			currentGrid = cycle(currentGrid)
			val index = sequence.indexOfFirst { it.contentDeepEquals(currentGrid) }
			if (index != -1) {
				cycleBegin = index.toLong()
				break
			}
			sequence.add(currentGrid)
		}

		return if (repetitionIndex < cycleBegin) sequence[repetitionIndex.toInt()] else sequence[(cycleBegin + ((repetitionIndex - cycleBegin) % (sequence.size - cycleBegin))).toInt()]
	}

}