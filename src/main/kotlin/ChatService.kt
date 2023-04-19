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
    fun getUnreadChatsCount(): Int {
        //Возврат количества чатов где есть хоть 1 непрочитанное сообщение, предварительная проверка и фильтрация от пустых и удаленных чатов
        return filterDeleteAndEmptyChats().values.count { chat -> chat.mutableListMessages.any { message -> !message.alreadyRead } }
    }

    //Показать все чаты
    fun getChats() = chats

    //Получить последнее сообщение из Каждого чата
    fun getLastMessageFromEveryChat(): List<String> {
        val filteredChats = chats.filterValues { !it.markDelete } //Фильтрация от удаленных, но НЕ пустых
        return filteredChats.values.map { chat -> chat.mutableListMessages.lastOrNull()?.content ?: "Нет сообщений" }
    }

    //Получить последние N (count) сообщений из чата (userId)
    fun getLastMessagesFromChat(userId: Int, count: Int): List<Message> {
        val filteredChats = filterDeleteAndEmptyChats() //Фильтрация от удаленных и пустых чатов
        val chat = filteredChats[userId] ?: throw ChatNotFoundException("Чат пользователя $userId не найден")
        return chat.mutableListMessages.takeLast(count).onEach { it.alreadyRead = true }
    }

    //Получить последние N (count) сообщений с индекса (lastMessageId) в чате (userId)
    fun getLastNumberMessagesWithIdAndChatId(userId: Int, startMessageId: Int, count: Int): List<Message> {
        val filteredChats = filterDeleteAndEmptyChats() //Фильтрация от удаленных и пустых чатов
        val chat = filteredChats[userId] ?: throw ChatNotFoundException("Чат пользователя $userId не найден")
        val lenghtOfSublist = startMessageId + count //Длина подсписка которую необходимо вернуть
        if (chat.mutableListMessages.size - lenghtOfSublist > 0) {      //Если количества сообщений в чате хватает по запросу от индекса до индекс+количество.
            return chat.mutableListMessages.subList(startMessageId, lenghtOfSublist).onEach { it.alreadyRead = true }
        } else {
            val lastMessage = chat.mutableListMessages.lastIndex
            return chat.mutableListMessages.subList(startMessageId, lastMessage).onEach { it.alreadyRead = true }
            //return throw MessageNotFoundException("Недостаточно сообщений для загрузки")
        }
    }

    fun deleteMessage(userId: Int, messageId: Int): Boolean {
        val filteredChats = filterDeleteAndEmptyChats() //Фильтрация от удаленных и пустых чатов
        val chat = filteredChats[userId] ?: throw ChatNotFoundException("Чат пользователя $userId не найден")
        return chat.mutableListMessages.removeIf { it.id == messageId } //Вернет true если удалит, false если не найдет чего удалить
    }

    fun deleteChat(userId: Int): Chat {
        val chat = chats[userId] ?: throw ChatNotFoundException("Чат пользователя $userId не найден")
        chat.markDelete = true
        return chat
    }

    //Функция Проверки на отметку удаления и пустоты чата.
    private fun filterDeleteAndEmptyChats(): Map<Int, Chat> {
        val filteredFromEmpty = chats.filterValues { it.mutableListMessages.size > 0 }    //Фильтр от пустых чатов
        return filteredFromEmpty.filterValues { !it.markDelete }    //Фильтр от удаленных чатов

    }

    fun clearAll() {
        chats.clear()
    }
}