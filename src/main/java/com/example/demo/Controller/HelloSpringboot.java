package com.example.demo.Controller;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloSpringboot {
    @RequestMapping(path = {"/helloSpringBoot"})
    public String HelloSpring (){
        System.out.println("hello spring 张旺健 boot");
        return "hello spring 张旺健 boot";
    }

    @RequestMapping(value = {"/helloSpringBoot/{repo_name:.+}/repo"})
    public String HelloSpringPath (@PathVariable(value = "repo_name") String repo_name){
        System.out.println(repo_name);
        return repo_name;
    }

    @RequestMapping(value = "/helloSpringBoot/{value1}/repos", method = RequestMethod.GET)
    public void getValue(@PathVariable String value1, HttpServletRequest request) {
        String value = extractPathFromPattern(request);

    }

    private String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }

}