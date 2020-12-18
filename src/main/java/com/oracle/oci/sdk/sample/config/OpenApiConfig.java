package com.oracle.oci.sdk.sample.config;

/**
 * @author bnasslahsen
 */

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.stereotype.Component;

@OpenAPIDefinition(info = @Info(title = "SAMPLE SDK TEST APIs",
		description = "Agregated APIs ", version = "v1.0"))
@Component
public class OpenApiConfig {}