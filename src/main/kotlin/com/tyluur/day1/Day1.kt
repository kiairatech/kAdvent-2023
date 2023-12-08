package com.tyluur.day1

import com.github.michaelbull.logging.InlineLogger
import java.io.File

/**
 * The first day solutions for advent of code 2023
 *
 * @author Tyluur <contact@tyluur.com>
 * @since December 7th, 2023
 */
class Day1 {

	/**
	 * Mapping of spelled-out numbers to their respective digits
	 */
	private val numberMapping = mapOf(
		"one" to "1",
		"two" to "2",
		"three" to "3",
		"four" to "4",
		"five" to "5",
		"six" to "6",
		"seven" to "7",
		"eight" to "8",
		"nine" to "9"
	)

	/**
	 * Replace spelled out numbers with their digit equivalent
	 */
	private fun replaceSpelledOutNumbers(line: String): String {
		var replacedLine = line
		numberMapping.forEach { (word, digit) ->
			replacedLine = replacedLine.replace(word, digit)
		}
		return replacedLine
	}

	/**
	 * Calculate the calibration value of a line
	 */
	private fun calculateCalibrationValue(line: String): Int {
		val replacedLine = replaceSpelledOutNumbers(line)
		val digits = replacedLine.filter { it.isDigit() }

		val calibrationValue = when {
			digits.length >= 2 -> {
				// Combine first and last digit to form a two-digit number
				"${digits.first()}${digits.last()}".toInt()
			}

			digits.length == 1 -> {
				// Duplicate the single digit to form a two-digit number
				"${digits.first()}${digits.first()}".toInt()
			}

			else -> {
				0
			}
		}
		logger.info { "Original line: '$line', Replaced line: '$replacedLine', Calibration value: $calibrationValue" }

		return calibrationValue
	}

	/**
	 * Calculate the total calibration value from a file
	 */
	fun sumCalibrationValuesFromFile(filePath: String): Int {
		var totalSum = 0
		File(filePath).forEachLine {
			val calculateCalibrationValue = calculateCalibrationValue(it)
//			logger.info { "$it [totalSum= $calculateCalibrationValue]" }
			totalSum += calculateCalibrationValue
		}
		return totalSum
	}

}

/**
 * The main method of this class
 */
fun main() {
	val filePath = "src/main/resources/day-1-input.txt"
	val totalCalibrationValue = Day1().sumCalibrationValuesFromFile(filePath)
	logger.info { "Total Calibration Value: $totalCalibrationValue" }
}

/**
 * The instance of this logger
 */
private val logger = InlineLogger()
