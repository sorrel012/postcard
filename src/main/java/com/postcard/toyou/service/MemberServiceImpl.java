package com.postcard.toyou.service;

import com.postcard.toyou.dao.MemberMapper;
import com.postcard.toyou.model.MemberModel;
import com.postcard.toyou.model.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.postcard.toyou.service.SnsTokenEnum.fromString;
import static com.postcard.toyou.service.SnsUserInfoEnum.toUpperString;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper mMapper;

    @Override
    public ResponseEntity<ResultModel> login(MemberModel mModel) {

        ResultModel rModel = new ResultModel();

        mModel = mMapper.login(mModel);
        try {
            if(mModel.getName() != null) {
                rModel.setState(true);
                rModel.setResult(mModel);
                rModel.setMessage(mModel.getName()+"님 반갑습니다.");
            }
        } catch(Exception e) {
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

    @Override
    public ResponseEntity<ResultModel> logout(String btnType, String authHeader, String contentHeader) {

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

            ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);

            if (response.getBody() != null) {
                rModel.setState(true);
                rModel.setMessage(snsType.getName() + " 로그아웃에 성공했습니다.");
                rModel.setResult(response.getBody());
            } else {
                rModel.setState(false);
                rModel.setMessage(snsType.getName() + " 로그아웃 응답을 받지 못했습니다.");
            }
        } catch (URISyntaxException e) {
            rModel.setState(false);
            rModel.setMessage("잘못된 URI 형식입니다.");
        } catch (Exception e) {
            rModel.setState(false);
            rModel.setMessage(snsType.getName() + " 로그아웃 중 오류가 발생했습니다: " + e.getMessage());
        }

        return ResponseEntity.ok(rModel);

    }

    public ResponseEntity<ResultModel> register(MemberModel mModel) {

        ResultModel rModel = new ResultModel();

        int result = mMapper.register(mModel);
        if(result == 1) {
            rModel.setState(true);
            rModel.setMessage("회원가입에 성공하였습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("회원가입에 실패하였습니다.");
        }

        return ResponseEntity.ok(rModel);
    };

    @Override
    public ResponseEntity<ResultModel> snsRegister(MemberModel mModel) {

        ResultModel rModel = new ResultModel();

        int result = mMapper.snsRegister(mModel);
        if(result == 1) {
            rModel.setState(true);
            rModel.setMessage("회원가입에 성공하였습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("회원가입에 실패하였습니다.");
        }

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<Object> selectDupId(String id) {

        ResultModel rModel = new ResultModel();

        List<String> dupId = this.mMapper.selectDupId(id);
        if(dupId.isEmpty()) {
            rModel.setState(true);
            rModel.setMessage(id + " 는 사용할 수 있는 아이디입니다.");
            rModel.setResult(dupId);
        } else {
            rModel.setState(false);
            rModel.setMessage(id + " 는 사용할 수 없는 아이디입니다.");
            rModel.setResult(dupId);
        }

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<Object> updateProfile(MemberModel mModel) {

        ResultModel rModel = new ResultModel();

        int result = this.mMapper.updateProfile(mModel);
        if(result == 1) {
            rModel.setState(true);
            rModel.setMessage("회원정보가 수정되었습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("회원정보를 수정하지 못했습니다.");
        }

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<Object> updatePw(MemberModel mModel) {

        ResultModel rModel = new ResultModel();

        int result = this.mMapper.updatePw(mModel);
        if(result == 1) {
            rModel.setState(true);
            rModel.setMessage("비밀번호가 수정되었습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("비밀번호를 수정하지 못했습니다.");
        }

        return ResponseEntity.ok(rModel);
    }

    @Override
    public ResponseEntity<Object> deleteAccount(String id) {

        ResultModel rModel = new ResultModel();

        int result = this.mMapper.deleteAccount(id);
        if(result == 1) {
            rModel.setState(true);
            rModel.setMessage("회원탈퇴를 성공했습니다.");
        } else {
            rModel.setState(false);
            rModel.setMessage("회원탈퇴를 실패했습니다.");
        }

        return ResponseEntity.ok(rModel);
    }

}
