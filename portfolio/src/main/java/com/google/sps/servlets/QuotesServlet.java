
package com.google.sps.servlets;

import com.google.sps.data.Quotes;
import com.google.sps.exchanges.GetQuotesResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns all quotes */
@WebServlet("/quotes")
public final class QuotesServlet extends HttpServlet {

    private List<Quotes> quotes;

    @Override
    public void init() {
        quotes = new ArrayList<>();
        List<String> suitsQuotes = 
                Arrays.asList(
                    "When you are backed against the wall, break the goddamn thing down.",
                    "I don’t play the odds I play the man.",
                    "I don’t play the odds I play the man.",
                    "You always have a choice.",
                    "They think you care, they’ll walk all over you.",
                    "Sorry, I can’t hear you over the sound of how awesome I am.",
                    "Anyone can do my job, but no one can be me.",
                    "I don’t have dreams, I have goals.",
                    "It’s going to happen, because I am going to make it happen.",
                    "It’s not bragging if it’s true.",
                    "Win a no win situation by rewriting the rules.",
                    "Let them hate, just make sure they spell your name right."
                );

        quotes.add(new Quotes("suits", suitsQuotes));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        GetQuotesResponse getQuotesResponse = new GetQuotesResponse(quotes);
        String responseString = new Gson().toJson(getQuotesResponse);

	    response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(responseString);
    }
}