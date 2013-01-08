package com.sko.resource.util;

import net.vz.mongodb.jackson.DBCursor;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class ResourceHelper {

    public static void notFoundIfNull(DBCursor<?> cursor) {
        if (!cursor.hasNext()) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
    }

    public static void notFoundIfNull(Object obj) {
        errorIfNull(obj, Status.NOT_FOUND);
    }

    public static void errorIfNull(Object obj, Status status) {
        if (obj == null) {
            throw new WebApplicationException(status);
        }
    }
}
