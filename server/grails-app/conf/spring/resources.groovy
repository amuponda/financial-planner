import com.financial.planner.RestAuthenticationFailureHandler
import com.financial.planner.UserPasswordEncoderListener
import groovy.json.JsonBuilder

import javax.servlet.http.HttpServletResponse

// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)

    restAuthenticationFailureHandler(RestAuthenticationFailureHandler) {
        statusCode = HttpServletResponse.SC_UNAUTHORIZED
        builder = new JsonBuilder()
        messageSource = ref('messageSource')
    }
}
