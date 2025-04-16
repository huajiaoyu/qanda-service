package com.hjy.qanda.service.feign;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "obj", url = "https://ruankaogaoxiamng-1326521419.cos.ap-beijing.myqcloud.com")
public interface IObjectClient {

    @GetMapping(value = "/{fileName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response getFile(@PathVariable String fileName);

}
