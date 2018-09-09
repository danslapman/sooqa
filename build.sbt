lazy val sooqa = (project in file("core"))
  .settings(Settings.common)
  .settings(
    name := "sooqa",
    parallelExecution in ThisBuild := false,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )

lazy val sooqaRef = LocalProject("sooqa")

lazy val `sooqa-plaintext` = (project in file("plaintext"))
  .settings(Settings.common)
  .dependsOn(sooqaRef)
  .settings(
    name := "sooqa-plaintext",
    parallelExecution in ThisBuild := false,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )

lazy val root = (project in file("."))
  .dependsOn(sooqa, `sooqa-plaintext`)
  .aggregate(sooqa, `sooqa-plaintext`)
  .settings(
    publish := {},
    bintrayRelease := {},
    bintrayUnpublish := {}
  )