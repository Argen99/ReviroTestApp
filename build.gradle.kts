// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.agp.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.agp.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.navigation.safeArgs) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}
true // Needed to make the Suppress annotation work for the plugins block