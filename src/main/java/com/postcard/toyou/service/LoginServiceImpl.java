package com.postcard.toyou.service;

import com.postcard.toyou.dao.MemberMapper;
import com.postcard.toyou.dto.NaverTokenDTO;
import com.postcard.toyou.dto.SmsResponseDTO;
import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private MemberMapper mMapper;

    @Override
    public ResponseEntity<ResultModel> login(MemberModel mModel) {

        ResultModel rModel = new ResultModel();

        mModel = mMapper.login(mModel);
        if(mModel.getName() != null) {
            rModel.setState(true);
            rModel.setResult(mModel);
            rModel.setMessage(mModel.getName()+"님 반갑습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("아이디 비밀번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> naverLogin(String params) {

        ResultModel rModel = new ResultModel();
        RestTemplate restTemplate = new RestTemplate();

        try {
            URI uri = new URI("https://nid.naver.com/oauth2.0/token?"+params );
            NaverTokenDTO response = restTemplate.postForObject(uri, null, NaverTokenDTO.class);

            if (response != null) {
                rModel.setState(true);
                rModel.setMessage("네이버 로그인에 성공했습니다.");
                rModel.setResult(response);
            } else {
                rModel.setState(false);
                rModel.setMessage("네이버 로그인 응답을 받지 못했습니다.");
            }
        } catch (URISyntaxException e) {
            rModel.setState(false);
            rModel.setMessage("잘못된 URI 형식입니다.");
        } catch (Exception e) {
            rModel.setState(false);
            rModel.setMessage("네이버 로그인 중 오류가 발생했습니다: " + e.getMessage());
        }

        return ResponseEntity.ok(rModel);
    }

}
