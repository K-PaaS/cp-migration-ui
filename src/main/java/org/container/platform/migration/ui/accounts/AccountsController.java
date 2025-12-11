package org.container.platform.migration.ui.accounts;

import org.container.platform.migration.ui.common.ConstantsUrl;
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
 * Accounts Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2025.03.19
 **/

@Controller
public class AccountsController {

    private static final String BASE_URL = "accounts/";

    private final RestTemplateService restTemplateService;

    @Autowired
    private LocaleResolver localeResolver;

    public AccountsController(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    private void setCommonData(Model model, HttpServletRequest request, HttpServletResponse response) {
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

            if (paramLang != null && !paramLang.isEmpty()) {
                Locale newLocale = new Locale(paramLang);

                localeResolver.setLocale(request, response, newLocale);
                LocaleContextHolder.setLocale(newLocale);
                request.setAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME, newLocale);

                model.addAttribute("lang", paramLang);
            } else {
                model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
            }

        } catch (Exception e) {
            model.addAttribute("accessToken", "");
            model.addAttribute("userGuid", "");
            model.addAttribute("lang", "ko");
        }
    }

    @GetMapping(value = ConstantsUrl.URI_CP_ACCOUNTS_LIST)
    public String getAccountsList(Model model, HttpServletRequest request, HttpServletResponse response) {
        setCommonData(model, request, response);
        return BASE_URL + "accounts";
    }

    @GetMapping(value = ConstantsUrl.URI_CP_ACCOUNTS_DETAIL)
    public String getAccountsDetail(Model model, HttpServletRequest request, HttpServletResponse response) {
        setCommonData(model, request, response);
        return BASE_URL + "accountsDetail";
    }

    @GetMapping(value = ConstantsUrl.URI_CP_ACCOUNTS_CREATE)
    public String getAccountsCreate(Model model, HttpServletRequest request, HttpServletResponse response) {
        setCommonData(model, request, response);
        return BASE_URL + "accountsCreate";
    }
}