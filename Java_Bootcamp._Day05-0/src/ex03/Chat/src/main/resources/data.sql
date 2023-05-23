INSERT INTO Chat.User(login, password) VALUES
    ('Armen', '15061999'),
    ('Leva', 'LevaGei228'),
    ('Temirkan' , 'LevaGeiSoglasen'),
    ('Nikita1', 'LevaGeiPlusuyou'),
    ('Nikita2', 'AbsolutnoAgreeWithNikita1'),
    ('Konstantin', 'LevaVnatureGeyouga');

INSERT INTO Chat.chatroom(chatroomname, chatroomowner) VALUES
    (1, (SELECT UserId FROM chat.User WHERE login = 'Armen')),
    (2, (SELECT UserId FROM chat.User WHERE login = 'Leva')),
    (3, (SELECT UserId FROM chat.User WHERE login = 'Temirkan')),
    (4, (SELECT UserId FROM chat.User WHERE login = 'Nikita1'));

INSERT INTO Chat.message(messageauthor, messageroom, messagetext) VALUES
    ((SELECT UserId FROM chat.user WHERE login = 'Armen'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '1'), 'LevaGei Я в шоке'),
    ((SELECT UserId FROM chat.user WHERE login = 'Temirkan'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '1'), 'Я сам в шоке, ahper'),
    ((SELECT UserId FROM chat.user WHERE login = 'Leva'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '1'), 'Да, я gei!'),
    ((SELECT UserId FROM chat.user WHERE login = 'Nikita1'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4'), 'Привет!'),
    ((SELECT UserId FROM chat.user WHERE login = 'Nikita2'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4'), 'ХА-ХА-ХА'),
    ((SELECT UserId FROM chat.user WHERE login = 'Konstantin'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4'), 'LevaGei Я в шоке');

INSERT INTO Chat.user_chatrooms(userid, chatroomid) VALUES
    ((SELECT UserId FROM chat.user WHERE login = 'Armen'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '1')),
    ((SELECT UserId FROM chat.user WHERE login = 'Temirkan'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '1')),
    ((SELECT UserId FROM chat.user WHERE login = 'Temirkan'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '3')),
    ((SELECT UserId FROM chat.user WHERE login = 'Leva'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '1')),
    ((SELECT UserId FROM chat.user WHERE login = 'Leva'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '2')),
    ((SELECT UserId FROM chat.user WHERE login = 'Nikita1'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4')),
    ((SELECT UserId FROM chat.user WHERE login = 'Nikita2'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4')),
    ((SELECT UserId FROM chat.user WHERE login = 'Konstantin'),
     (SELECT ChatRoomId FROM chat.chatroom WHERE chatroomname = '4'));