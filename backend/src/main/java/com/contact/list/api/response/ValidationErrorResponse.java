package com.contact.list.api.response;

public record ValidationErrorResponse(String property, String object, String message) {
}
