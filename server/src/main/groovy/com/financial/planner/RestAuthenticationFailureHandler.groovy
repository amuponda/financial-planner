package com.financial.planner

import groovy.json.JsonBuilder
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.context.MessageSource
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@CompileStatic
class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
    /**
     * Configurable status code, by default: conf.rest.login.failureStatusCode?:HttpServletResponse.SC_FORBIDDEN
     */
    Integer statusCode
    MessageSource messageSource
    JsonBuilder builder
    /**
     * Called when an authentication attempt fails.
     * @param request the request during which the authentication attempt occurred.
     * @param response the response.
     * @param exception the exception which was thrown to reject the authentication request.
     */
    void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.addHeader('WWW-Authenticate', 'Bearer')
        response.setContentType('application/json')
        response.setStatus(statusCode)
        log.info("${exception.getClass()}")
        String msg
        if (exception instanceof BadCredentialsException) {
            msg = messageSource.getMessage("springSecurity.login.badCredentials", null, request.locale)
        } else {
            msg = messageSource.getMessage("springSecurity.login.authFailed", null, request.locale)
        }
        builder.call([message: msg])
        response.getWriter().write(builder.toString())
        //PrintWriter out = response.getWriter()
        //out.print(builder.toString())
        //out.flush()
    }
}
