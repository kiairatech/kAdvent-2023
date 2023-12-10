package com.tyluur.day10

import com.tyluur.Puzzle

/**
 * Solves the "Day 10: Pipe Maze" puzzle from Advent of Code.
 *
 * This object inherits from the Puzzle class, providing implementations specific
 * to solving both parts of the Day 10 puzzle. It uses the PipeMazeSolver class
 * to analyze a maze of pipes and find solutions based on the maze's structure.
 *
 * @author Tyluur <contact@tyluur.com>
 * @since December 9th, 2023
 */
object Day10 : Puzzle<PipeMazeSolver>(10) {

	/**
	 * Parses the input for the Day 10 puzzle into a PipeMazeSolver.
	 *
	 * Converts the input sequence of strings into a list and then creates a
	 * PipeMazeSolver instance from this list.
	 *
	 * @param input A sequence of strings representing the puzzle input.
	 * @return An instance of PipeMazeSolver based on the provided input.
	 */
	override fun parse(input: Sequence<String>): PipeMazeSolver {
		return PipeMazeSolver.fromInput(input.toList())
	}

	/**
	 * Solves Part 1 of the Day 10 puzzle.
	 *
	 * Calculates the number of steps required to complete the loop in the pipe maze.
	 *
	 * @param input The parsed PipeMazeSolver instance representing the puzzle input.
	 * @return The number of steps to complete the loop in the pipe maze.
	 */
	override fun solvePart1(input: PipeMazeSolver): Int {
		return input.countSteps()
	}

	/**
	 * Solves Part 2 of the Day 10 puzzle.
	 *
	 * Determines the number of tiles that are enclosed within the main loop of the pipe maze.
	 *
	 * @param input The parsed PipeMazeSolver instance representing the puzzle input.
	 * @return The number of tiles enclosed within the loop of the pipe maze.
	 */
	override fun solvePart2(input: PipeMazeSolver): Int {
		return input.countEnclosed()
	}
}
