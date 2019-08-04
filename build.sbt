name := "hg-rs-server"
organization := "com.github.alexsniffintest"
version := "2.0-M1"
scalaVersion := "2.12.8"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case "reference.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}