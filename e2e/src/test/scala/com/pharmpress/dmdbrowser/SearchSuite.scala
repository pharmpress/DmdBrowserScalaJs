package com.pharmpress.dmdbrowser

import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.scalatest.selenium.WebBrowser
import org.scalatest.{FlatSpec, ShouldMatchers}

class SearchSuite extends FlatSpec with ShouldMatchers with WebBrowser {

  implicit val webDriver: WebDriver = new HtmlUnitDriver

  val host = "http://localhost:9000/"

  "The search page" should "display results" in {
    go to (host + "search")
    textField("new-query").value = "Aspirin"
    click on "clear-completed"
    val t = find("search-results").get.text
    t should include("Results")
  }
}
