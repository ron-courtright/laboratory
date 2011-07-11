package org.restlet.firststeps;

import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * User: ron
 * Date: 7/2/11
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class FirstServerResource extends ServerResource {

    public static void main(String[] args) throws Exception {
        // Create the HTTP server and listen on port 8182
        new Server(Protocol.HTTP, 8182, FirstServerResource.class).start();
    }

    @Get
    public String toString() {
        return "hello, world";
    }

}
