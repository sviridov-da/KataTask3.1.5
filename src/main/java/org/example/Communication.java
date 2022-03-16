package org.example;

import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Communication {
    private HttpHeaders httpHeaders;
    private final RestTemplate restTemplate;
    private final String url = "http://94.198.50.185:7081/api/users";
    private String resultStr;

    public String getRes(){
        return resultStr;
    }

    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        httpHeaders = new HttpHeaders();
        resultStr = "";
    }

    public List<User> getAllUsers(){
        ResponseEntity<List<User>> response =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<User>>() {});

        HttpHeaders headers = response.getHeaders();
        String cookies = headers.get("set-cookie").get(0);
        int start = cookies.indexOf('=')+1;
        int end = cookies.indexOf(';');
        cookies = "JSESSIONID=" + cookies.substring(start, end);
        httpHeaders.add("Cookie", cookies);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        List<User> result = response.getBody();
        return result;
    }

//    public User getUserById(long id){
//        ResponseEntity<User> response =
//                restTemplate.exchange(new StringBuilder(url).append("/").append(id).toString(), HttpMethod.GET,
//                            new HttpEntity<User>(httpHeaders),
//                                    new ParameterizedTypeReference<User>() {});
//
//        User result = response.getBody();
//        return result;
//    }

    public void saveUser(User user){
        HttpEntity<User> request = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> response = restTemplate
                .exchange(url, HttpMethod.POST, request, String.class);

        resultStr += response.getBody();
    }

    public void updateUser(User user){
        HttpEntity<User> request = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> response = restTemplate
                .exchange(url, HttpMethod.PUT, request, String.class);

        resultStr += response.getBody();
    }

    public void deleteUserById(int id){
        HttpEntity<User> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate
                .exchange(url+"/"+id, HttpMethod.DELETE, request, String.class);
        resultStr += response.getBody();
    }
}
