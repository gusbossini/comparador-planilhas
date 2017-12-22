package br.com.comparadorplanilhas;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ComparadorPlanilhasApplication {

    private int maxUploadSizeInMb = 10 * 1024 * 1024;

    public static void main(String[] args) {
        SpringApplication.run(ComparadorPlanilhasApplication.class, args);
    }

    //Tomcat large file upload connection reset
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 significa ilimitado
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });

        return tomcat;

    }
}
