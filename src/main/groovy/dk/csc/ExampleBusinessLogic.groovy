package dk.csc

import groovy.json.JsonSlurper

/**
 * Eksempel på noget forretningslogik, der benytter en ekstern service

 * @author Rune Molin, rmo@nine.dk
 */
class ExampleBusinessLogic {
    private final HelloServiceInterface serviceInterface

    ExampleBusinessLogic(HelloServiceInterface serviceInterface) {
        this.serviceInterface = serviceInterface
    }

    /**
     * @return "Hello World"
     */
    String helloWorld() {
        String json = serviceInterface.getHello("World")
        def data = new JsonSlurper().parseText(json)
        return "${data.greeting} ${data.name}"
    }
}
