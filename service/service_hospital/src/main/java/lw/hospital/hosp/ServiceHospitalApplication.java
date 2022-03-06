package lw.hospital.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "lw.hospital")
@EnableFeignClients(basePackages = "lw.hospital")
public class ServiceHospitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospitalApplication.class,args);
    }
}
