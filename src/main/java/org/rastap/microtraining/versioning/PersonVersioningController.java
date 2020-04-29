package org.rastap.microtraining.versioning;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller implemented to show different uses of versioning and how it call be called
 * <p>
 * There are 4 common options to accept versioning or content negotiation, the first 2 options are perfect for a browser call
 * the other two are useful for example to expose clean content services, for example in Postman, but a browser can't send
 * headers in its call, finally the API documentation will be more difficult to write for header or produces, because
 * it's hidden in the header
 * Both of them has better performance depending the needs of the exposure
 */
@RestController
public class PersonVersioningController {

    @GetMapping("/person/v1")
    public ResponseEntity<PersonV1> getPersonV1() {
        return ResponseEntity.ok(new PersonV1("Pepito Grillo"));
    }

    @GetMapping("/person/v2")
    public ResponseEntity<PersonV2> getPersonV2() {
        return ResponseEntity.ok(new PersonV2(new Name("Pepito", "Grillo")));
    }

    /**
     * Other version for verioning Oject person through params
     * /person/param?version=1
     *
     * @return person
     */
    @GetMapping(value = "/person/param", params = "version=1")
    public ResponseEntity<PersonV1> getPersonParamV1() {
        return ResponseEntity.ok(new PersonV1("Bub Espuma"));
    }

    /**
     * Other version for verioning Oject person
     * /person/param?version=2
     *
     * @return person
     */
    @GetMapping(value = "/person/param", params = "version=2")
    public ResponseEntity<PersonV2> getPersonParamV2() {
        return ResponseEntity.ok(new PersonV2(new Name("Bub", "Espuma")));
    }

    /**
     * Other version for verioning Oject person through header
     * /person/header
     * Headers option: X-API-VERSION=1
     *
     * @return person
     */
    @GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
    public ResponseEntity<PersonV1> getPersonHeaderV1() {
        return ResponseEntity.ok(new PersonV1("Bub Espuma"));
    }

    /**
     * Other version for verioning Oject person
     * /person/header
     * Headers option: X-API-VERSION=1
     *
     * @return person
     */
    @GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
    public ResponseEntity<PersonV2> getPersonHeaderV2() {
        return ResponseEntity.ok(new PersonV2(new Name("Bub", "Espuma")));
    }

    /**
     * Other version for versioning Object person through produces
     * /person/produces
     * In Headers: Accept - application/org.arst80.appv1+json
     *
     * @return person
     */
    @GetMapping(value = "/person/produces", produces = "application/org.arst80.appv1+json")
    public ResponseEntity<PersonV1> getPersonProducesV1() {
        return ResponseEntity.ok(new PersonV1("Bub Espuma"));
    }

    /**
     * Other version for versioning Object person
     * /person/produces
     * In Headers: Accpet - application/org.arst80.appv2+json
     *
     * @return person
     */
    @GetMapping(value = "/person/produces", produces = "application/org.arst80.appv2+json")
    public ResponseEntity<PersonV2> getPersonProducesV2() {
        return ResponseEntity.ok(new PersonV2(new Name("Bub", "Espuma")));
    }


}
