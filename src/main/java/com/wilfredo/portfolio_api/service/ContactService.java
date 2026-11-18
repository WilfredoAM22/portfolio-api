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

        String emailSubject = "Nuevo mensaje desde tu portafolio";

        String body = buildContactHtml(request);

        emailService.sendEmail(recipientEmail, emailSubject, body);
    }

    private String buildContactHtml(ContactRequest request) {

        String name = HtmlUtils.htmlEscape(request.name());
        String email = HtmlUtils.htmlEscape(request.email());
        String subject = HtmlUtils.htmlEscape(request.subject());

        String message = HtmlUtils.htmlEscape(request.message())
                .replace("\r\n", "<br>")
                .replace("\n", "<br>")
                .replace("\r", "<br>");

        // Usa "%%" para porcentajes literales dentro de formatted().
        return """
            <div style="margin:0; padding:30px 15px;
                        background-color:#0d1117;
                        font-family:'Fira Code','JetBrains Mono',Menlo,Consolas,'Courier New',monospace;">

                <div style="max-width:640px; margin:0 auto;
                            background-color:#0d1117;
                            border:1px solid #30363d;
                            border-radius:10px;
                            overflow:hidden;">

                    <table width="100%%" cellpadding="0" cellspacing="0"
                           style="background-color:#161b22;
                                  border-bottom:1px solid #30363d;">
                        <tr>
                            <td width="70" style="padding:12px 0 12px 16px;
                                                  font-size:0; line-height:0;">
                                <span style="display:inline-block; width:10px; height:10px;
                                             border-radius:50%%; background-color:#ff5f56;
                                             margin-right:6px;"></span><span style="display:inline-block; width:10px; height:10px;
                                             border-radius:50%%; background-color:#ffbd2e;
                                             margin-right:6px;"></span><span style="display:inline-block; width:10px; height:10px;
                                             border-radius:50%%; background-color:#27c93f;"></span>
                            </td>
                            <td align="center" style="padding:12px 0;
                                                      color:#8b949e;
                                                      font-size:12px;">
                                wilfredo@portfolio — terminal
                            </td>
                            <td width="70"></td>
                        </tr>
                    </table>

                    <div style="padding:24px; color:#c9d1d9;
                                font-size:13px; line-height:1.7;">

                        <p style="margin:0;">
                            <span style="color:#3fb950;">wilfredo@portfolio</span><span style="color:#8b949e;">:</span><span style="color:#58a6ff;">~</span><span style="color:#8b949e;">$</span>
                            <span style="color:#c9d1d9;">cat nuevo-mensaje.txt</span>
                        </p>

                        <p style="margin:10px 0 0; color:#3fb950; font-weight:bold;">
                            [ OK ] Nuevo mensaje recibido desde tu portafolio
                        </p>

                        <p style="margin:24px 0 0;">
                            <span style="color:#3fb950;">wilfredo@portfolio</span><span style="color:#8b949e;">:</span><span style="color:#58a6ff;">~</span><span style="color:#8b949e;">$</span>
                            <span style="color:#c9d1d9;">cat contacto.json</span>
                        </p>

                        <table width="100%%" cellpadding="0" cellspacing="0"
                               style="margin-top:10px;
                                      border-left:2px solid #30363d;">
                            <tr>
                                <td width="90" valign="top"
                                    style="padding:4px 0 4px 14px;
                                           color:#8b949e; font-size:12px;">
                                    nombre
                                </td>
                                <td style="padding:4px 0; color:#c9d1d9;
                                           font-weight:bold; word-break:break-word;">
                                    %s
                                </td>
                            </tr>
                            <tr>
                                <td width="90" valign="top"
                                    style="padding:4px 0 4px 14px;
                                           color:#8b949e; font-size:12px;">
                                    email
                                </td>
                                <td style="padding:4px 0; color:#58a6ff;
                                           word-break:break-word;">
                                    %s
                                </td>
                            </tr>
                            <tr>
                                <td width="90" valign="top"
                                    style="padding:4px 0 4px 14px;
                                           color:#8b949e; font-size:12px;">
                                    asunto
                                </td>
                                <td style="padding:4px 0; color:#c9d1d9;
                                           font-weight:bold; word-break:break-word;">
                                    %s
                                </td>
                            </tr>
                        </table>

                        <p style="margin:24px 0 0;">
                            <span style="color:#3fb950;">wilfredo@portfolio</span><span style="color:#8b949e;">:</span><span style="color:#58a6ff;">~</span><span style="color:#8b949e;">$</span>
                            <span style="color:#c9d1d9;">cat mensaje.txt</span>
                        </p>

                        <div style="margin-top:10px;
                                    padding:14px 16px;
                                    background-color:#161b22;
                                    border:1px solid #30363d;
                                    border-radius:6px;
                                    color:#c9d1d9;
                                    line-height:1.8;
                                    overflow-wrap:break-word;
                                    word-break:break-word;">
                            %s
                        </div>

                        <p style="margin:24px 0 0;">
                            <span style="color:#3fb950;">wilfredo@portfolio</span><span style="color:#8b949e;">:</span><span style="color:#58a6ff;">~</span><span style="color:#8b949e;">$</span>
                            <span style="color:#c9d1d9;">status --delivery</span>
                        </p>

                        <p style="margin:10px 0 0; color:#3fb950; font-weight:bold;">
                            ● RECEIVED
                        </p>

                        <p style="margin:24px 0 0; color:#8b949e;">
                            <span style="color:#3fb950;">wilfredo@portfolio</span>:<span style="color:#58a6ff;">~</span>$ <span style="color:#c9d1d9;">█</span>
                        </p>

                    </div>

                    <div style="padding:14px 20px;
                                border-top:1px solid #30363d;
                                background-color:#161b22;
                                text-align:center;">

                        <p style="color:#8b949e;
                                  font-size:11px;
                                  margin:0;">
                            Generated by Wilfredo's Portfolio · Contact API
                        </p>

                    </div>

                </div>
            </div>
            """.formatted(name, email, subject, message);
    }
}
