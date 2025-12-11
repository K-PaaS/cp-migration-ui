package org.container.platform.migration.ui.login;

import org.container.platform.migration.ui.common.Constants;
import org.container.platform.migration.ui.common.ConstantsUrl;
import org.container.platform.migration.ui.common.RestTemplateService;
import org.container.platform.migration.ui.login.model.AuthenticationResponse;
import org.container.platform.migration.ui.login.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * User Service 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2025.03.19
 **/
@Service
public class ProviderService {

    private final RestTemplateService restTemplateService;

    @Autowired
    public ProviderService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }


    /**
     * 클러스터 관리자 로그인(Post cluster admin login)
     *
     * @param users the users
     * @return the resultStatus
     */
    public AuthenticationResponse loginUsers(Users users) {
        return restTemplateService.send(Constants.TARGET_CP_API, ConstantsUrl.URL_API_LOGIN, HttpMethod.POST, users, AuthenticationResponse.class);
    }

}