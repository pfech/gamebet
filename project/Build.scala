import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "gamebet"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "be.objectify" %% "deadbolt-java" % "2.1-RC2",
    "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
  )

  val main = play.Project(appName, appVersion, appDependencies)
  .settings(
      resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
      resolvers += Resolver.url("Objectify Play Snapshot Repository", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns)
      )
  .settings(
    // Add your own project settings here   
    //templatesImport += Seq("play.i18n._")
  )

}
