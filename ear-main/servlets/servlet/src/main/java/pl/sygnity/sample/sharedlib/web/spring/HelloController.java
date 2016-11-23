package pl.sygnity.sample.sharedlib.web.spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.sygnity.ApplicationProperties;
import pl.sygnity.GuavaTest;

@Controller
public class HelloController {

    @RequestMapping(value = "/spring-test", method = RequestMethod.GET)
    public String springTest(ModelMap model) {
        model.addAttribute("message", "Spring 3 MVC Hello World");
        return "hello";
    }

    @RequestMapping(value = "/guava-test", method = RequestMethod.GET)
    public String guavaTest(ModelMap model) {
        GuavaTest.isNullOrEmpty("");
        model.addAttribute("message", "Guava");
        return "hello";
    }


    @RequestMapping(value = "/tld-problem", method = RequestMethod.GET)
    public String tldProblem(ModelMap model) {
        model.addAttribute("message", "Spring 3 MVC Hello World, Guava test:" + GuavaTest.isNullOrEmpty(""));
        return "tld-problem";
    }

    @RequestMapping(value = "/config/{paramName}", method = RequestMethod.GET)
    public String configurationReload(@PathVariable String paramName, ModelMap model) {
        model.addAttribute("message", paramName + "=" + ApplicationProperties.getProperty(paramName, String.class, "brak-wartosci"));
        return "hello";
    }

}
