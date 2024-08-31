package seominkim.puppyAlert.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seominkim.puppyAlert.domain.user.dto.request.LoginRequest;
import seominkim.puppyAlert.domain.user.dto.request.SignUpRequest;
import seominkim.puppyAlert.domain.user.dto.response.LoginResponse;
import seominkim.puppyAlert.domain.user.dto.response.SignUpResponse;
import seominkim.puppyAlert.domain.host.entity.Host;
import seominkim.puppyAlert.domain.host.repository.HostRepository;
import seominkim.puppyAlert.domain.puppy.entity.Puppy;
import seominkim.puppyAlert.domain.puppy.repository.PuppyRepository;
import seominkim.puppyAlert.domain.user.entity.User;
import seominkim.puppyAlert.domain.user.repository.UserRepository;
import seominkim.puppyAlert.global.entity.UserType;
import seominkim.puppyAlert.global.exception.errorCode.ErrorCode;
import seominkim.puppyAlert.global.exception.exception.CommonException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PuppyRepository puppyRepository;
    @Autowired
    HostRepository hostRepository;

    public SignUpResponse signUp(SignUpRequest signUpRequest){
        String id = signUpRequest.id();

        if(userRepository.existsById(id)){
            throw new CommonException(ErrorCode.INVALID_USERTYPE);
        }

        UserType userType = signUpRequest.userType();

        if(userType.equals(UserType.HOST)){
            Host host = new Host();
            host.setId(id);
            host.setPassword(signUpRequest.password());
            host.setNickName(signUpRequest.nickName());
            host.setName(signUpRequest.name());
            host.setBirth(signUpRequest.birth());
            host.setPhoneNumber(signUpRequest.phoneNumber());
            host.setAddress(signUpRequest.address());
            host.setDetailAddress(signUpRequest.detailAddress());
            host.setLocation(signUpRequest.location());
            hostRepository.save(host);
        }else {
            Puppy puppy = new Puppy();
            puppy.setId(id);
            puppy.setPassword(signUpRequest.password());
            puppy.setNickName(signUpRequest.nickName());
            puppy.setName(signUpRequest.name());
            puppy.setPhoneNumber(signUpRequest.phoneNumber());
            puppy.setAddress(signUpRequest.address());
            puppy.setDetailAddress(signUpRequest.detailAddress());
            puppy.setBirth(signUpRequest.birth());
            puppy.setLocation(signUpRequest.location());
            puppyRepository.save(puppy);
        }

        return new SignUpResponse(id, userType);
    }

    public LoginResponse checkIfAccountExists(LoginRequest loginRequest){
        String id = loginRequest.id();
        String password = loginRequest.password();

        // ID가 존재하지 않으면 로그인 실패임
        if(!checkIfIdExists(id)){
            throw new CommonException(ErrorCode.INVALID_ID);
        }

        // ID PW 모두 올바르면
        if(userRepository.existsByIdAndPassword(id, password)){
            User user = userRepository.findById(id).get();
            String nickName = user.getNickName();
            if(user instanceof Host){
                return new LoginResponse(nickName, UserType.HOST);
            }else{
                return new LoginResponse(nickName, UserType.PUPPY);
            }
        }

        // 아니면 ID는 존재하는데 PW이 틀린거임
        throw new CommonException(ErrorCode.INVALID_PASSWORD);
    }

    public boolean checkIfIdExists(String id){
        return userRepository.existsById(id);
    }

    public boolean checkIfNickNameExists(String nickName) {
        return userRepository.existsByNickName(nickName);
    }
}