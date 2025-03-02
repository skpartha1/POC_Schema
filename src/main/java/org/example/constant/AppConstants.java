package org.example.constant;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConstants {

    // TODO : make it bean
    public static ObjectMapper om = new ObjectMapper();
}
