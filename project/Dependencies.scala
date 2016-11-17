import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
import sbt._

object Dependencies
{
  object apache {
    val commonsLang3="com.hynnet" % "commons-lang3" % "3.4"
  }

  object scalaJs
  {
    def stubs = "org.scala-js" %% "scalajs-stubs" % scalaJSVersion

    def angular = "com.greencatsoft" %%%! "scalajs-angular" % "0.7"

    def jquery = "be.doeraene" %%%! "scalajs-jquery" % "0.9.0"
  }

  object js
  {
    def angular = "org.webjars.bower" % "angular" % "1.5.7"

    def jquery = "org.webjars.bower" % "jquery" % "2.2.4" force()
  }

  object prickle
  {
    val version = "1.1.11"

    def js = "com.github.benhutchison" %%%! "prickle" % version

    def jvm = "com.github.benhutchison" %% "prickle" % version
  }

  object db
  {
    val elastic4s = Seq(
      "com.sksamuel.elastic4s" %% "elastic4s-core" % "1.7.5",
      "com.sksamuel.elastic4s" %% "elastic4s-streams" % "1.7.5"
    )

//    val elasticsearch = "org.elasticsearch" % "elasticsearch" % "1.7.5"

  }

}