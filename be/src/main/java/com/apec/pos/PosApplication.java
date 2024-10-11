package com.apec.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
public class PosApplication extends SpringBootServletInitializer {

    public static void main(String[] args)throws IOException {
        SpringApplication.run(PosApplication.class, args);

    }
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private AccountRepository accountRepository;
//    @PostConstruct
//    private void createRoleIfNotFound() {
//        List<RoleEntity> roleEntity = roleRepository.findAll();
//        if (roleEntity.size()==0) {
//            RoleEntity roleAdmin = new RoleEntity();
//            roleAdmin.setAuthority("ADMIN");
//            roleRepository.insert(roleAdmin);
//
//            RoleEntity roleUser = new RoleEntity();
//            roleUser.setAuthority("USER");
//            roleRepository.insert(roleUser);
//
//            AccountEntity adminEntity = new AccountEntity();
//            adminEntity.setUsername("ADMIN");
//            adminEntity.setPassword("ADMIN");
//            Set<RoleEntity> roleEntityTemp = new HashSet<>();
//            RoleEntity userRole = new RoleEntity();
//            userRole.setAuthority("ADMIN");
//            userRole.setId(1);
//            roleEntityTemp.add(userRole);
//            adminEntity.setRoles(roleEntityTemp);
//
//            accountRepository.insert(adminEntity);
//        }
//    }
    public static String currentUserGlobal = "";
    public static String currentUrlGlobal = "";

  
}
