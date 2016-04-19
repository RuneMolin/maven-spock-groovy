package dk.csc

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
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

    def "Servicen kan kaldes over en lokal forbindelse"() {
        given:
        HelloServiceInterface httpService = new HttpHelloService("http://localhost:8000/test")

        // spin en lokal server op
        def server = HttpServer.create(new InetSocketAddress(8000), 0)

        server.createContext("/test", new HttpHandler() {
            void handle(HttpExchange httpExchange) throws IOException {
                def query = httpExchange.requestURI.query

                // 'foo=bar' => ['foo', 'bar']
                String[] params = query.split('=')

                String response = """
                {
                  "greeting": "Hello",
                  "name": "${params[1]}"
                }
                """
                httpExchange.sendResponseHeaders(200, response.length())

                OutputStream os = httpExchange.responseBody
                os.write(response.getBytes())
                os.close()
            }
        })

        server.executor = null
        server.start()

        @Subject ExampleBusinessLogic exampleBusinessLogic = new ExampleBusinessLogic(httpService)

        when:
        String s = exampleBusinessLogic.helloWorld()

        then:

        s == "Hello World"

        server.stop(0)
    }

}
