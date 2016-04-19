package dk.csc

import spock.lang.Specification
import spock.lang.Subject

/**
 * @author Rune Molin, rmo@nine.dk
 */
class HelloTest extends Specification {


    def "Servicen kan kaldes med en mock"() {
        given:
        HelloServiceInterface mockService = Mock(HelloServiceInterface)

        @Subject ExampleBusinessLogic exampleBusinessLogic = new ExampleBusinessLogic(mockService)

        when:
        String s = exampleBusinessLogic.helloWorld()

        then:
        1 * mockService.getHello(_) >> { String name ->
            """
                {
                  "greeting": "Hello",
                  "name": "${name}"
                }
            """
        }

        s == "Hello World"
    }

}
