package org.example;

import org.example.config.AppConfig;
import org.example.models.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Communication communication = context.getBean(Communication.class);
        System.out.println(communication.getAllUsers());
        communication.saveUser(new User((long) 3, "James", "Brown", (byte)10));
        communication.updateUser(new User((long) 3, "Thomas", "Shelby", (byte)10));
        communication.deleteUserById(3);
        System.out.println(communication.getRes());
    }
}
