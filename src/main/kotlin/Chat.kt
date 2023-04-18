data class Chat(     //Класс чатов пользователя
    override var id: Int =0,
    override val mutableListMessages: MutableList<Message> = mutableListOf(), //Список сообщений чата
    override var markDelete: Boolean = false,          //Пометка об удалении
    override var text: String = "$id",       //Название чата, по умолчанию = id чата
    //var haveUnreadMessages: Boolean = false //Метка наличия непрочитанных сообщений в чате
) : Item
{
    override fun  toString(): String {
        return "ID чата: $id \n Количество сообщений (в том числе удаленных): ${mutableListMessages.size} \n Пометка об удалении всего чата: $markDelete \n Список сообщений: \n $mutableListMessages"
    }
}
class ChatNotFoundException(message: String) : RuntimeException(message)