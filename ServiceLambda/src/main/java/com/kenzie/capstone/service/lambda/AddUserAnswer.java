package com.kenzie.capstone.service.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddUserAnswer implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {}

    static final Logger log = LogManager.getLogger();


}
