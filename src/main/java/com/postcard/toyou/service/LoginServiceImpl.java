package com.postcard.toyou.service;

import com.postcard.toyou.dao.MemberMapper;
import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static com.postcard.toyou.service.SnsTokenEnum.fromString;
import static com.postcard.toyou.service.SnsUserInfoEnum.toUpperString;

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
    public ResponseEntity<ResultModel> snsLogin(String btnType, String params) {

        ResultModel rModel = new ResultModel();
        RestTemplate restTemplate = new RestTemplate();

        SnsTokenEnum snsType = fromString(btnType);

        try {
            URI uri = new URI(snsType.getUri() + "?" + params);
            Object response = restTemplate.postForObject(uri, null, Object.class);

            if (response != null) {
                rModel.setState(true);
                rModel.setMessage(snsType.getName() + " 로그인에 성공했습니다.");
                rModel.setResult(response);
            } else {
                rModel.setState(false);
                rModel.setMessage(snsType.getName() + " 로그인 응답을 받지 못했습니다.");
            }
        } catch (URISyntaxException e) {
            rModel.setState(false);
            rModel.setMessage("잘못된 URI 형식입니다.");
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            rModel.setState(false);
            rModel.setMessage(snsType.getName() + " 로그인 중 서버 응답 오류: " + e.getStatusCode() + " " + e.getStatusText());
        } catch (Exception e) {
            rModel.setState(false);
            rModel.setMessage(snsType.getName() + " 로그인 중 오류가 발생했습니다: " + e.getMessage());
        }

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<ResultModel> getSnsInfo(String btnType, String authHeader, String contentHeader) {

        ResultModel rModel = new ResultModel();
        RestTemplate restTemplate = new RestTemplate();

        SnsUserInfoEnum snsType = toUpperString(btnType);

        try {
            URI uri = new URI(snsType.getUri());

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            headers.set("Content-type", contentHeader);
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Object.class);

            if (response.getBody() != null) {
                rModel.setState(true);
                rModel.setMessage(snsType.getName() + " 로그인에 성공했습니다.");
                rModel.setResult(response.getBody());
            } else {
                rModel.setState(false);
                rModel.setMessage(snsType.getName() + " 로그인 응답을 받지 못했습니다.");
            }
        } catch (URISyntaxException e) {
            rModel.setState(false);
            rModel.setMessage("잘못된 URI 형식입니다.");
        } catch (Exception e) {
            rModel.setState(false);
            rModel.setMessage(snsType.getName() + " 로그인 중 오류가 발생했습니다: " + e.getMessage());
        }

        return ResponseEntity.ok(rModel);
    }

}
