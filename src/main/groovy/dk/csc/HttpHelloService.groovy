package dk.csc

/**
 * Implementering af HelloServiceInterface som kalder en URL med HTTP GET
 *
 * @author Rune Molin, rmo@nine.dk
 */
class HttpHelloService implements HelloServiceInterface {
    private final String urlStr

    HttpHelloService(String urlStr) {
        this.urlStr = urlStr
    }

    /**
     * HTTP GET
     * @param name
     * @return
     */
    String getHello(String name) {
        return new URL(urlStr+"?name=${name}").getText(connectTimeOut: 5000)
    }
}
