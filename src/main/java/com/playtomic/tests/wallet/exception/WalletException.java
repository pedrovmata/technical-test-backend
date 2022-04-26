package com.playtomic.tests.wallet.exception;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.http.HttpStatus;


@Getter
public class WalletException extends RuntimeException {


     /**
     * Application error code.
     */
    private final int applicationCode;
    /**
     * Title of the exception.
     */
    private final String title;
    /**
     * Status assigned to the response.
     */
    private HttpStatus status;


    /**
     * Builds a com.playtomic.tests.wallet.model.WalletException with all the data received as parameters. It
     * assigns 404 status to the response by default.
     *
     * @param message         Error message
     * @param applicationCode Error code
     * @param title           Error title
     */
    public WalletException(final @NonNull String message, final int applicationCode, final String title) {
        super(message);

        this.applicationCode = applicationCode;
        this.status = HttpStatus.NOT_FOUND;
        this.title = title;
    }

    /**
     * Builds a WalletException with all the data received as parameters.
     *
     * @param message         Error message
     * @param applicationCode Error code
     * @param title           Error title
     * @param status          {@link HttpStatus} assigned to the response
     */
    public WalletException(final String message, final int applicationCode, final String title, final @NotNull HttpStatus status) {
        this(message, applicationCode, title);

        this.status = status;
    }
}
