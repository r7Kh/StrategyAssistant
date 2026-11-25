package com.strategy.assistant.data.ai

import org.junit.Assert.assertTrue
import org.junit.Test

class CocAttackPlanValidatorTest {
    @Test fun flagsMissingArmyAndNonNumericQuantities() {
        val warnings = CocAttackPlanValidator.validate("""{"army":[{"unit":"Archer","quantity":"many","confidence":"high"}]}""")
        assertTrue(warnings.any { it.contains("not a simple whole number") })
    }

    @Test fun flagsDuplicateUnitsAndUnsupportedLabels() {
        val warnings = CocAttackPlanValidator.validate("""{"army":[{"unit":"Archer","quantity":"10","confidence":"certain"},{"unit":"archer","quantity":"5","confidence":"high"}],"evaluation":{"army_fit":"excellent"}}""")
        assertTrue(warnings.any { it.contains("Duplicate army entry") })
        assertTrue(warnings.any { it.contains("unsupported qualitative label") })
    }

    @Test fun flagsExcessiveAlternativesAndPhases() {
        val warnings = CocAttackPlanValidator.validate("""{"army":[],"alternatives":[{},{},{}],"phases":[{},{},{},{},{},{},{},{},{}]}""")
        assertTrue(warnings.any { it.contains("More than two alternatives") })
        assertTrue(warnings.any { it.contains("unusually many deployment phases") })
    }

    @Test fun doesNotInventWarningsForAWellFormedPlan() {
        val warnings = CocAttackPlanValidator.validate("""{"army":[{"unit":"Archer","quantity":"10","confidence":"high"}],"entry":{"confidence":"medium"},"evaluation":{"resource_fit":"strong","army_fit":"moderate","entry_safety":"unknown","training_efficiency":"weak"},"alternatives":[]}""")
        assertTrue(warnings.isEmpty())
    }
}
