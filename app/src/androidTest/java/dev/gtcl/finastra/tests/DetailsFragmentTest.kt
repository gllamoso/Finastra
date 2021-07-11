package dev.gtcl.finastra.tests

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import dev.gtcl.finastra.R
import dev.gtcl.finastra.model.Camera
import dev.gtcl.finastra.model.Photo
import dev.gtcl.finastra.model.Rover
import dev.gtcl.finastra.view.DetailsFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class DetailsFragmentTest {

    @Test
    fun verifyPhotoDetails(){
        val id = 123
        val sol = 1
        val cameraName = "camera name"
        val cameraFullName = "camera fullname"
        val roverName = "rover name"
        val roverLandingDate = "01-01-01"
        val roverLaunchDate = "01-01-01"
        val roverStatus = "status"

        val imgSrc = "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/00002/opgs/edr/ncam/NLA_397671934EDR_F0010008AUT_04096M_.JPG"
        val earthDate = "01-01-03"

        val camera = Camera(
            cameraName,
            cameraFullName
        )
        val rover = Rover(
            roverName,
            roverLandingDate,
            roverLaunchDate,
            roverStatus
        )
        val photo =  Photo(
            id,
            sol,
            camera,
            imgSrc,
            rover,
            earthDate
        )

        launchFragmentInContainer<DetailsFragment>(
            bundleOf("photo" to photo)
        )

        onView(withId(R.id.id_value)).check(matches(withText("$id")))
        onView(withId(R.id.sol_value)).check(matches(withText("$sol")))
        onView(withId(R.id.camera_value)).check(matches(withText(cameraFullName)))
        onView(withId(R.id.image_source_value)).check(matches(withText(imgSrc)))
        onView(withId(R.id.earth_date_value)).check(matches(withText(earthDate)))
        onView(withId(R.id.rover_value)).check(matches(withText(roverName)))
        onView(withId(R.id.rover_landing_date_value)).check(matches(withText(roverLandingDate)))
        onView(withId(R.id.rover_launch_date_value)).check(matches(withText(roverLaunchDate)))
        onView(withId(R.id.rover_status_value)).check(matches(withText(roverStatus)))
    }
}