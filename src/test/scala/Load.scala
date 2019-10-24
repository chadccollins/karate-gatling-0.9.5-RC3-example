package features.load

import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class LoadSimulation extends Simulation {

  // https://github.com/intuit/karate/tree/master/karate-gatling#karateprotocol
  // This allows Gatling to correctly aggregate the requests that Karate is making
  val protocol = karateProtocol(
    "/something/v1/{id}" -> Nil,
    "/something/v1/" -> pauseFor("get" -> 15, "post" -> 25)
  )

  // https://github.com/intuit/karate/tree/master/karate-gatling#nameresolver
  protocol.nameResolver = (req, ctx) => req.getHeader("karate-name")

  // Get environment variables for configuration of the load test
  val env: String = System.getProperty("env")
  val users = Integer.getInteger("users", 10).toInt
  val rampUp = Integer.getInteger("rampup", 1).toInt

  val fail = scenario("myScenario").exec(karateFeature("classpath:features/read-from-csv.feature"))
  val pass = scenario("myScenario").exec(karateFeature("classpath:features/hard-coded-value.feature"))

  if (env contains "fail") {
    setUp(
      fail.inject(rampUsers(users) during (rampUp seconds)).protocols(protocol)
    )
  }
  if (env contains "pass") {
    setUp(
      pass.inject(rampUsers(users) during (rampUp seconds)).protocols(protocol)
    )
  }
}