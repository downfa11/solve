package com.ns.solve.Controller;

import com.ns.solve.Service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/membership")
public class MembershipController {

    private final MembershipService membershipService;

}
