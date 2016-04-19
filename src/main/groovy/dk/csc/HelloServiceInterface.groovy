package dk.csc

/**
 * @author Rune Molin, rmo@nine.dk
 */
interface HelloServiceInterface {
    /**
     *
     * @param name Navn der skal sendes en hilsen til
     *
     * @return denne json:
     * {
     *     "greeting: "Hello",
     *     "name: "<name>"
     * }
     */

    String getHello(String name)
}