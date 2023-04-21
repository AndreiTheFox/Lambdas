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
    fun getUnreadChatsCount(): Int =
        //Возврат количества чатов где есть хоть 1 непрочитанное сообщение, предварительная проверка и фильтрация от пустых и удаленных чатов
        chats.filterValues { it.mutableListMessages.size > 0 }
            .filterValues { !it.markDelete }
            .values.count { chat -> chat.mutableListMessages.any { message -> !message.alreadyRead } }

    //Показать все чаты
    fun getChats() = chats

    //Получить последнее сообщение из Каждого чата
    fun getLastMessageFromEveryChat(): List<String> {
        val filteredChats = chats
            .filterValues { !it.markDelete } //Фильтрация от удаленных, но НЕ пустых
        return filteredChats.values.map { chat -> chat.mutableListMessages.lastOrNull()?.content ?: "Нет сообщений" }
    }

    //Получить последние N (count) сообщений из чата (userId)
    fun getLastMessagesFromChat(userId: Int, count: Int): List<Message> {
        val foundChat = chats.filterValues { it.mutableListMessages.size > 0 }[userId]
            ?: throw ChatNotFoundException("Чат пользователя $userId не найден")
        return foundChat.mutableListMessages
            .takeLast(count)
            .onEach { it.alreadyRead = true }
    }

    //Получить последние N (count) сообщений с индекса (lastMessageId) в чате (userId)
    fun getLastNumberMessagesWithIdAndChatId(userId: Int, startMessageId: Int, count: Int): List<Message> {
        val foundChat = chats.filterValues { it.mutableListMessages.size > 0 }[userId]
            ?: throw ChatNotFoundException("Чат пользователя $userId не найден")
        return foundChat.mutableListMessages
            .asSequence()
            .drop(startMessageId)
            .take(count)
            .ifEmpty { throw MessageNotFoundException("Нет сообщений") }
            .onEach { it.alreadyRead = true }
            .toList()
    }

    fun deleteMessage(userId: Int, messageId: Int): Boolean {
        val foundChat = chats.filterValues { it.mutableListMessages.size > 0 }[userId]
            ?: throw ChatNotFoundException("Чат пользователя $userId не найден")
        return foundChat.mutableListMessages.removeIf { it.id == messageId } //Вернет true если удалит, false если не найдет чего удалить
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