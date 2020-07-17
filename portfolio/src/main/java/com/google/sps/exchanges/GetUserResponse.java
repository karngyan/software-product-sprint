package com.google.sps.exchanges;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetUserResponse {
    private boolean userLoggedIn;
    // if user logged in
    private String email;
    private String logoutUrl;
    private String nick;
    // if user not logged in
    private String loginUrl;
}
