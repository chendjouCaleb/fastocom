package app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("app.security")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    private UserDetailsService userDetailsService;

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        //super(true);
        this.userDetailsService = userDetailsService;
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authManager) throws Exception {
        authManager.userDetailsService(userDetailsService);

    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
          .authorizeRequests()
                .antMatchers("/login", "/registration", "/web/dist/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")

                .defaultSuccessUrl("/account/home")
                .failureUrl("/login")
                .and()
                .logout().logoutSuccessUrl("/login")
                .and()
                .rememberMe().key("fastocom_xxx")
                .and().csrf().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(rawPassword.toString());
            }
        };
    }
}
