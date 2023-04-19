fun main() {
    val unreadMessage = Message("incoming", content = "Какое то непрочитанное сообщение")
    val oldMessage = Message("out", content = "Уже давно прочитано")
    val unreadMessage2 = Message("incoming", content = "Какое то непрочитанное сообщение для чата 2 (индекс 2)")
    val unreadMessage3 = Message("incoming", content = "Какое то непрочитанное сообщение для удаления из чата 2")
    val emptyChat = Chat()
    ChatService.getChats().put(0,emptyChat) //Чат 0

    ChatService.add(1, oldMessage)   //Добавить новый чат с индексом пользователя = 1
    ChatService.add(2, oldMessage)   //Добавить новый чат с индексом пользователя 2 и - прочитанным сообщением
    ChatService.add(2, unreadMessage)   //В чат с индексом пользователя 2 - непрочитанное сообщение
    ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
    ChatService.add(2, unreadMessage3) //В чат с индексом пользователя 2 - непрочитанное сообщение
    ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
    ChatService.add(2, unreadMessage2) //В чат с индексом пользователя 2 - непрочитанное сообщение
    ChatService.add(3, unreadMessage2) //В чат с индексом пользователя 3 - непрочитанное сообщение
    ChatService.add(4, unreadMessage2) //В чат с индексом пользователя 4 - непрочитанное сообщение
    ChatService.deleteChat(3)

   // println(ChatService.deleteMessage(1,0))

    println(ChatService.getLastMessageFromEveryChat())
}