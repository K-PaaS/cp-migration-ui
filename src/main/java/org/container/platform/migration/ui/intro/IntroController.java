package org.container.platform.migration.ui.intro;

import org.container.platform.migration.ui.common.RestTemplateService;
import org.container.platform.migration.ui.security.model.OAuthTokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Intro Controller 클래스
 */
@Controller
public class IntroController {

    private final RestTemplateService restTemplateService;

    @Autowired
    private LocaleResolver localeResolver;

    public IntroController(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @GetMapping(value = {"/"})
    public Object baseView(Model model, HttpServletRequest request, HttpServletResponse response) {

        try {
            OAuthTokens oAuthTokens = restTemplateService.getKeyCloakToken();
            if (oAuthTokens != null) {
                model.addAttribute("accessToken", oAuthTokens.getAccessToken());
                model.addAttribute("userGuid", oAuthTokens.getUserId());
            } else {
                model.addAttribute("accessToken", "");
                model.addAttribute("userGuid", "");
            }

            String paramLang = request.getParameter("lang");
            String currentLang;

            if (paramLang != null && !paramLang.isEmpty()) {
                Locale newLocale = new Locale(paramLang);

                localeResolver.setLocale(request, response, newLocale);
                LocaleContextHolder.setLocale(newLocale);
                request.setAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME, newLocale);

                currentLang = paramLang;
            } else {
                currentLang = LocaleContextHolder.getLocale().getLanguage();
            }

            model.addAttribute("lang", currentLang);

        } catch (Exception e) {
            model.addAttribute("accessToken", "");
            model.addAttribute("userGuid", "");
            model.addAttribute("lang", "ko");
        }
        return "accounts/accounts";
    }
}