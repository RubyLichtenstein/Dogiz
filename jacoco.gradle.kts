
tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

private val classDirectoriesTree = fileTree("${project.buildDir}") {
    include(
        "**/classes/**/main/**",
        "**/intermediates/classes/debug/**",
        "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
        "**/tmp/kotlin-classes/debug/**"
    )
    exclude(
        "**/R.class",
        "**/R\$*.class",
        "**/*\$1*",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/models/**",
        "**/*\$Lambda$*.*",
        "**/*\$inlined$*.*"
    )
}

private val sourceDirectoriesTree = files("$projectDir/src/main/java")

private val executionDataTree = fileTree("${project.buildDir}") {
    include(
        "outputs/code_coverage/**/*.ec",
        "jacoco/jacocoTestReportDebug.exec",
        "jacoco/testDebugUnitTest.exec",
        "jacoco/test.exec"
    )
}

fun JacocoReportsContainer.reports() {
    html.required.set(true)
    xml.required.set(true)
    csv.required.set(false)
//    csv.isEnabled = false
//    xml.apply {
//        isEnabled = true
//        destination = file("$buildDir/reports/code-coverage/xml")
//    }
//    html.apply {
//        isEnabled = true
//        destination = file("$buildDir/reports/code-coverage/html")
//    }
}

fun JacocoReport.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}

fun JacocoCoverageVerification.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}

val jacocoGroup = "verification"
tasks.register<JacocoReport>("jacocoTestReport") {
    group = jacocoGroup
    description = "Code coverage report for both Android and Unit tests."
    dependsOn("testDebugUnitTest")
    reports {
        reports()
    }
    setDirectories()
}

//val minimumCoverage = "0.8".toBigDecimal()
//tasks.register<JacocoCoverageVerification>("jacocoCoverageVerification") {
//    group = jacocoGroup
//    description = "Code coverage verification for Android both Android and Unit tests."
//    dependsOn("testDebugUnitTest")
//    violationRules {
//        rule {
//            limit {
//                minimum = minimumCoverage
//            }
//        }
//        rule {
//            element = "CLASS"
//            excludes = listOf(
//                "**.FactorFacade.Builder",
//                "**.ServiceFacade.Builder",
//                "**.ChallengeFacade.Builder",
//                "**.Task"
//            )
//            limit {
//                minimum = minimumCoverage
//            }
//        }
//    }
//    setDirectories()
//}
