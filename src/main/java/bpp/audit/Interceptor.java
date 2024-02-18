package bpp.audit;

import bpp.model.AuditModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class Interceptor implements HandlerInterceptor {
    private final AuditService auditService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuditModel auditModel = new AuditModel(request.getRemoteAddr(), request.getRequestURI());

        auditService.save(auditModel);
        return true;
    }
}
