create table roles
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table users
(
    id       bigint auto_increment
        primary key,
    image    longblob     null,
    username varchar(255) null,
    email    varchar(255) null,
    password varchar(255) null,
    role_id  bigint       null,
    constraint fk_users_roles
        foreign key (role_id) references roles (id)
);

create table colors
(
    id   bigint auto_increment
        primary key,
    code varchar(255) null
);

create table tags
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table drawings
(
    id      bigint auto_increment
        primary key,
    cells   json         null,
    user_id bigint       null,
    name    varchar(255) null,
    constraint drawings_ibfk_1
        foreign key (user_id) references users (id) ON DELETE CASCADE
);

create index user_id
    on drawings (user_id);

create table palettes
(
    id      bigint auto_increment
        primary key,
    name    varchar(255) null,
    user_id bigint       null,
    constraint palettes_ibfk_1
        foreign key (user_id) references users (id) ON DELETE CASCADE
);

create index user_id
    on palettes (user_id);

create table palettes_colors
(
    palette_id bigint not null,
    color_id   bigint not null,
    primary key (palette_id, color_id),
    constraint palettes_colors_ibfk_1
        foreign key (palette_id) references palettes (id) ON DELETE CASCADE,
    constraint palettes_colors_ibfk_2
        foreign key (color_id) references colors (id)
);

create index color_id
    on palettes_colors (color_id);

create table palettes_tags
(
    palette_id bigint not null,
    tag_id     bigint not null,
    primary key (palette_id, tag_id),
    constraint palettes_tags_ibfk_1
        foreign key (palette_id) references palettes (id) ON DELETE CASCADE,
    constraint palettes_tags_ibfk_2
        foreign key (tag_id) references tags (id)
);

create index tag_id
    on palettes_tags (tag_id);