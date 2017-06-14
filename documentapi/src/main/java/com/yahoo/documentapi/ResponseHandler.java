// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.documentapi;

/**
 * @author <a href="mailto:simon@yahoo-inc.com">Simon Thoresen</a>
 */
public interface ResponseHandler {

    /**
     * This method is called once for each document api operation invoked on a {@link AsyncSession}. There is no
     * guarantee as to which thread calls this, so any implementation must be thread-safe.
     *
     * @param response The response to process.
     */
    public void handleResponse(Response response);
}
