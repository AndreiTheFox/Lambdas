//Создание сервиса по работе с чатами
object ChatService {
    private val chats = mutableMapOf<Int, Chat>()

    //Создать новое сообщение. Если чата не существует, то создастся чат и в нем новое сообщение.
    fun add(userId: Int, message: Message): Int {
        chats.getOrPut(userId) { Chat(id = userId) }.mutableListMessages.add(element = message)
        val lastMessage =
            chats.getOrPut(userId) { Chat(id = userId) }.mutableListMessages.lastIndex //Получаем индекс последнего сообщения
        chats.getOrPut(userId) { Chat(id = userId) }.mutableListMessages[lastMessage].id =
            lastMessage  //Присваиваем последнему сообщению актуальный индекс
        return chats.size //Возвращает размер карты чатов
    }

    //Подсчет количества чатов с непрочитанными сообщениями:
    fun getUnreadChatsCount() =
        chats.values.count { chat -> chat.mutableListMessages.any { message -> !message.alreadyRead } }

    //Показать все чаты
    fun getChats() = chats

    //Получить последнее сообщение из Каждого чата
    fun getLastMessageFromEveryChat(): List<String> {
        val lastMessages =
            chats.values.map { chat -> chat.mutableListMessages.lastOrNull()?.content ?: "Нет сообщений" }
        return lastMessages
    }

    //Получить последние N (count) сообщений из чата (userId)
    fun getLastMessagesFromChat(userId: Int, count: Int): List<Message> {
        val chat = chats[userId] ?: throw ChatNotFoundException("Чат пользователя $userId не найден")
        return chat.mutableListMessages.takeLast(count).onEach { it.alreadyRead = true }
    }

    //Получить последние N (count) сообщений с индекса (lastMessageId) в чате (userId)
    fun getLastNumberMessagesWithIdAndChatId(userId: Int, startMessageId: Int, count: Int): List<Message> {
        val chat = chats[userId] ?: throw ChatNotFoundException("Чат пользователя $userId не найден")
        val lenghtOfSublist = startMessageId + count //Длина подсписка которую необходимо вернуть
        if (chat.mutableListMessages.size - lenghtOfSublist > 0) {      //Если количества сообщений в чате хватает по запросу от индекса до индекс+количество.
            return chat.mutableListMessages.subList(startMessageId, lenghtOfSublist).onEach { it.alreadyRead = true }
        } else {
            val lastMessage = chat.mutableListMessages.lastIndex
            return chat.mutableListMessages.subList(startMessageId, lastMessage).onEach { it.alreadyRead = true }
            //return throw MessageNotFoundException("Недостаточно сообщений для загрузки")
        }

    }

    fun deleteMessage(userId: Int, messageId: Int): Message {
        val chat = chats[userId] ?: throw ChatNotFoundException("Чат пользователя $userId не найден")

        val modifMessage = chat.mutableListMessages.find { it.id == messageId }
            ?: throw MessageNotFoundException("Сообщение не найдено")
        modifMessage.markForDelete = true
        return modifMessage
    }

    fun deleteChat(userId: Int): Chat {
        val chat = chats[userId] ?: throw ChatNotFoundException("Чат пользователя $userId не найден")
        chat.markDelete = true
        return chat
    }

    fun clearAll() {
        chats.clear()
    }
}