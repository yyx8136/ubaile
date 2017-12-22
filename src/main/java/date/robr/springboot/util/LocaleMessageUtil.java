package date.robr.springboot.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import date.robr.springboot.config.RestServiceError;
/**
 * 异常处理信息本地化
 * @author 岳亦祥
 *
 */
@Component
public class LocaleMessageUtil {

    @Autowired
    private MessageSource messageSource;

    public RestServiceError getLocalErrorMessage(RestServiceError.Type errorCode) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(errorCode.getCode(), null, locale);
        RestServiceError error = RestServiceError.build(errorCode, errorMessage);
        return error;
    }

}