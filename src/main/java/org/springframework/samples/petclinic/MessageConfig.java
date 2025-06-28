package org.springframework.samples.petclinic;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.messageresolver.IMessageResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Factory
public class MessageConfig {

    @Singleton
    public IMessageResolver messageResolver() {
        return new IMessageResolver() {
            private final Properties messages = loadMessages();

            @Override
            public String resolveMessage(ITemplateContext context, Class<?> origin, String key, Object[] parameters) {
                System.out.println("Resolving message for key: " + key);
                
                // Handle locale-specific keys (e.g., welcome_en_GB -> welcome)
                String baseKey = key;
                if (key.contains("_")) {
                    baseKey = key.substring(0, key.indexOf("_"));
                    System.out.println("Extracted base key: " + baseKey + " from: " + key);
                }
                
                String message = messages.getProperty(baseKey);
                if (message != null) {
                    System.out.println("Found message for key: " + baseKey + " = " + message);
                    return message;
                }
                
                System.out.println("No message found for key: " + baseKey);
                return "??" + key + "??";
            }

            @Override
            public String createAbsentMessageRepresentation(ITemplateContext context, Class<?> origin, String key, Object[] parameters) {
                return "??" + key + "??";
            }

            @Override
            public Integer getOrder() {
                return 1;
            }

            @Override
            public String getName() {
                return "PetClinicMessageResolver";
            }

            private Properties loadMessages() {
                Properties props = new Properties();
                try (InputStream is = getClass().getClassLoader().getResourceAsStream("messages/messages.properties")) {
                    if (is != null) {
                        props.load(is);
                        System.out.println("Loaded base messages: " + props.size() + " entries");
                        System.out.println("Available keys: " + props.keySet());
                        
                        // Test the welcome key specifically
                        String welcome = props.getProperty("welcome");
                        System.out.println("Welcome message: " + welcome);
                    } else {
                        System.out.println("Failed to load base messages");
                    }
                } catch (IOException e) {
                    System.err.println("Error loading messages: " + e.getMessage());
                }
                return props;
            }
        };
    }
} 