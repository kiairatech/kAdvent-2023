package com.tyluur.day10

/**
 * Class that solves the pipe maze puzzle.
 *
 * This class provides functionality to analyze a grid of characters representing a maze of pipes
 * and compute different aspects of the maze, such as the number of steps to complete a loop and
 * the number of enclosed areas within the maze.
 *
 * @property grid The grid representing the pipe maze, where each string is a row in the grid.
 */
class PipeMazeSolver(private val grid: List<String>) {

	/**
	 * Map defining the options for each type of pipe character.
	 */
	private val options = mapOf(
		'|' to listOf(true, false, true, false),
		'-' to listOf(false, true, false, true),
		'L' to listOf(true, true, false, false),
		'J' to listOf(true, false, false, true),
		'7' to listOf(false, false, true, true),
		'F' to listOf(false, true, true, false),
		'.' to listOf(false, false, false, false),
		'S' to listOf(true, true, true, true)
	)

	/**
	 * Calculates the number of steps required to complete the loop in the pipe maze.
	 *
	 * @return The number of steps to complete the loop in the pipe maze.
	 */
	fun countSteps(): Int {
		val startY = grid.indexOfFirst { it.contains('S') }
		val startX = grid[startY].indexOf('S')
		var x = startX
		var y = startY
		var steps = 0
		var lastD = -1
		while (true) {
			for (d in dirs.indices) {
				if (d == (lastD + 2) % 4) continue // No turning back
				val nx = x + dirs[d][0]
				val ny = y + dirs[d][1]
				if (!(nx in grid[0].indices && ny in grid.indices)) continue // Out of bounds
				if (options[grid[y][x]]!![d] && options[grid[ny][nx]]!![(d + 2) % 4]) {
					x = nx
					y = ny
					lastD = d
					steps++
					break
				}
			}
			if (x == startX && y == startY) break
		}
		return steps / 2
	}

	/**
	 * Determines the number of tiles that are enclosed within the main loop of the pipe maze.
	 *
	 * @return The number of tiles enclosed within the loop of the pipe maze.
	 */
	fun countEnclosed(): Int {
		val bigGrid = MutableList(grid.size * 2) { MutableList(grid[0].length * 2) { false } }
		var (x, y) = findStart()
		var lastD = -1L

		while (true) {
			for (d in dirs.indices) {
				if (d.toLong() == (lastD + 2) % 4) continue

				val nx = x + dirs[d][0]
				val ny = y + dirs[d][1]
				if (nx !in grid[0].indices || ny !in grid.indices) continue

				if (options[grid[y][x]]!![d] && options[grid[ny][nx]]!![(d + 2) % 4]) {
					bigGrid[y * 2][x * 2] = true
					bigGrid[y * 2 + dirs[d][1]][x * 2 + dirs[d][0]] = true
					x = nx
					y = ny
					lastD = d.toLong()
					break
				}
			}
			if (x == findStart().first && y == findStart().second) break
		}

		val cellStates = MutableList(bigGrid.size) { MutableList(bigGrid[0].size) { 0L } }
		var enclosedCount = 0
		for (originalY in grid.indices) {
			for (originalX in grid[0].indices) {
				val cellX = originalX * 2
				val cellY = originalY * 2

				if (!bigGrid[cellY][cellX]) {
					if (isEnclosed(cellX.toLong(), cellY.toLong(), bigGrid, cellStates)) {
						enclosedCount++
					}
				}
			}
		}

		return enclosedCount
	}

	/**
	 * Finds the starting position of 'S' in the grid.
	 *
	 * @return A Pair of integers representing the x and y coordinates of the start position.
	 */
	private fun findStart(): Pair<Int, Int> {
		val startY = grid.indexOfFirst { it.contains('S') }
		val startX = grid[startY].indexOf('S')
		return Pair(startX, startY)
	}

	/**
	 * Checks if the cell at the specified coordinates is enclosed within the maze.
	 *
	 * @param x The x-coordinate of the cell.
	 * @param y The y-coordinate of the cell.
	 * @param grid The grid representing the pipe maze.
	 * @param cellStates A mutable list representing the state of each cell in the grid.
	 * @return True if the cell is enclosed, False otherwise.
	 */
	private fun isEnclosed(x: Long, y: Long, grid: Grid, cellStates: CellStates): Boolean {
		// BFS
		var todo = mutableListOf(listOf(x, y))
		val visited = MutableList(grid.size) { MutableList(grid[0].size) { false } }
		visited[y.toInt()][x.toInt()] = true
		while (todo.isNotEmpty()) {
			val newTodo = mutableListOf<List<Long>>()
			for (pos in todo) {
				for (d in 0 until 4) {
					val nx = pos[0] + dirs[d][0]
					val ny = pos[1] + dirs[d][1]
					if (!(nx in 0 until grid[0].size && ny in grid.indices)) {
						for (i in visited.indices) {
							for (j in visited[0].indices) {
								if (visited[i][j]) cellStates[i][j] = -1
							}
						}
						return false // found a way to escape
					}

					if (cellStates[ny.toInt()][nx.toInt()] == 1L) return true
					else if (cellStates[ny.toInt()][nx.toInt()] == -1L) return false

					if (!visited[ny.toInt()][nx.toInt()] && !grid[ny.toInt()][nx.toInt()]) {
						newTodo.add(listOf(nx, ny))
						visited[ny.toInt()][nx.toInt()] = true
					}
				}
			}
			todo = newTodo
		}
		for (i in visited.indices) {
			for (j in visited[0].indices) {
				if (visited[i][j]) cellStates[i][j] = 1
			}
		}
		return true
	}

	companion object {

		/**
		 * Directions for navigation represented as North, East, South, and West.
		 */
		private val dirs = listOf(listOf(0, -1), listOf(1, 0), listOf(0, 1), listOf(-1, 0))

		/**
		 * Factory method to create an instance of PipeMazeSolver from input strings.
		 *
		 * @param input A list of strings representing the puzzle input.
		 * @return An instance of PipeMazeSolver based on the provided input.
		 */
		fun fromInput(input: List<String>): PipeMazeSolver = PipeMazeSolver(input)
	}
}

/**
 * Represents a grid where each cell is a Boolean value.
 * This grid is used to represent a larger version of the maze, where each cell indicates
 * whether it is part of the path (true) or not (false).
 */
typealias Grid = List<List<Boolean>>

/**
 * Represents the states of each cell in a grid.
 * This mutable grid stores state information for each cell, such as whether a cell is
 * unexplored (0), enclosed (1), or leads to an escape from the maze (-1).
 */
typealias CellStates = MutableList<MutableList<Long>>
