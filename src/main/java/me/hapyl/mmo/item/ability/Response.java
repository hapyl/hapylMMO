package me.hapyl.mmo.item.ability;

import javax.annotation.Nonnull;

public class Response {

    private final Status status;
    private final String message;

    public static final Response OK = new Response(Status.OK, "");
    public static final Response AWAIT = new Response(Status.AWAIT, "");

    private Response(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static Response error(@Nonnull String message) {
        return new Response(Status.ERROR, message);
    }

    @Nonnull
    public String getMessage() {
        return message;
    }

    public boolean isOk() {
        return status == Status.OK;
    }

    public boolean isError() {
        return status == Status.ERROR;
    }

    public boolean isAwait() {
        return status == Status.AWAIT;
    }

    public enum Status {
        OK,
        ERROR,
        AWAIT
    }

}
