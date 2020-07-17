package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.sps.exchanges.GetUserResponse;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public final class UserServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        GetUserResponse getUserResponse = new GetUserResponse();
        UserService userService = UserServiceFactory.getUserService();
        getUserResponse.setUserLoggedIn(userService.isUserLoggedIn());

        if (userService.isUserLoggedIn()) {
            getUserResponse.setEmail(userService.getCurrentUser().getEmail());
            String urlToRedirectToAfterUserLogsOut = "/";
            getUserResponse.setLogoutUrl(userService
                    .createLogoutURL(urlToRedirectToAfterUserLogsOut));
        } else {
            String urlToRedirectToAfterUserLogsIn = "/";
            getUserResponse.setLoginUrl(userService
                    .createLoginURL(urlToRedirectToAfterUserLogsIn));
        }

        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(getUserResponse));

    }
}
