import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;


public class ChatModel {



        public void askForJoke() {

            ChatLanguageModel model = OpenAiChatModel.withApiKey("demo");

            String joke = model.generate("Tell me a joke about JavaScript");

            System.out.println(joke);
        }



}
