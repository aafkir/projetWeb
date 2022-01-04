package fr.umontpellier.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private  DataSource dataSource;

/*    @Autowired
    public void globalConfig(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("{noop}123").roles("ADMIN");
                auth.inMemoryAuthentication().withUser("user2").password("{noop}1234").roles("USER");
}*/
   /*
    @Override
    protected  void config (AuthenticationManagerBuilder auth) throws  Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();

        System.out.println("****************************");
        System.out.println(passwordEncoder.encode("1234"));
        System.out.println("****************************");
        auth.inMemoryAuthentication().withUser("user11").password("{noop}}1234").roles("USER");
        auth.inMemoryAuthentication().withUser("user2").password("{noop}1234").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("USER");

    }*/




    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select login as principal, password as credentials from t_users where login=?")
                .authoritiesByUsernameQuery("select login as principal , roleName  as role from t_users u join  t_users_roles_associations t on u.idUSer=t.idUser join t_roles r ON r.idROle=t.idRole where u.login=?");
    //.anyRequest().authenticated();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/ajout", "/remove", "/updateMonument", "/updateEcole").hasRole("ADMIN")
                .antMatchers("/list").permitAll()
                .antMatchers("/rechercher").hasRole("TOURIST")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login");

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*    // create two users, admin and user
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
    }*/
}