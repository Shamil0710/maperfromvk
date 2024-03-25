package com.example.maperfromvk.service;

import com.example.maperfromvk.models.Message;
import com.example.maperfromvk.models.Profile;
import com.example.maperfromvk.models.Root;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class VKService {

    private static Map<Integer, String> profiles = new HashMap<>();
    private static Integer myId = 748505981;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static String userpicGreen = "userpic2";
    private static String userpicPink = "userpic6";

    private void addProfiles(List<Profile> profilesList) {
        if (!profilesList.isEmpty()) {
            for (int i = 0; i < profilesList.size(); i++) {
                profiles.put(profilesList.get(i).getId(), profilesList.get(i).getFirstName() + " " + profilesList.get(i).getLastName() + "VK ID: " + profilesList.get(i).getId());
            }
        }
    }

    public void parseDialog(Root root) {
        addProfiles(root.profiles);
        List<Message> messages = root.getMessages();
        StringBuilder sb = new StringBuilder();
        String localUserPic;
        sb.append("<html>\n");
        sb.append(getHead());

        //Открываем боди
        sb.append("<body onload=\"CheckLocation()\">\n");
        sb.append("<div class=\"page_wrap\">");

        //Заголовок с именем пользователя
        sb.append("   <div class=\"page_header\">\n" +
                "\n" +
                "    <div class=\"content\">\n" +
                "\n" +
                "     <div class=\"text bold\">\n" +
                root.profiles.get(0).firstName +
                "     </div>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "   </div>");

        sb.append("<div class=\"page_body chat_page\">");
        sb.append("<div class=\"history\">");
//        sb.append("<ul>\n");
//
//
//
////        for (String item : list) {
////            sb.append("<li>").append(item).append("</li>\n");
////        }
//
//        sb.append("</ul>\n");
//        sb.append("</body>\n");
//        sb.append("</html>");

//        return sb.toString();

        for (int i = messages.size() - 1; i >= 0; i--) {
            StringBuilder stringBuilder = new StringBuilder();
            LocalDateTime date = LocalDateTime.ofInstant(java.time.Instant.ofEpochSecond(messages.get(i).getTime()), ZoneId.systemDefault());
//            if (myId.equals(messages.get(i).getFrom())) {
//                stringBuilder.append("<div class=\"my-message\">");
//            } else {
//                stringBuilder.append("<div class=\"other-message\">");
//            }
            if(myId.equals(messages.get(i).getFrom())) {
                localUserPic = userpicPink;
            } else {
                localUserPic = userpicGreen;
            }

            //Просто сообщение без реплеев и т.д
            sb.append("     <div class=\"message default clearfix\" id=" + messages.get(i).getId() + "\n" + //id сообщения
                    "\n" +
                    "      <div class=\"pull_left userpic_wrap\">\n" +
                    "\n" +
                    "       <div class=\"userpic " + localUserPic + "\" style=\"width: 42px; height: 42px\">\n" +
                    "\n" +
                    "        <div class=\"initials\" style=\"line-height: 42px\">\n" +
                    profiles.get(messages.get(i).getFrom()).charAt(0) + "\n" +
                    "        </div>\n" +
                    "\n" +
                    "       </div>\n" +
                    "\n" +
                    "      </div>\n" +
                    "\n" +
                    "      <div class=\"body\">\n" +
                    "\n" +
                    "       <div class=\"pull_right date details\" title=\"23.03.2024 21:06:08 UTC+03:00\">\n" + //хз нужен ли тайтл
                    dateTimeFormatter.format(date) + "\n" +
                    "       </div>\n" +
                    "\n" +
                    "       <div class=\"from_name\">\n" +
                    profiles.get(messages.get(i).getFrom()) + "\n" + //Имя отправителя
                    "       </div>\n" +
                    "\n" +
                    "       <div class=\"text\">\n" +
                    messages.get(i).getText() + "\n" + //Текст сообщения
                    "       </div>\n" +
                    "\n" +
                    "      </div>\n" +
                    "\n" +
                    "     </div>");

            //Вложения фото, гс и т.д
            if (Objects.nonNull(messages.get(i).getAttachments())) {
                if (!messages.get(i).getAttachments().isEmpty()) {
                    for (int x = 0; x < messages.get(i).getAttachments().size(); x++) {
                        if (messages.get(i).getAttachments().get(x).getType().equals("photo")) {
                            sb.append("<div class=\"message default clearfix\" id=" + messages.get(i).getId() + ">\n" +
                                    "\n" +
                                    "      <div class=\"pull_left userpic_wrap\">\n" +
                                    "\n" +
                                    "       <div class=\"userpic " + localUserPic + "\" style=\"width: 42px; height: 42px\">\n" +
                                    "\n" +
                                    "        <div class=\"initials\" style=\"line-height: 42px\">\n" +
                                    profiles.get(messages.get(i).getFrom()).charAt(0) + "\n" +
                                    "        </div>\n" +
                                    "\n" +
                                    "       </div>\n" +
                                    "\n" +
                                    "      </div>\n" +
                                    "\n" +
                                    "      <div class=\"body\">\n" +
                                    "\n" +
                                    "       <div class=\"pull_right date details\" title=\"23.03.2024 21:06:08 UTC+03:00\">\n" + //хз нужен ли тайтл
                                    dateTimeFormatter.format(date) + "\n" +
                                    "       </div>\n" +
                                    "\n" +
                                    "       <div class=\"from_name\">\n" +
                                    profiles.get(messages.get(i).getFrom()) + "\n" + //Имя отправителя
                                    "       </div>\n" +
                                    "\n" +
                                    "       <div class=\"media_wrap clearfix\">\n" +
                                    "\n" +
                                    "        <a class=\"photo_wrap clearfix pull_left\" href=\"" + messages.get(i).getAttachments().get(x).getHd() + "\">\n" +
                                    "\n" +
                                    "         <img class=\"photo\" src=\"" + messages.get(i).getAttachments().get(x).getHd() + "\" style=\"width: 195px; height: 260px\"/>\n" +
                                    "\n" +
                                    "        </a>\n" +
                                    "\n" +
                                    "       </div>\n" +
                                    "\n" +
                                    "      </div>\n" +
                                    "\n" +
                                    "     </div>");
                        }
                        if (messages.get(i).getAttachments().get(x).getType().equals("audio_message")) {
                            sb.append("<div class=\"message default clearfix joined\" id=\"" + messages.get(i).getId() + "\">\n" +
                                    "\n" +
                                    "      <div class=\"body\">\n" +
                                    "\n" +
                                    "       <div class=\"pull_right date details\" title=\"23.03.2024 21:06:22 UTC+03:00\">\n" +
                                    dateTimeFormatter.format(date) + "\n" +
                                    "       </div>\n" +
                                    "\n" +
                                    "       <div class=\"media_wrap clearfix\">\n" +
                                    "\n" +
                                    "        <a class=\"media clearfix pull_left block_link media_voice_message\" href=\"" + messages.get(i).getAttachments().get(x).getLink() + "\">\n" +
                                    "\n" +
                                    "         <div class=\"fill pull_left\">\n" +
                                    "\n" +
                                    "         </div>\n" +
                                    "\n" +
                                    "         <div class=\"body\">\n" +
                                    "\n" +
                                    "          <div class=\"title bold\">\n" +
                                    "Voice message\n" +
                                    "          </div>\n" +
                                    "\n" +
                                    "          <div class=\"status details\">\n" +
                                    messages.get(i).getAttachments().get(x).getDuration()/60 + ":" + messages.get(i).getAttachments().get(x).getDuration()%60 + "\n" +
                                    "          </div>\n" +
                                    "\n" +
                                    "         </div>\n" +
                                    "\n" +
                                    "        </a>\n" +
                                    "\n" +
                                    "       </div>\n" +
                                    "\n" +
                                    "      </div>\n" +
                                    "\n" +
                                    "     </div>");
                        }
                        if (messages.get(i).getAttachments().get(x).getType().equals("video")) {

                            sb.append("     <div class=\"message default clearfix\" id=\"" + messages.get(i).getId() + "\">\n" +
                                    "\n" +
                                    "      <div class=\"pull_left userpic_wrap\">\n" +
                                    "\n" +
                                    "       <div class=\"userpic " + localUserPic + "\" style=\"width: 42px; height: 42px\">\n" +
                                    "\n" +
                                    "        <div class=\"initials\" style=\"line-height: 42px\">\n" +
                                    "F\n" +
                                    "        </div>\n" +
                                    "\n" +
                                    "       </div>\n" +
                                    "\n" +
                                    "      </div>\n" +
                                    "\n" +
                                    "      <div class=\"body\">\n" +
                                    "\n" +
                                    "       <div class=\"pull_right date details\" title=\"25.03.2024 00:18:11 UTC+03:00\">\n" +
                                    messages.get(i).getAttachments().get(x).getDuration()/60 + ":" + messages.get(i).getAttachments().get(x).getDuration()%60 + "\n" +
                                    "       </div>\n" +
                                    "\n" +
                                    "       <div class=\"from_name\">\n" +
                                    profiles.get(messages.get(i).getFrom()) + "\n" +
                                    "       </div>\n" +
                                    "\n" +
                                    "       <div class=\"media_wrap clearfix\">\n" +
                                    "\n" +
                                    "        <a class=\"video_file_wrap clearfix pull_left\" href=\"" + messages.get(i).getAttachments().get(x).getLink() + "\">\n" +
                                    "\n" +
                                    "         <div class=\"video_play_bg\">\n" +
                                    "\n" +
                                    "          <div class=\"video_play\">\n" +
                                    "\n" +
                                    "          </div>\n" +
                                    "\n" +
                                    "         </div>\n" +
                                    "\n" +
                                    "         <div class=\"video_duration\">\n" +
                                    messages.get(i).getAttachments().get(x).getDuration()/60 + ":" + messages.get(i).getAttachments().get(x).getDuration()%60 + "\n" +
                                    "         </div>\n" +
                                    "\n" +
                                    "         <img class=\"video_file\" href=\"" + messages.get(i).getAttachments().get(x).getLink() + "\">\n" +
                                    "\n" +
                                    "        </a>\n" +
                                    "\n" +
                                    "       </div>\n" +
                                    "\n" +
                                    "      </div>\n" +
                                    "\n" +
                                    "     </div>");

                        }
                    }
                }
            }
//            stringBuilder.append("<div class=\"avatar\"></div>");
//            stringBuilder.append("<div class=\"text\">");
//            stringBuilder.append("От: ").append(profiles.get(messages.get(i).getFrom())).append(" ");
//            stringBuilder.append(dateTimeFormatter.format(date)).append(" \n");
//            stringBuilder.append("<div class=\"message-bubble\">")
//                    .append("От: ").append(profiles.get(messages.get(i).getFrom())).append(" ")
//                    .append(dateTimeFormatter.format(date)).append(" \n")
//                    .append("<br></br>")
//                    .append(messages.get(i).getText())
//                    .append("\n</div>")
//                    .append("\n</div>");
//            stringBuilder.append(messages.get(i).getText());
//            if (Objects.nonNull(messages.get(i).getAttachments())) {
//                if (!messages.get(i).getAttachments().isEmpty()) {
//                    for (int x = 0; x < messages.get(i).getAttachments().size(); x++) {
//                        if (messages.get(i).getAttachments().get(x).getType().equals("photo")) {
//                            stringBuilder.append("<div class=\"message-bubble\">");
//                            stringBuilder.append("Вложение фото ").append(x+1);
//                            stringBuilder.append("\n");
//                            stringBuilder.append("<img src=\"").append(messages.get(i).getAttachments().get(x).getHd()).append("width=\"500\" height=\"600\">");
//                            stringBuilder.append("\n</div>");
//                        }
//                        if (messages.get(i).getAttachments().get(x).getType().equals("audio_message")) {
//                            stringBuilder.append("Вложение аудио ").append(x+1);
//                            stringBuilder.append("\n");
//                            stringBuilder.append("<audio controls>\n" +
//                                    "  <source src=" + messages.get(i).getAttachments().get(x).getLink() + " />" +
//                                    "</audio>");
//                            stringBuilder.append(messages.get(i).getAttachments().get(x).getLink());
//                        }
//                        if (messages.get(i).getAttachments().get(x).getType().equals("video")) {
//                            stringBuilder.append("Вложение видео ").append(x+1);
//                            stringBuilder.append("\n");
//                            stringBuilder.append(messages.get(i).getAttachments().get(x).getHd());
//
//                        }
//                    }
//                }
//            }
//
////            sb.append("---------------------------------------------------------------------------------------------- \n");
//
//            sb.append("</div>\n" +
//                    "    </div>");
//
//            sb.append(stringBuilder.toString());

//            sb.append("</ul>\n");
        }

//        sb.append("</body>\n");
//        sb.append("</html>");

        sb.append("   </div>\n" +
                "\n" +
                "  </div>\n" +
                "\n" +
                " </body>\n" +
                "\n" +
                "</html>\n");

        saveHtmlToFile(sb.toString(), root.profiles.get(0).firstName + " и " + root.profiles.get(1).firstName + ".html");

    }

    private void saveHtmlToFile(String htmlString, String filename) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            writer.write(htmlString);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении HTML файла: " + e.getMessage());
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии BufferedWriter: " + e.getMessage());
            }
        }
    }

    private String getHead() {
        return "<head>\n" +
                "\n" +
                "  <meta charset=\"utf-8\"/>\n" +
                "<title>Exported Data</title>\n" +
                "  <meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/>\n" +
                "\n" +
                "  <link href=\"css/style.css\" rel=\"stylesheet\"/>\n" +
                "\n" +
                "  <script src=\"js/script.js\" type=\"text/javascript\">\n" +
                "\n" +
                "  </script>\n" +
                "\n" +
                " </head>";
    }
}

