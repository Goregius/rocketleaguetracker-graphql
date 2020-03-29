create table if not exists leaderboard_rating
(
    id       bigserial not null
        constraint leaderboard_rating_pk
            primary key,
    gamer    text      not null,
    rank     int       not null,
    rating   int       not null,
    games    int       not null,
    playlist int       not null
);