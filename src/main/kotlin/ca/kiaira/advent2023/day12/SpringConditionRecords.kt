package ca.kiaira.advent2023.day12

/**
 * Represents a collection of spring condition records and damaged group information.
 * This class is designed to encapsulate and manage data pertaining to a set of springs,
 * specifically their operational conditions and any groupings of damage that may exist.
 *
 * @property springConditions A list of [SpringCondition] objects. Each [SpringCondition]
 *                            represents the condition of a spring in a specific row. This list
 *                            serves as a comprehensive record of the operational status of each
 *                            spring, where each spring's condition is detailed individually.
 *
 * @property damagedGroups A list of [DamagedGroup] objects. Each [DamagedGroup] represents a
 *                         group of springs that have been collectively identified as damaged.
 *                         This list enables the tracking and management of springs that are
 *                         part of the same damage event or category, thus facilitating targeted
 *                         repair or inspection efforts.
 *
 * The combination of these two properties provides a complete overview of the spring conditions,
 * enabling efficient monitoring and maintenance of the spring system.
 *
 * Example Usage:
 * ```
 * val springConditionList = listOf(SpringCondition(...), SpringCondition(...))
 * val damagedGroupList = listOf(DamagedGroup(...), DamagedGroup(...))
 * val springConditionRecords = SpringConditionRecords(springConditionList, damagedGroupList)
 * ```
 *
 * This class can be utilized in scenarios such as predictive maintenance systems, where
 * understanding both individual spring conditions and grouped damage patterns is crucial for
 * optimal operation and maintenance planning.
 */
class SpringConditionRecords(
	val springConditions: List<SpringCondition>,
	val damagedGroups: List<DamagedGroup>
)