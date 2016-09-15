import com.typesafe.sbt.web.Import.WebKeys._
import com.typesafe.sbt.web.Import.{WebKeys, _}
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import org.scalajs.sbtplugin.cross.CrossType
import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayScala
import play.sbt.routes.RoutesKeys._
import playscalajs.PlayScalaJS.autoImport._
import playscalajs.ScalaJSPlay
import sbt.Keys._
import sbt._

object BuildAll extends Build
{

  lazy val commonSettings = Seq(
    organization := "com.pharmpress",
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.11.8",
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature"),
    resolvers += Resolver.sonatypeRepo("snapshots")
  )

  import Dependencies._

  lazy val crossType = CrossType.Full

  lazy val root = project.in(file("."))
    .aggregate(client, server)
    .settings(
      name := "todomvc",
      run in Compile <<= (run in Compile in server)
    ).settings(commonSettings)

  lazy val e2e = (project in file("e2e"))
  .settings(commonSettings)
  .settings(Seq(
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-xml" % "1.0.5",
      "org.scalamock" %% "scalamock-scalatest-support" % "3.1.1",
      "org.seleniumhq.selenium" % "selenium-java" % "2.35.0" % "test"
    )
  ))

  lazy val server = todomvc.jvm
    .enablePlugins(PlayScala)
    .settings(commonSettings)
    .settings(
      name := "todomvc-server",
      testOptions in Test += {
        Tests.Argument("-oFD", "-u", ((target in Test).value / "test-reports").getCanonicalPath)
      },
      pipelineStages := Seq(scalaJSProd),
      scalaJSProjects := Seq(todomvc.js),
      stage <<= stage dependsOn(WebKeys.assets, fullOptJS in(client, Compile)),
      routesGenerator := InjectedRoutesGenerator,
      libraryDependencies ++= Seq(
        prickle.jvm,
        scalaJs.stubs,
        js.jquery,
        js.angular,
        "org.cvogt" %% "play-json-extensions" % "0.8.0",
        apache.commonsLang3
      ) ++ db.elastic4s
    )

  lazy val client = todomvc.js
    .enablePlugins(ScalaJSPlay)
    .settings(commonSettings)
    .settings(
      name := "todomvc-client",
      libraryDependencies ++= Seq(
        scalaJs.angular,
        scalaJs.jquery,
        prickle.js),
      jsDependencies ++= Seq(
        js.jquery / "dist/jquery.js" minified "dist/jquery.min.js",
        js.angular / "angular.js" minified "angular.min.js" dependsOn "dist/jquery.js",
        RuntimeDOM),
      persistLauncher := true,
      relativeSourceMaps := true,
      skip in packageJSDependencies := false,
      jsEnv in Test := PhantomJSEnv(args = Seq("--web-security=no")).value)

  lazy val todomvc = (crossProject in file("."))
    .settings(
      name := "todomvc-common",
      unmanagedSourceDirectories in Compile :=
        Seq((scalaSource in Compile).value) ++
          crossType.sharedSrcDir(baseDirectory.value, "main"),
      unmanagedSourceDirectories in Test :=
        Seq((scalaSource in Test).value) ++
          crossType.sharedSrcDir(baseDirectory.value, "test"),
      testOptions in Test := Seq(Tests.Filter(_.endsWith("Test"))))

}