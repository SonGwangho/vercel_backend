package app.vercel.gwangho

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class GwanghoApplication

fun main(args: Array<String>) {
	runApplication<GwanghoApplication>(*args)
}
