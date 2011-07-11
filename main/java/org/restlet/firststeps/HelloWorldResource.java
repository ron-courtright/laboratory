package org.restlet.firststeps;

import org.restlet.resource.Get;

/**
 * User: ron
 * Date: 7/2/11
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldResource {

    @Get
    public String represent() {
        return "hello, world";
    }

}
