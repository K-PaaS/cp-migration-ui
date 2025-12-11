package org.container.platform.migration.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ContainerPlatformMigrationUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainerPlatformMigrationUiApplication.class, args);
    }

}
