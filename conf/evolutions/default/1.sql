# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table game (
  id                        bigint not null,
  home_id                   bigint,
  away_id                   bigint,
  gameday_id                bigint,
  start_date                timestamp,
  constraint pk_game primary key (id))
;

create table gameday (
  id                        bigint not null,
  name                      varchar(255),
  meeting_id                bigint,
  constraint pk_gameday primary key (id))
;

create table group_role (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_group_role primary key (id))
;

create table meeting (
  id                        bigint not null,
  name                      varchar(255),
  manager_id                bigint,
  constraint pk_meeting primary key (id))
;

create table password (
  id                        bigint not null,
  user_id                   bigint,
  password                  varchar(255),
  constraint pk_password primary key (id))
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
  meeting_id                bigint,
  constraint pk_team primary key (id))
;

create table tip (
  id                        bigint not null,
  owner_id                  bigint,
  game_id                   bigint,
  home                      integer,
  away                      integer,
  last_change               timestamp,
  constraint pk_tip primary key (id))
;

create table user (
  id                        bigint not null,
  username                  varchar(255),
  email                     varchar(255),
  constraint pk_user primary key (id))
;


create table meeting_user (
  meeting_id                     bigint not null,
  user_id                        bigint not null,
  constraint pk_meeting_user primary key (meeting_id, user_id))
;

create table user_group_role (
  user_id                        bigint not null,
  group_role_id                  bigint not null,
  constraint pk_user_group_role primary key (user_id, group_role_id))
;
create sequence game_seq;

create sequence gameday_seq;

create sequence group_role_seq;

create sequence meeting_seq;

create sequence password_seq;

create sequence result_seq;

create sequence team_seq;

create sequence tip_seq;

create sequence user_seq;

alter table game add constraint fk_game_home_1 foreign key (home_id) references team (id) on delete restrict on update restrict;
create index ix_game_home_1 on game (home_id);
alter table game add constraint fk_game_away_2 foreign key (away_id) references team (id) on delete restrict on update restrict;
create index ix_game_away_2 on game (away_id);
alter table game add constraint fk_game_gameday_3 foreign key (gameday_id) references gameday (id) on delete restrict on update restrict;
create index ix_game_gameday_3 on game (gameday_id);
alter table gameday add constraint fk_gameday_meeting_4 foreign key (meeting_id) references meeting (id) on delete restrict on update restrict;
create index ix_gameday_meeting_4 on gameday (meeting_id);
alter table meeting add constraint fk_meeting_manager_5 foreign key (manager_id) references user (id) on delete restrict on update restrict;
create index ix_meeting_manager_5 on meeting (manager_id);
alter table password add constraint fk_password_user_6 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_password_user_6 on password (user_id);
alter table result add constraint fk_result_game_7 foreign key (game_id) references game (id) on delete restrict on update restrict;
create index ix_result_game_7 on result (game_id);
alter table team add constraint fk_team_meeting_8 foreign key (meeting_id) references meeting (id) on delete restrict on update restrict;
create index ix_team_meeting_8 on team (meeting_id);
alter table tip add constraint fk_tip_owner_9 foreign key (owner_id) references user (id) on delete restrict on update restrict;
create index ix_tip_owner_9 on tip (owner_id);
alter table tip add constraint fk_tip_game_10 foreign key (game_id) references game (id) on delete restrict on update restrict;
create index ix_tip_game_10 on tip (game_id);



alter table meeting_user add constraint fk_meeting_user_meeting_01 foreign key (meeting_id) references meeting (id) on delete restrict on update restrict;

alter table meeting_user add constraint fk_meeting_user_user_02 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_group_role add constraint fk_user_group_role_user_01 foreign key (user_id) references user (id) on delete restrict on update restrict;

alter table user_group_role add constraint fk_user_group_role_group_role_02 foreign key (group_role_id) references group_role (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists game;

drop table if exists gameday;

drop table if exists group_role;

drop table if exists meeting;

drop table if exists meeting_user;

drop table if exists password;

drop table if exists result;

drop table if exists team;

drop table if exists tip;

drop table if exists user;

drop table if exists user_group_role;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists game_seq;

drop sequence if exists gameday_seq;

drop sequence if exists group_role_seq;

drop sequence if exists meeting_seq;

drop sequence if exists password_seq;

drop sequence if exists result_seq;

drop sequence if exists team_seq;

drop sequence if exists tip_seq;

drop sequence if exists user_seq;

