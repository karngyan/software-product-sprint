
package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns all quotes */
@WebServlet("/quotes")
public final class QuotesServlet extends HttpServlet {

    private List<String> quotes;

    @Override
    public void init() {
        quotes = new ArrayList<>();
        quotes.add("When you are backed against the wall, break the goddamn thing down.");
        quotes.add("I don’t play the odds I play the man.");
        quotes.add("I don’t play the odds I play the man.");
        quotes.add("You always have a choice.");
        quotes.add("They think you care, they’ll walk all over you.");
        quotes.add("Sorry, I can’t hear you over the sound of how awesome I am.");
        quotes.add("Anyone can do my job, but no one can be me.");
        quotes.add("I don’t have dreams, I have goals.");
        quotes.add("It’s going to happen, because I am going to make it happen.");
        quotes.add("It’s not bragging if it’s true.");
        quotes.add("Win a no win situation by rewriting the rules.");
        quotes.add("Let them hate, just make sure they spell your name right.");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String quotesJsonString = new Gson().toJson(quotes);

	    response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(quotesJsonString);
    }
}