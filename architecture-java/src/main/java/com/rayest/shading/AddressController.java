package com.rayest.shading;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AddressController {

    @Resource
    private AddressMapper addressMapper;

    @RequestMapping("/address/save")
    public void save() {
        for (int i = 0; i < 10; i++) {
            Address address = new Address();
            address.setCode("code_" + i);
            address.setName("name_" + i);
            address.setPid(i + "");
            address.setType(0);
            address.setLit(i % 2 == 0 ? 1 : 2);
            addressMapper.save(address);
        }
    }

    @RequestMapping("/address/get/{id}")
    public Address get(@PathVariable Long id) {
        return addressMapper.get(id);
    }
}
