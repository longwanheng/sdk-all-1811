package com.test.sdk.web.exception;

import com.test.sdk.common.exception.SdkException;
import com.test.sdk.common.util.ErrorConstants;
import com.test.sdk.common.util.JsonUtil;
import com.test.sdk.common.util.ResponseTO;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SdkExceptionHandler implements HandlerExceptionResolver {
    @Nullable
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @Nullable Object o, Exception e) {
        e.printStackTrace();
        ResponseTO response = new ResponseTO();
        response.setSuccess(false);
        if (e instanceof SdkException) {
            SdkException exception = (SdkException) e;
            response.setError(exception.getError());
        }else{
            response.setError(ErrorConstants.UNKNOWN_ERROR);
        }
        MappingJackson2JsonView view=new MappingJackson2JsonView();
        view.setExtractValueFromSingleKeyModel(true);
        return new ModelAndView(view,"response",response);
    }

    public static void handlerException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,  Exception e){
        ResponseTO response = new ResponseTO();
        if (e instanceof SdkException) {
            SdkException exception = (SdkException) e;
            response.setError(exception.getError());
        }
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            out.print(JsonUtil.getJSON(response));
            out.flush();
        } catch (IOException ex) {
        } finally {
            if (out != null) {
                out.close();
                out = null;
            }
        }
    }
}
