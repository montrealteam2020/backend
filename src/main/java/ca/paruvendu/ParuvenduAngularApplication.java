package ca.paruvendu;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import ca.paruvendu.config.SecurityUtility;
import ca.paruvendu.domain.User;
import ca.paruvendu.domain.security.Role;
import ca.paruvendu.domain.security.UserRole;
import ca.paruvendu.service.UserService;

@SpringBootApplication
public class ParuvenduAngularApplication extends SpringBootServletInitializer    {

//	implements CommandLineRunner

	
	@Autowired 
	private UserService userService;
	
	
	public static void main(String[] args) {
		System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
		SpringApplication.run(ParuvenduAngularApplication.class, args);
	}

	

	
	
//	@Override 
//	public void run(String... args){
		
//		User user1 = new User();
//		user1.setFirstName("John");
//		user1.setLastName("Adams");
//		user1.setUsername("john");
//		user1.setPassword(SecurityUtility.passwordEncoder().encode("p12345p"));
//		user1.setEmail("test@gmail.com");
//		Set<UserRole> userRoles = new HashSet<>();
//		Role role1 = new Role();
//		role1.setRoleId(1);
//		role1.setName("ROLE_USER");
//		userRoles.add(new UserRole(user1, role1));
//		
//		userService.createUser(user1, userRoles);
//		
//		userRoles.clear();
//		
//		User user2 = new User();
//		user2.setFirstName("Admin");
//		user2.setLastName("Admin");
//		user2.setUsername("admin");
//		user2.setPassword(SecurityUtility.passwordEncoder().encode("p"));
//		user2.setEmail("Admin@gmail.com");
//		Role role2 = new Role();
//		role2.setRoleId(0);
//		role2.setName("ROLE_ADMIN");
//		userRoles.add(new UserRole(user2, role2));
//		
//		userService.createUser(user2, userRoles);
		
//	}
}
