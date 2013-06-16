# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table game (
  id                        bigint not null,
  home_id                   bigint,
  away_id                   bigint,
  gameday_id                bigint,
  constraint pk_game primary key (id))
;

create table gameday (
  id                        bigint not null,
  name                      varchar(255),
  meeting_id                bigint,
  constraint pk_gameday primary key (id))
;

create table meeting (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_meeting primary key (id))
;

create table result (
  id                        bigint not null,
  home                      integer,
  away                      integer,
  game_id                   bigint,
  constraint pk_result primary key (id))
;

create table team (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_team primary key (id))
;


create table team_meeting (
  team_id                        bigint not null,
  meeting_id                     bigint not null,
  constraint pk_team_meeting primary key (team_id, meeting_id))
;
create sequence game_seq;

create sequence gameday_seq;

create sequence meeting_seq;

create sequence result_seq;

create sequence team_seq;

alter table game add constraint fk_game_home_1 foreign key (home_id) references team (id) on delete restrict on update restrict;
create index ix_game_home_1 on game (home_id);
alter table game add constraint fk_game_away_2 foreign key (away_id) references team (id) on delete restrict on update restrict;
create index ix_game_away_2 on game (away_id);
alter table game add constraint fk_game_gameday_3 foreign key (gameday_id) references gameday (id) on delete restrict on update restrict;
create index ix_game_gameday_3 on game (gameday_id);
alter table gameday add constraint fk_gameday_meeting_4 foreign key (meeting_id) references meeting (id) on delete restrict on update restrict;
create index ix_gameday_meeting_4 on gameday (meeting_id);
alter table result add constraint fk_result_game_5 foreign key (game_id) references game (id) on delete restrict on update restrict;
create index ix_result_game_5 on result (game_id);



alter table team_meeting add constraint fk_team_meeting_team_01 foreign key (team_id) references team (id) on delete restrict on update restrict;

alter table team_meeting add constraint fk_team_meeting_meeting_02 foreign key (meeting_id) references meeting (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists game;

drop table if exists gameday;

drop table if exists meeting;

drop table if exists team_meeting;

drop table if exists result;

drop table if exists team;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists game_seq;

drop sequence if exists gameday_seq;

drop sequence if exists meeting_seq;

drop sequence if exists result_seq;

drop sequence if exists team_seq;

