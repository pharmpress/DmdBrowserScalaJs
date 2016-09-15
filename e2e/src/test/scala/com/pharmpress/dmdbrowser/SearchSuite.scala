package com.pharmpress.dmdbrowser

import java.net.URL

import org.openqa.selenium.Platform
import org.openqa.selenium.remote.{BrowserType, DesiredCapabilities, RemoteWebDriver}
import org.scalatest.selenium.WebBrowser
import org.scalatest.{FlatSpec, ShouldMatchers}

import scala.util.{Success, Try}

class SearchSuite extends FlatSpec with ShouldMatchers with WebBrowser {
  val USERNAME = System.getenv("SAUCELABS_USERNAME")
  val ACCESS_KEY = System.getenv("SAUCELABS_ACCESS_KEY")

  val capability = new DesiredCapabilities(BrowserType.CHROME, "", Platform.ANY){
    setCapability("platform", "Windows XP")
    setCapability("version", "43.0")
  }

  implicit val webDriver = new RemoteWebDriver(new URL("http://" + USERNAME + ":" + ACCESS_KEY + "@localhost:8000/wd/hub"), capability)


  val host = "http://10.11.1.168:9000/"

  def waitForElement(timeout: Int = 60, query: String): Boolean = {
    if(timeout >= 0){
      Try(find(cssSelector(query))) match {
        case Success(Some(e)) => true
        case _ =>
          Thread.sleep(1000)
          waitForElement(timeout - 1, query)
      }
    } else{
      throw new Exception("timout")
    }
  }

  "The search page" should "display results" in {
    go to (host + "search")
    textField("new-query").value = "Aspirin"
    click on "clear-completed"
    waitForElement(10, """p.ng-scope""")

    val results = findAll(xpath("""//p[@class="ng-scope"]"""))
    assert(results.length === 0)
  }
}
