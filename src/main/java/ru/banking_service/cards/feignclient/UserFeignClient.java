package ru.banking_service.cards.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.banking_service.cards.model.User;

@FeignClient(value = "userFeignClient", url = "http://localhost:8083/api/v1")
public interface UserFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id);
}
