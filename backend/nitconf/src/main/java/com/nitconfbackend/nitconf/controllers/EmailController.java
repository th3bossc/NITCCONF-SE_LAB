package com.nitconfbackend.nitconf.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.nitconfbackend.nitconf.models.User;
import com.nitconfbackend.nitconf.repositories.UserRepository;
import com.nitconfbackend.nitconf.service.EmailSender;
import com.nitconfbackend.nitconf.service.JwtService;
import com.nitconfbackend.nitconf.types.ResendRequest;
import com.nitconfbackend.nitconf.types.ResetRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EmailSender emailSender;

    /**
     * verifyEmail
     * Upon registration, an automated email is sent to the user with a link to this
     * route.
     * This route handles the verification of the access token sent to the user and
     * marks his email as verified
     * 
     * @param token String
     * @return "Email verified successfully"
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyEmail(@PathVariable String token) {
        String email = jwtService.extractUsername(token);
        User user = userRepo.findByEmail(email).orElseThrow();
        user.setIsVerified(true);
        userRepo.save(user);
        return ResponseEntity.ok("Email verified successfully");
    }

    /**
     * resendEmail
     * If the user accidentally deletes the email sent to the provided email
     * address, he can request for another verification email via this controller
     * 
     * @param body {@link ResendRequest}
     * @return "Email sent successfully"
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @PostMapping("/resend")
    public ResponseEntity<String> resendEmail(@RequestBody ResendRequest body) {
        User user = userRepo.findByEmail(body.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        emailSender.sendEmail("NITCONF", user.getEmail(), "verify email",
                "click the below link to verify your email \n http://localhost:8080/api/email/verify/" + token);
        return ResponseEntity.ok("Email sent successfully");
    }

    /**
     * resetEmail
     * On the occation that the user needs to reset his password, he can request for
     * a timed token with his credentials.
     * This token is sent as a callback to the frontend interface, which handles the
     * entry of the new password
     * 
     * @param body {@link ResendRequest}
     * @return "Email sent successfully"
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @PostMapping("/reset")
    public ResponseEntity<String> resetEmail(@RequestBody ResendRequest body) {
        System.out.println(body.getEmail());
        User user = userRepo.findByEmail(body.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        emailSender.sendEmail("NITCONF", user.getEmail(), "reset password",
                "click the below link to reset your password \n http://localhost:3000/reset/" + token);
        return ResponseEntity.ok("Email sent successfully");
    }

    /**
     * resetPassword
     * The user's password is updated with the updated information
     * 
     * @param token - The token generated by the route
     *              {@link #resetEmail(ResendRequest)} is used for verification of
     *              user identity and time interval
     * @param body  - {@link ResetRequest}
     * @return "Password reset successfully"
     * @since 1.0
     * @author <a href="https://th3bossc.github.io/Portfolio"> Diljith P D</a>
     */
    @PutMapping("/reset/{token}")
    public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody ResetRequest body) {
        if (jwtService.isTokenExpired(token))
            return ResponseEntity.badRequest().body("Token expired");
        String email = jwtService.extractUsername(token);
        User user = userRepo.findByEmail(email).orElseThrow();
        user.setPassword(encoder.encode(body.password));
        userRepo.save(user);
        return ResponseEntity.ok("Password reset successfully");
    }
}
