package com.yangtzelsl.security;

import com.yangtzelsl.security.authentication.MyAuthentiationFailureHandler;
import com.yangtzelsl.security.authentication.MyAuthenticationSuccessHandler;
import com.yangtzelsl.security.authentication.MyLogoutSuccessHandler;
import com.yangtzelsl.security.authentication.RestAuthenticationAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义用户认证逻辑
 *   1.处理用户信息获取逻辑 UserDetailsService
 *   2.处理用户校验逻辑 UserDetails
 *   3.处理密码加密解密逻辑 PasswordEncoder
 *
 * 自定义用户验证流程
 *   1.自定义登录页面 /login.html
 *   2.自定义登录成功处理 MyAuthenticationSuccessHandler
 *   3.自定义登录失败处理 MyAuthenctiationFailureHandler
 */
@Configuration
@EnableWebSecurity// 这个注解必须加，开启Security
@EnableGlobalMethodSecurity(prePostEnabled = true)//保证post之前的注解可以使用
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthentiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    /**
     * http请求的安全处理
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 关闭 跨站请求伪造
        httpSecurity.csrf().disable();
        // 开始请求权限配置
        httpSecurity.authorizeRequests()
                // 下面配置的这些资源放行
                .antMatchers("/login.html",
                        "/my/**",
                        "/treetable-lay/**",
                        "/xadmin/**",
                        "/ztree/**",
                        "/statics/**"
//                        "/templates/**"
                )
                .permitAll()
                // 任何请求都要通过权限验证
                .anyRequest()
                .authenticated()
        ;
        //解决X-Frame-Options DENY问题
        httpSecurity.headers().frameOptions().sameOrigin();
        httpSecurity.formLogin()
                // 指定自定义登录页面
                .loginPage("/login.html")
                // 指定默认的拦截URL处理地址
                .loginProcessingUrl("/login")
                // 成功怎么处理
                .successHandler(myAuthenticationSuccessHandler)
                // 失败怎么处理
                .failureHandler(myAuthenctiationFailureHandler)
                .and()
                .logout()
                .permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(myLogoutSuccessHandler)
        ;
        //异常处理
        httpSecurity.exceptionHandling().accessDeniedHandler(restAuthenticationAccessDeniedHandler);
    }

    /**
     * 使用的BCrypt加密/解密方式，类似的还有MD5等
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份验证
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                // 密码加密
                .passwordEncoder(passwordEncoder());
    }

}
