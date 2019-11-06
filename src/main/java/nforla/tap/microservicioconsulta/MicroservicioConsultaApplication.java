package nforla.tap.microservicioconsulta;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class MicroservicioConsultaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioConsultaApplication.class, args);
	}

	@Bean
	public OkHttpClient okHttpClient(){
		return new OkHttpClient();
	 }

	 @Bean
	public ObjectMapper objectMapper(){
		return new ObjectMapper();
	 }
}
