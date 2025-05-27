import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import dev.langchain4j.model.output.Response;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O;

public class ImageModel {


    public void createAnImage(){
        dev.langchain4j.model.image.ImageModel model = OpenAiImageModel.withApiKey(System.getenv("OPENAI_API_KEY"));

        Response<Image> response = model.generate("Iron man in Rio de Janeiro, cartoon style");

        System.out.println(response.content().url());
    }


    public void identifyImage(){
        ChatLanguageModel model = OpenAiChatModel.builder().apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName(GPT_4_O)
                .maxTokens(50)
                .build();

        UserMessage userMessage = UserMessage.from(
                TextContent.from("What do you see?"),
                ImageContent.from("https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Mickey_Mouse_-_Steamboat_Willie_%281928%29.jpg/640px-Mickey_Mouse_-_Steamboat_Willie_%281928%29.jpg")
        );

        Response<AiMessage> response = model.generate(userMessage);

        System.out.println(response.content().text());
    }

}
