drop schema if exists Chat cascade;
create schema if not exists Chat;

CREATE TABLE Chat.User (
    UserId SERIAL PRIMARY KEY,
    Login varchar(50) NOT NULL UNIQUE,
    Password VARCHAR(50) NOT NULL
);
CREATE TABLE Chat.ChatRoom (
    ChatRoomId SERIAL PRIMARY KEY,
    ChatRoomName varchar(50) NOT NULL UNIQUE,
    ChatRoomOwner INT NOT NULL,
    foreign key (ChatRoomOwner) references Chat.User(UserId)
);
CREATE TABLE Chat.Message (
    MessageId SERIAL PRIMARY KEY,
    MessageAuthor INT NOT NULL,
    MessageRoom INT NOT NULL,
    MessageText text NOT NULL,
    MessageDate timestamp default CURRENT_TIMESTAMP,
    foreign key (MessageAuthor) references Chat.User(UserId),
    foreign key (MessageRoom) references Chat.ChatRoom(ChatRoomId)
);
CREATE TABLE Chat.User_ChatRooms (
    ChatRoomsId SERIAL PRIMARY KEY,
    UserId INT NOT NULL,
    ChatRoomId INT NOT NULL,
    foreign key (UserId) references Chat.User(UserId),
    foreign key (ChatRoomId) references Chat.ChatRoom(ChatRoomId)
);