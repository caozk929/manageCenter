package com.zjht.manager.common.log.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.zjht.manager.common.web.Constants;
import com.zjht.manager.common.web.session.SessionProvider;
import com.zjht.manager.common.web.threadvariable.RequestThread;
import com.zjht.manager.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * logback日志扩展，增加操作者和业务编码
 * @author caozhaokui
 *
 */
public class MessageConverter extends ClassicConverter {
	@Override
	public String convert(ILoggingEvent event) {
		StringBuilder sb = new StringBuilder();
		HttpServletRequest request = RequestThread.get();
		if(request != null){
			String bizCode = request.getParameter(Constants.BUSINESS_CODE);
			if(bizCode != null){
				sb.append(bizCode).append(Constants.SPT_LOG);
			}
		}
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject != null){
                Session session = subject.getSession();
                if(session!=null){
                    Object objUser = session.getAttribute(Constants.ADMIN_SESSION_KEY);
                    if(objUser != null){
                        User user = (User)objUser;
                        if(user != null){
                            sb.append(user.getUsername()).append(Constants.SPT_LOG);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
		sb.append(event.getFormattedMessage());
		return sb.toString();
	}
}
