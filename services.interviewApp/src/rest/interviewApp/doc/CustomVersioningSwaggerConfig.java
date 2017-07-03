package rest.interviewApp.doc;

import libraries.RESTDocs.SwaggerConfig;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class CustomVersioningSwaggerConfig extends SwaggerConfig {
    public CustomVersioningSwaggerConfig() {
        super("api");
    }

    @Bean
    public Docket apiV1(){
        return createDocketForApiVersion("1");
    }

//    @Bean
//    public Docket apiV2(){
//        return createDocketForApiVersion("2");
//    }
//
//    @Bean
//    public Docket apiV3(){
//        return createDocketForApiVersion("3");
//    }
//
//    @Bean
//    public Docket apiV4(){
//        return createDocketForApiVersion("4");
//    }
}
