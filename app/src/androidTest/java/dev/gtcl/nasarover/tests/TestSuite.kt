package dev.gtcl.nasarover.tests

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    DetailsFragmentTest::class,
    ListFragmentTest::class,
    NavigationTest::class
)
class TestSuite