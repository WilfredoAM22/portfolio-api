
package com.wilfredo.portfolio_api.service;

import com.wilfredo.portfolio_api.dto.ContactRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class ContactService {

    private static final Logger log =
            LoggerFactory.getLogger(ContactService.class);

    private final EmailService emailService;
    private final String recipientEmail;

    public ContactService(
            EmailService emailService,
            @Value("${app.contact.recipient}") String recipientEmail) {

        this.emailService = emailService;
        this.recipientEmail = recipientEmail;
    }

    public void processContact(ContactRequest request) {

        log.info(
                "Nuevo mensaje de contacto recibido de: {}",
                request.name()
        );

        String subject = "Nuevo mensaje desde tu portafolio";

        String body = buildContactHtml(request);

        emailService.sendEmail(recipientEmail, subject, body);
    }


    private String buildContactHtml(ContactRequest request) {

        String name = HtmlUtils.htmlEscape(request.name());
        String email = HtmlUtils.htmlEscape(request.email());

        String message = HtmlUtils.htmlEscape(request.message())
                .replace("\r\n", "<br>")
                .replace("\n", "<br>")
                .replace("\r", "<br>");

        return """
            <div style="margin:0; padding:30px 15px;
                        background-color:#0d1117;
                        font-family:Arial,Helvetica,sans-serif;">

                <div style="max-width:600px; margin:0 auto;
                            background-color:#161b22;
                            border:1px solid #30363d;
                            border-radius:10px;
                            overflow:hidden;">

                    <!-- HEADER -->
                    <div style="padding:20px 24px;
                                border-bottom:1px solid #30363d;">

                        <table width="100%%" cellpadding="0" cellspacing="0">
                            <tr>
                                <td style="color:#c9d1d9;
                                           font-size:13px;
                                           font-weight:bold;">
                                    PORTFOLIO / NOTIFICATIONS
                                </td>

                                <td align="right">
                                    <span style="display:inline-block;
                                                 padding:5px 9px;
                                                 background-color:#1c3326;
                                                 border-radius:20px;
                                                 color:#3fb950;
                                                 font-size:10px;
                                                 font-weight:bold;">
                                        NEW MESSAGE
                                    </span>
                                </td>
                            </tr>
                        </table>

                    </div>

                    <!-- MAIN CONTENT -->
                    <div style="padding:28px 24px;">

                        <h2 style="color:#c9d1d9;
                                   font-size:24px;
                                   margin:0 0 10px;">
                            Nuevo mensaje recibido
                        </h2>

                        <p style="color:#8b949e;
                                  font-size:14px;
                                  line-height:1.6;
                                  margin:0;">
                            Se ha recibido una nueva consulta desde tu portafolio.
                        </p>

                        <!-- CONTACT INFORMATION -->
                        <div style="margin-top:28px;
                                    padding:20px;
                                    background-color:#0d1117;
                                    border:1px solid #30363d;
                                    border-radius:8px;">

                            <p style="color:#3fb950;
                                      font-size:11px;
                                      font-weight:bold;
                                      margin:0 0 20px;">
                                01 / CONTACT INFORMATION
                            </p>

                            <p style="color:#8b949e;
                                      font-size:11px;
                                      margin:0 0 6px;">
                                NOMBRE
                            </p>

                            <p style="color:#c9d1d9;
                                      font-size:15px;
                                      font-weight:bold;
                                      margin:0 0 18px;">
                                %s
                            </p>

                            <hr style="border:0;
                                       border-top:1px solid #30363d;
                                       margin:0 0 18px;">

                            <p style="color:#8b949e;
                                      font-size:11px;
                                      margin:0 0 6px;">
                                EMAIL
                            </p>

                            <p style="color:#58a6ff;
                                      font-size:14px;
                                      margin:0;
                                      word-break:break-word;">
                                %s
                            </p>

                        </div>

                        <!-- MESSAGE -->
                        <div style="margin-top:18px;
                                    padding:20px;
                                    background-color:#0d1117;
                                    border:1px solid #30363d;
                                    border-radius:8px;">

                            <p style="color:#3fb950;
                                      font-size:11px;
                                      font-weight:bold;
                                      margin:0 0 18px;">
                                02 / MESSAGE
                            </p>

                            <p style="color:#8b949e;
                                      font-size:11px;
                                      margin:0 0 10px;">
                                MESSAGE.TXT
                            </p>

                            <div style="color:#c9d1d9;
                                        font-size:14px;
                                        line-height:1.8;
                                        overflow-wrap:break-word;">
                                %s
                            </div>

                        </div>

                        <!-- STATUS -->
                        <table width="100%%"
                               cellpadding="0"
                               cellspacing="0"
                               style="margin-top:25px;">
                            <tr>
                                <td style="color:#8b949e;
                                           font-size:11px;">
                                    DELIVERY STATUS
                                </td>

                                <td align="right">
                                    <span style="color:#3fb950;
                                                 font-size:11px;
                                                 font-weight:bold;">
                                        ● RECEIVED
                                    </span>
                                </td>
                            </tr>
                        </table>

                    </div>

                    <!-- FOOTER -->
                    <div style="padding:18px 20px;
                                border-top:1px solid #30363d;
                                text-align:center;">

                        <p style="color:#8b949e;
                                  font-size:11px;
                                  margin:0;">
                            Generated by Wilfredo's Portfolio
                            · Contact API
                        </p>

                    </div>

                </div>
            </div>
            """.formatted(name, email, message);
    }

}