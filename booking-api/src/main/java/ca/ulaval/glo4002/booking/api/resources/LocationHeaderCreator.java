package ca.ulaval.glo4002.booking.api.resources;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class LocationHeaderCreator {

    public static URI createURI(UriInfo uriInfo, String id) throws URISyntaxException {
        UriBuilder builder = uriInfo.getRequestUriBuilder().path(id);
        String uri = uriInfo.getBaseUri().relativize(builder.build()).toString();
        return new URI(String.format("/%s", uri));
    }
}
