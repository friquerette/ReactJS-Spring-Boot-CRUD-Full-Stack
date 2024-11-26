package net.javaguides.springboot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AIService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private final ChatClient chatClient;

    public AIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    public String getOneQuestion(String prompt) {
        List<Media> metadata = new ArrayList<>();
        List<String> list = getMedataData();
        Media media = new Media(new MimeType("application/text"), list.toString());
        metadata.add(media);
        String textContent = "peut tu faire une requete pour cette struture de table que tu trouveras en metadata";
        UserMessage userMessage = new UserMessage(textContent, metadata);
        int x = 0;
        if(x == 1) {
            return chatClient.prompt()
                    .messages(userMessage)
                    .call()
                    .content();
        }
        return "coucou";
    }

    List<String> getMedataData() {
        String result = "";
        Map<String, String> tablesAndColumns = new HashMap<>();
        List<String> metadata = new ArrayList<>();
        try {
            DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                ResultSet columns = metaData.getColumns(null, null, tableName, "%");

                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    tablesAndColumns.put(tableName, columnName);
                    metadata.add(columnName);
                }
            }
        } catch (Exception e) {
            result = "Erreur " + e.getMessage();
        }
        System.out.println(metadata);
        return metadata;
    }

}
