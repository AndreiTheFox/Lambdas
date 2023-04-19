import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Before
    fun setUp() {
        ChatService.clearAll()
    }

    @Test
    fun addMessageCreateChat() {
        val unreadMessage = Message("incoming", content = "Какое то непрочитанное сообщение")
        val userId = 0
        val result = ChatService.add(userId,unreadMessage)
        assertEquals(1, result) //При добавлении 1 сообщения - создастся 1 чат, соответственно размер карты чатов = 1
    }

    @Test
    fun getUnreadChatsCount() {
        val unreadMessage = Message("incoming", content = "Какое то непрочитанное сообщение для чата 1 (индекс чата 1)")
        val unreadMessage2 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 2 (индекс чата 2)")
        val readMessage3 = Message("какой угодно тип", content = "Какое то непрочитанное сообщение для чата 3 (индекс чата 3)")
        ChatService.add(1, unreadMessage)   //В чат с индексом пользователя 1 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(3, readMessage3) //В чат с индексом пользователя 3 - прочитанное сообщение
        val result = ChatService.getUnreadChatsCount()
        assertEquals(2, result)
    }

    @Test
    fun getChats() {
        val result = ChatService.getChats()
        assertEquals(0,result.size)
    }

    @Test
    fun getLastMessageFromEveryChat() {
        val unreadMessage1 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 1 (индекс чата 1)")
        val unreadMessage2 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 2 (индекс чата 2)")
        val readMessage3 = Message("какой угодно тип", content = "Какое то непрочитанное сообщение для чата 3 (индекс чата 3)")
        ChatService.add(1, unreadMessage1)   //В чат с индексом пользователя 1 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(3, readMessage3) //В чат с индексом пользователя 3 - прочитанное сообщение

        val result = ChatService.getLastMessageFromEveryChat()
        assertEquals(3,result.size ) //Количество чатов = 3, количество последних сообщений суммарно = 3
    }
    @Test
    fun getLastMessageFromEveryChatNoMessageChatExists() {
        val emptyChat = Chat()
        ChatService.getChats().put(0,emptyChat)
        ChatService.getChats()[0]
        val result = ChatService.getLastMessageFromEveryChat()
        assertEquals("Нет сообщений",result.last() ) //Исключение, которое записывает в список "Нет сообщений" по пустому чату
    }
    @Test
    fun getLastMessages() {
        val unreadMessage = Message("incoming", content = "Какое то непрочитанное сообщение для чата 1 (индекс чата 1)")
        val unreadMessage2 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 2 (индекс чата 2)")
        ChatService.add(1, unreadMessage)   //В чат с индексом пользователя 1 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение, итого 6 сообщений в чате 2
        val result = ChatService.getLastMessagesFromChat(2, 5)
        assertEquals(5, result.size)
    }
    @Test
    fun getLastMessagesToMuchMessagesRequest() {
        val unreadMessage = Message("incoming", content = "Какое то непрочитанное сообщение для чата 1 (индекс чата 1)")
        val unreadMessage2 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 2 (индекс чата 2)")
        ChatService.add(1, unreadMessage)   //В чат с индексом пользователя 1 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение, итого 6 сообщений в чате 2
        val result = ChatService.getLastMessagesFromChat(2, 7)
        assertEquals(6, result.size)        //Вернется 6 сообщений с конца (последних), т.е. все что есть
    }
    @Test
    fun getLastMessagesZeroMessagesRequest() {
        val unreadMessage2 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 2 (индекс чата 2)")
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение, итого 6 сообщений в чате 2
        val result = ChatService.getLastMessagesFromChat(2, 0) //Запрошено 0 сообщений
        assertEquals(0, result.size)        //Вернется 0 сообщений с конца (последних)
    }
    @Test (expected = ChatNotFoundException::class)
    fun getLastMessagesNotFindChat() {
        ChatService.getLastMessagesFromChat(1, 4) //Запрошено 0 сообщений
    }

    @Test(expected = ChatNotFoundException::class)
    fun getLastNumberMessagesWithIdAndChatIdChatNotFind() {
        ChatService.getLastNumberMessagesWithIdAndChatId(0,0,1)
    }

    @Test
    fun getLastNumberMessagesWithIdAndChatId() {
        val unreadMessage2 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 2 (индекс чата 2)")
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение, итого 6 непрочитанных сообщений в чате 2
        val result = ChatService.getLastNumberMessagesWithIdAndChatId(2,1,3)
        assertEquals(3, result.size)    //Проверка того, что вернулся список из 3 запрошенных элементов (не превышает размер списка сообщений)
        assertEquals(true, result.last().alreadyRead)// Проверка установки значения о прочтении = true
    }
    @Test
    fun getLastNumberMessagesWithIdAndChatIdNumberExceedAvailable() {
        val unreadMessage2 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 2 (индекс чата 2)")
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение, итого 6 непрочитанных сообщений в чате 2
        // С 4 ID сообщения (5е сообщение в списке) вернуть 3 следующих сообщения
        val result = ChatService.getLastNumberMessagesWithIdAndChatId(2,4,3)
        assertEquals(1, result.size)    //Проверка того, что вернулся список из 1 элемента, т.к. запрос превышает имеющееся количество сообщений после ID = 4
        assertEquals(true, result.last().alreadyRead)// Проверка установки значения о прочтении = true
    }

    @Test (expected = ChatNotFoundException::class) //Исключение если чата не существует
    fun deleteMessage() {
        ChatService.deleteMessage(0,0)
    }
    @Test
    fun deleteMessageChatExistsMessageNotExists() {
        val unreadMessage2 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 2 (индекс чата 2)")
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        val result = ChatService.deleteMessage(2,3)
        assertEquals(false, result) //операция удаления вернет false, если не найдет элемент для удаления
    }
    @Test
    fun deleteMessageChatExistsMessageExists() {
        val unreadMessage2 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 2 (индекс чата 2)")
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        val result = ChatService.deleteMessage(2,0)
        assertEquals(true, result)
    }

    @Test (expected = ChatNotFoundException::class) //Исключение если чата не существует
    fun deleteChat() {
        ChatService.deleteChat(0)
    }
    @Test
    fun deleteChatOk() {
        val unreadMessage2 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 2 (индекс чата 2)")
        ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
        val result = ChatService.deleteChat(2)
        assertEquals(true, result.markDelete) //Проверка того, что выставлена метка об удалении чата
    }
}