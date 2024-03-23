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

        sb.append("<html>\n");
//        sb.append("<head>\n");
//        sb.append("<meta charset=\"utf-8\">\n");
//        sb.append("<style>\n");
//        sb.append("body {color:white;}\n");
//        sb.append("body {background-color:DarkSlateGray;}\n");
//        sb.append("</style>\n");
//        sb.append("</head>\n");

        sb.append(getHead());

        sb.append("<body>\n");
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
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            stringBuilder.append("<div class=\"container\">");
            if (myId.equals(messages.get(i).getFrom())) {
                stringBuilder.append("<div class=\"my-message\">");
            } else {
                stringBuilder.append("<div class=\"other-message\">");
            }
//            stringBuilder.append("<div class=\"avatar\"></div>");
//            stringBuilder.append("<div class=\"text\">");
//            stringBuilder.append("От: ").append(profiles.get(messages.get(i).getFrom())).append(" ");
//            stringBuilder.append(dateTimeFormatter.format(date)).append(" \n");
            stringBuilder.append("<div class=\"message-bubble\">")
                    .append("От: ").append(profiles.get(messages.get(i).getFrom())).append(" ")
                    .append(dateTimeFormatter.format(date)).append(" \n")
                    .append("<br></br>")
                    .append(messages.get(i).getText())
                    .append("\n</div>")
                    .append("\n</div>");
//            stringBuilder.append(messages.get(i).getText());
            if (Objects.nonNull(messages.get(i).getAttachments())) {
                if (!messages.get(i).getAttachments().isEmpty()) {
                    for (int x = 0; x < messages.get(i).getAttachments().size(); x++) {
                        if (messages.get(i).getAttachments().get(x).getType().equals("photo")) {
                            stringBuilder.append("<div class=\"message-bubble\">");
                            stringBuilder.append("Вложение фото ").append(x+1);
                            stringBuilder.append("\n");
                            stringBuilder.append("<img src=\"").append(messages.get(i).getAttachments().get(x).getHd()).append("width=\"500\" height=\"600\">");
                            stringBuilder.append("\n</div>");
                        }
                        if (messages.get(i).getAttachments().get(x).getType().equals("audio_message")) {
                            stringBuilder.append("Вложение аудио ").append(x+1);
                            stringBuilder.append("\n");
                            stringBuilder.append("<audio controls>\n" +
                                    "  <source src=" + messages.get(i).getAttachments().get(x).getLink() + " />" +
                                    "</audio>");
                            stringBuilder.append(messages.get(i).getAttachments().get(x).getLink());
                        }
                        if (messages.get(i).getAttachments().get(x).getType().equals("video")) {
                            stringBuilder.append("Вложение видео ").append(x+1);
                            stringBuilder.append("\n");
                            stringBuilder.append(messages.get(i).getAttachments().get(x).getHd());

                        }
                    }
                }
            }

//            sb.append("---------------------------------------------------------------------------------------------- \n");

            sb.append("</div>\n" +
                    "    </div>");

            sb.append(stringBuilder.toString());

//            sb.append("</ul>\n");
        }

        sb.append("</body>\n");
        sb.append("</html>");

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
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <style>\n" +
                "    * {\n" +
                "      box-sizing: border-box;\n" +
                "    }\n" +
                "    \n" +
                "    .container {\n" +
                "      max-width: 800px;\n" +
                "      margin: 0 auto;\n" +
                "      padding: 20px;\n" +
                "    }\n" +
                "    \n" +
                "    .message {\n" +
                "      display: flex;\n" +
                "      margin-bottom: 10px;\n" +
                "    }\n" +
                "    \n" +
                "    .my-message {\n" +
                "      justify-content: flex-end;\n" +
                "    }\n" +
                "    \n" +
                "    .other-message {\n" +
                "      justify-content: flex-start;\n" +
                "    }\n" +
                "    \n" +
                "    .my-message .message-bubble {\n" +
                "      background-color: #DCF8C6;\n" +
                "    }\n" +
                "    \n" +
                "    .other-message .message-bubble {\n" +
                "      background-color: #E8E8E8;\n" +
                "    }\n" +
                "    \n" +
                "    .message-bubble {\n" +
                "      max-width: 70%;\n" +
                "      padding: 10px;\n" +
                "      border-radius: 10px;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>";
    }
}

