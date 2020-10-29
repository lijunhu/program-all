package program.tiger.sword.enums;

import lombok.Getter;

/**
 * @author lijh
 */
@Getter
public enum HttpContentType {
    /**
     * application/Json
     */
    applicationJson("application/Json");

    private String contentType;

    HttpContentType(String contentType) {
        this.contentType = contentType;
    }


}
