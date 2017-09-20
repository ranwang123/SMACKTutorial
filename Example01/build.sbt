name := "SMACK01"
version := "1.0"
scalaVersion := "2.11.1"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
