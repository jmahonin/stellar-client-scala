name := "steller-client-scala"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
	"org.bouncycastle" % "bcprov-jdk15on" % "1.51", // Bouncy Castle crypto provider
	"com.lambdaworks" % "scrypt" % "1.4.0", // SCrypt implementation
	"org.abstractj.kalium" % "kalium" % "0.2.1", // NaCL port for Ed25519 sigs, no AES-GCM yet
	"commons-codec" % "commons-codec" % "1.9", // For Base64
	"net.databinder.dispatch" %% "dispatch-core" % "0.11.1", // For HTTP request to Stellar
	"ch.qos.logback" % "logback-classic" % "1.1.2" // Logging
)
