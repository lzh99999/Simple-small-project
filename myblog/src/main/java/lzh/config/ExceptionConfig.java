package lzh.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(Exception.class)
    public ModelAndView handler(Exception e) {
        ModelAndView mv = new ModelAndView();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        mv.addObject("message",e.getMessage());
        mv.addObject("stackTrace",sw.toString());
        mv.setViewName("error");
        return mv;
    }
}
