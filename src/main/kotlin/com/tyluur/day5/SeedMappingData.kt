package com.tyluur.day5

/**
 * Holds the parsed data for the Day 5 puzzle.
 *
 * This data class encapsulates the initial seeds and the list of seed mappings.
 *
 * @property seeds The list of initial seed numbers to be transformed.
 * @property mappings The list of [SeedMapping] objects representing the series of transformations.
 *
 * @author Tyluur
 * @since December 10th, 2023
 */
data class SeedMappingData(
	val seeds: List<Long>,
	val mappings: List<SeedMapping>
)
